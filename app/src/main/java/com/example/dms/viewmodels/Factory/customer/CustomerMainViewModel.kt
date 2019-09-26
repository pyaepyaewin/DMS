package com.example.dms.viewmodels.Factory.customer

import androidx.lifecycle.MutableLiveData
import com.example.dms.data.repositories.CustomerRepository
import com.example.dms.data.database.table.Customer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CustomerMainViewModel(
    private val customerRepo: CustomerRepository
) : CustomerBaseViewModel()
{
    var errorState = MutableLiveData<String>()
    var successState = MutableLiveData<List<Customer>>()

    fun loadCustomerList(customerReqParam : String) {
        customerRepo.customerData
            .observeForever {
                launch {
                    it
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnNext {
                            customerRepo.saveDataIntoDatabase(it.data[0].Customer)
                        }
                        .subscribe({ response ->
                            customerRepo.customerData = MutableLiveData()
                            successState.postValue(response.data[0].Customer)
                        }, { error ->
                            errorState.postValue(error.localizedMessage)
                        })
                }
            }


        customerRepo.loadCustomerList(customerReqParam)
    }
}

package com.example.dms.viewmodels.Factory.api

import androidx.lifecycle.MutableLiveData
import com.example.dms.data.repositories.ApiRepository
import com.example.dms.data.repositories.CustomerRepository
import com.example.dms.network.response.Customer
import com.example.dms.network.response.Sale.Product
import com.example.dms.viewmodels.Factory.customer.CustomerBaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ApiMainViewModel (
    private val repo: ApiRepository
) : ApiBaseViewModel()
{
//    var errorState = MutableLiveData<String>()
//    var successCustomerState = MutableLiveData<List<Customer>>()
//    var successSaleState = MutableLiveData<List<Product>>()
//
//    fun loadCustomerList(customerReqParam : String) {
//        repo.customerData
//            .observeForever {
//                launch {
//                    it
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe({ response ->
//                            repo.customerData = MutableLiveData()
//                            successCustomerState.postValue(response.data[0].Customer)
//                        }, { error ->
//                            errorState.postValue(error.localizedMessage)
//                        })
//                }
//            }
//
//
//        repo.loadCustomerList(customerReqParam)
//    }
//    fun loadSaleList(saleRequestParm: String) {
//        repo.saleData
//            .observeForever {
//                launch {
//                    it
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe({ response ->
//                            repo.saleData = MutableLiveData()
//                            successSaleState.postValue(response.data[0].Product)
//                        }, { error ->
//                            errorState.postValue(error.localizedMessage)
//                        })
//                }
//            }
//       repo.loadSaleList(saleRequestParm)
//
//    }
}
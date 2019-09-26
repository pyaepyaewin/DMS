package com.example.dms.data.repositories

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.dms.data.database.MyDatabase
import com.example.dms.network.ApiService
import com.example.dms.data.database.table.Customer
import com.example.dms.network.response.CustomerListResponse
import com.example.dms.network.response.Data
import com.example.dms.util.Utils
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CustomerRepositoryImpl(
    private val context: Context,
    private val mApiService: ApiService,
    private val database: MyDatabase
) : CustomerRepository {

    override fun loadCustomerList(param: String) {
        if (Utils.isOnline(context)) {
            customerData.postValue(mApiService.loadCustomerData(param))
        }
        else {
            val localCustomerDataList = database.customerDao().allCustomerData
            val disposable = CompositeDisposable()
            disposable.add(
                localCustomerDataList
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe {
                        disposable.clear()
//                        var list= listOf<Data>()
                        val list: List<Data> = listOf(Data(it))

                        var responseData = CustomerListResponse("null","null",list,"null")
                       // responseData.user_id=it.toString()
                        customerData.postValue(Observable.just(responseData))
                    }
            )
        }

    }


    override var customerData: MutableLiveData<Observable<CustomerListResponse>> = MutableLiveData()

    override fun saveDataIntoDatabase(customerlist: List<Customer>) {
        Observable.fromCallable{database.customerDao().insertAll(customerlist)}
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe()
    }

    }





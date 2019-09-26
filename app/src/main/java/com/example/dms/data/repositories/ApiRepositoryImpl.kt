package com.example.dms.data.repositories

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.dms.data.database.MyDatabase
import com.example.dms.network.ApiService
import com.example.dms.network.response.Customer
import com.example.dms.network.response.CustomerListResponse
import com.example.dms.network.response.Sale.Product
import com.example.dms.network.response.Sale.SaleListResponse
import com.example.dms.util.Utils
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ApiRepositoryImpl(
) {}

//    override var customerData: MutableLiveData<Observable<CustomerListResponse>> = MutableLiveData()
//
//    override fun loadCustomerList(param: String) {
//        if (Utils.isOnline(context)) {
//            customerData.postValue(mApiService.loadCustomerData(param))
//      }
//        else {
//            val localCustomerDataList = database.customerDao().allCustomerData
//            val disposable = CompositeDisposable()
//            disposable.add(
//                localCustomerDataList
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(Schedulers.io())
//                    .subscribe {
//                        disposable.clear()
//                        val responseData=CustomerListResponse()
//
//
//                    }
//            )
//        }

    //}


//    override var saleData: MutableLiveData<Observable<SaleListResponse>> = MutableLiveData()
//
//
//
//    override fun loadSaleList(param: String) {
//        if (Utils.isOnline(context)) {
//         saleData.postValue(mApiService.loadSaleData(param))
//                  }
//    }
//    override fun saveCustomerDataIntoDatabase(customerList: List<Customer>) {
//        Observable.fromCallable{database.customerDao().insertAll(customerList)}
//            .subscribeOn(Schedulers.io())
//            .observeOn(Schedulers.io())
//            .subscribe()
//    }
//
//
//
//    override fun saveSaleDataIntoDatabase(saleList: List<Product>) {
//        Observable.fromCallable{database.productDao().insertAll(saleList)}
//            .subscribeOn(Schedulers.io())
//            .observeOn(Schedulers.io())
//            .subscribe()
//    }
//
//}
package com.example.dms.data.repositories

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.dms.data.database.MyDatabase
import com.example.dms.network.ApiService
import com.example.dms.network.response.Sale.Data
import com.example.dms.network.response.Sale.Product
import com.example.dms.network.response.Sale.SaleListResponse
import com.example.dms.util.Utils
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SaleRepositoryImpl(
    private val context: Context,
    private val mApiService: ApiService,
    private val database:MyDatabase
) : SaleRepository {
    override var saleData: MutableLiveData<Observable<SaleListResponse>> = MutableLiveData()
    override fun loadSaleList(param: String) {
        if (Utils.isOnline(context)) {
            saleData.postValue(mApiService.loadSaleData(param))
                   }
        else {
            val localSaleDataList = database.productDao().allProductData
            val disposable = CompositeDisposable()
            disposable.add(
                localSaleDataList
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe {
                        disposable.clear()
//                        var list= listOf<Data>()
                        val list: List<Data> = listOf(Data(it))

                        var responseData = SaleListResponse("null","null",list,"null")
                        // responseData.user_id=it.toString()
                        saleData.postValue(Observable.just(responseData))
                    }
            )
        }

    }

    override fun saveDataIntoDatabase(saleList: List<Product>) {
        Observable.fromCallable{database.productDao().insertAll(saleList)}
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe()
    }
    }


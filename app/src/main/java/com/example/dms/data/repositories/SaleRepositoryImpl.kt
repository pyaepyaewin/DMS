package com.example.dms.data.repositories

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.dms.network.ApiService
import com.example.dms.network.response.Sale.SaleListResponse
import com.example.dms.util.Utils
import io.reactivex.Observable

class SaleRepositoryImpl(
    private val context: Context,
    private val mApiService: ApiService
) : SaleRepository {
    override var saleData: MutableLiveData<Observable<SaleListResponse>> = MutableLiveData()


    override fun loadSaleList(param: String) {
        if (Utils.isOnline(context)) {
            saleData.postValue(mApiService.loadSaleData(param))
                   }
    }
}
package com.example.dms.data.repositories

import androidx.lifecycle.MutableLiveData
import com.example.dms.data.database.table.Date
import com.example.dms.data.database.table.Product
import com.example.dms.network.response.Sale.SaleListResponse
import io.reactivex.Observable

interface SaleRepository {
    var saleData: MutableLiveData<Observable<SaleListResponse>>
   fun loadSaleList(param : String)
    fun saveDataIntoDatabase(movieList:List<Product>)

}
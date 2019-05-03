package com.aceplus.data.database.dao.sale.saleexchange

import com.aceplus.domain.entity.sale.saleexchange.SaleExchangeDetail
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface SaleExchangeDetailDao{

    @get:Query("select * from sale_exchange_detail")
    val allDataLD: LiveData<List<SaleExchangeDetail>>

    @get:Query("select * from sale_exchange_detail")
    val allData: List<SaleExchangeDetail>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<SaleExchangeDetail>)

    @Query("Delete from sale_exchange_detail")
    fun deleteAll()

}
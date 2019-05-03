package com.aceplus.data.database.dao.sale.saleexchange

import com.aceplus.domain.entity.sale.saleexchange.SaleExchange
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface SaleExchangeDao{

    @get:Query("select * from sale_exchange")
    val allDataLD: LiveData<List<SaleExchange>>

    @get:Query("select * from sale_exchange")
    val allData: List<SaleExchange>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<SaleExchange>)

    @Query("Delete from sale_exchange")
    fun deleteAll()

}
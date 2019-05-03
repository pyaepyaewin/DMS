package com.aceplus.data.database.dao.sale.saletarget

import com.aceplus.domain.entity.sale.saletarget.SaleTargetSaleMan
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface SaleTargetSaleManDao{

    @get:Query("select * from sale_target_sale_man")
    val allDataLD: LiveData<List<SaleTargetSaleMan>>

    @get:Query("select * from sale_target_sale_man")
    val allData: List<SaleTargetSaleMan>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<SaleTargetSaleMan>)

    @Query("Delete from sale_target_sale_man")
    fun deleteAll()

}
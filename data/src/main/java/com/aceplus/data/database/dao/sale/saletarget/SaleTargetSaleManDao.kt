package com.aceplus.data.database.dao.sale.saletarget

import com.aceplus.domain.entity.sale.saletarget.SaleTargetSaleMan
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.aceplus.domain.model.Product
import com.aceplus.domain.vo.report.SaleTargetVO
import com.aceplus.domain.vo.report.TargetAndActualSaleForProduct


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

    @Query("select * from sale_target_sale_man where sale_man_id = :customerId ")
    fun getTargetSaleDB(customerId: Int): List<SaleTargetSaleMan>?


}
package com.aceplus.data.database.dao.sale.salereturn

import com.aceplus.domain.entity.sale.salereturn.SaleReturnDetail
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface SaleReturnDetailDao{

    @get:Query("select * from sale_return_detail")
    val allDataLD: LiveData<List<SaleReturnDetail>>

    @get:Query("select * from sale_return_detail")
    val allData: List<SaleReturnDetail>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<SaleReturnDetail>)

    @Query("Delete from sale_return_detail")
    fun deleteAll()

}
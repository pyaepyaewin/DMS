package com.aceplus.data.database.dao.sale

import com.aceplus.domain.entity.sale.SaleMan
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface SaleManDao{

    @get:Query("select * from sale_man")
    val allDataLD: LiveData<List<SaleMan>>

    @get:Query("select * from sale_man")
    val allData: List<SaleMan>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<SaleMan>)

    @Query("Delete from sale_man")
    fun deleteAll()

}
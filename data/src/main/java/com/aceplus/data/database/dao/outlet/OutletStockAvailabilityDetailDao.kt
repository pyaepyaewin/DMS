package com.aceplus.data.database.dao.outlet

import com.aceplus.domain.entity.outlet.OutletStockAvailabilityDetail
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface OutletStockAvailabilityDetailDao{

    @get:Query("select * from outlet_stock_availability_detail")
    val allDataLD: LiveData<List<OutletStockAvailabilityDetail>>

    @get:Query("select * from outlet_stock_availability_detail")
    val allData: List<OutletStockAvailabilityDetail>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<OutletStockAvailabilityDetail>)

    @Query("Delete from outlet_stock_availability_detail")
    fun deleteAll()

}
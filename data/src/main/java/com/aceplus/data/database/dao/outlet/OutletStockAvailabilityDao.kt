package com.aceplus.data.database.dao.outlet

import com.aceplus.domain.entity.outlet.OutletStockAvailability
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface OutletStockAvailabilityDao{

    @get:Query("select * from outlet_stock_availability")
    val allDataLD: LiveData<List<OutletStockAvailability>>

    @get:Query("select * from outlet_stock_availability")
    val allData: List<OutletStockAvailability>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<OutletStockAvailability>)

    @Query("Delete from outlet_stock_availability")
    fun deleteAll()

}
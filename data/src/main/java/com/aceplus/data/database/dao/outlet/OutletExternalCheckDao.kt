package com.aceplus.data.database.dao.outlet

import com.aceplus.domain.entity.outlet.OutletExternalCheck
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface OutletExternalCheckDao{

    @get:Query("select * from outlet_external_check")
    val allDataLD: LiveData<List<OutletExternalCheck>>

    @get:Query("select * from outlet_external_check")
    val allData: List<OutletExternalCheck>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<OutletExternalCheck>)

    @Query("Delete from outlet_external_check")
    fun deleteAll()

}
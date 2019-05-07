package com.aceplus.data.database.dao.outlet

import com.aceplus.domain.entity.outlet.OutletVisibility
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface OutletVisibilityDao{

    @get:Query("select * from outlet_visibility")
    val allDataLD: LiveData<List<OutletVisibility>>

    @get:Query("select * from outlet_visibility")
    val allData: List<OutletVisibility>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<OutletVisibility>)

    @Query("Delete from outlet_visibility")
    fun deleteAll()

}
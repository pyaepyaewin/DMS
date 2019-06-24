package com.aceplus.data.database.dao.outlet

import com.aceplus.domain.entity.outlet.OutletVisibility
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

@Dao
interface OutletVisibilityDao {

    @get:Query("select * from outlet_visibility")
    val allDataLD: LiveData<List<OutletVisibility>>

    @get:Query("select * from outlet_visibility")
    val allData: List<OutletVisibility>

    @get:Query("select * from outlet_visibility WHERE delete_flag = 0")
    val allActiveData: List<OutletVisibility>

    @get:Query("select count(*) from outlet_visibility")
    val dataCount: Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<OutletVisibility>)

    @Query("Delete from outlet_visibility")
    fun deleteAll()

    @Query("update outlet_visibility set delete_flag = 1 WHERE delete_flag = 0")
    fun updateAllInactiveData()

}
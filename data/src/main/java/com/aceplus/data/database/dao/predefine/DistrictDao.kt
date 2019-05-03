package com.aceplus.data.database.dao.predefine

import com.aceplus.domain.entity.predefine.District
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface DistrictDao{

    @get:Query("select * from district")
    val allDataLD: LiveData<List<District>>

    @get:Query("select * from district")
    val allData: List<District>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<District>)

    @Query("Delete from district")
    fun deleteAll()

}
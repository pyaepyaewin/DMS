package com.aceplus.data.database.dao.predefine

import com.aceplus.domain.entity.predefine.Street
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface StreetDao{

    @get:Query("select * from street")
    val allDataLD: LiveData<List<Street>>

    @get:Query("select * from street")
    val allData: List<Street>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<Street>)

    @Query("Delete from street")
    fun deleteAll()

}
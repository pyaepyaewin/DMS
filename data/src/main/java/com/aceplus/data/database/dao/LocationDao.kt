package com.aceplus.data.database.dao

import com.aceplus.domain.entity.Location
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface LocationDao{

    @get:Query("select * from location")
    val allDataLD: LiveData<List<Location>>

    @get:Query("select * from location")
    val allData: List<Location>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<Location>)

    @Query("Delete from location")
    fun deleteAll()

}
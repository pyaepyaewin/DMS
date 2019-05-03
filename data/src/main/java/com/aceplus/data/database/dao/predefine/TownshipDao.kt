package com.aceplus.data.database.dao.predefine

import com.aceplus.domain.entity.predefine.Township
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface TownshipDao{

    @get:Query("select * from township")
    val allDataLD: LiveData<List<Township>>

    @get:Query("select * from township")
    val allData: List<Township>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<Township>)

    @Query("Delete from township")
    fun deleteAll()

}
package com.aceplus.data.database.dao.predefine

import com.aceplus.domain.entity.predefine.StateDivision
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface StateDivisionDao{

    @get:Query("select * from state_division")
    val allDataLD: LiveData<List<StateDivision>>

    @get:Query("select * from state_division")
    val allData: List<StateDivision>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<StateDivision>)

    @Query("Delete from state_division")
    fun deleteAll()

}
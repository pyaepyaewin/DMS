package com.aceplus.data.database.dao.route

import com.aceplus.domain.entity.route.RouteSchedule
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface RouteScheduleDao{

    @get:Query("select * from route_schedule")
    val allDataLD: LiveData<List<RouteSchedule>>

    @get:Query("select * from route_schedule")
    val allData: List<RouteSchedule>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<RouteSchedule>)

    @Query("Delete from route_schedule")
    fun deleteAll()

}
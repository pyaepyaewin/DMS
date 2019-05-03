package com.aceplus.data.database.dao.route

import com.aceplus.domain.entity.route.RouteScheduleItem
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface RouteScheduleItemDao{

    @get:Query("select * from route_schedule_item")
    val allDataLD: LiveData<List<RouteScheduleItem>>

    @get:Query("select * from route_schedule_item")
    val allData: List<RouteScheduleItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<RouteScheduleItem>)

    @Query("Delete from route_schedule_item")
    fun deleteAll()

}
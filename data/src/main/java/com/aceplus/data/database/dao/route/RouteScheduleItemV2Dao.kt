package com.aceplus.data.database.dao.route

import com.aceplus.domain.entity.route.RouteScheduleItemV2
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface RouteScheduleItemV2Dao {

    @get:Query("select * from route_schedule_item_v2")
    val allDataLD: LiveData<List<RouteScheduleItemV2>>

    @get:Query("select * from route_schedule_item_v2")
    val allData: List<RouteScheduleItemV2>

    @Query("select * from route_schedule_item_v2 where route_schedule_id = :routeScheduleId")
    fun allDataByRouteScheduleId(routeScheduleId: String): List<RouteScheduleItemV2>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<RouteScheduleItemV2>)

    @Query("Delete from route_schedule_item_v2")
    fun deleteAll()

}
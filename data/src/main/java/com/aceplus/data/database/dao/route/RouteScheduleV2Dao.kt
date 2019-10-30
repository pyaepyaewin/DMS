package com.aceplus.data.database.dao.route

import com.aceplus.domain.entity.route.RouteScheduleV2
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface RouteScheduleV2Dao {

    @get:Query("select * from route_schedule_v2")
    val allDataLD: LiveData<List<RouteScheduleV2>>

    @get:Query("select * from route_schedule_v2")
    val allData: List<RouteScheduleV2>

    @Query("select * from route_schedule_v2 where sale_man_id = :saleManId")
    fun dataBySaleManId(saleManId: String): RouteScheduleV2

    @Query("select id from route_schedule_v2 where sale_man_id = :saleManId")
    fun getRouteId(saleManId: String): List<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<RouteScheduleV2>)

    @Query("Delete from route_schedule_v2")
    fun deleteAll()

}
package com.aceplus.data.database.dao.route

import com.aceplus.domain.entity.route.RouteAssign
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface RouteAssignDao{

    @get:Query("select * from route_assign")
    val allDataLD: LiveData<List<RouteAssign>>

    @get:Query("select * from route_assign")
    val allData: List<RouteAssign>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<RouteAssign>)

    @Query("Delete from route_assign")
    fun deleteAll()

}
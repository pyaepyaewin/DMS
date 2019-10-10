package com.aceplus.data.database.dao.route

import com.aceplus.domain.entity.route.Route
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.aceplus.domain.model.route.Route_Township
import com.aceplus.domain.model.routedataclass.TownshipDataClass


@Dao
interface RouteDao{

    @get:Query("select * from route")
    val allDataLD: LiveData<List<Route>>

    @get:Query("select * from route")
    val allData: List<Route>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<Route>)

    @Query("Delete from route")
    fun deleteAll()

    @Query("select township.id,township.township_name from township")
    fun getTownShipList():List<TownshipDataClass>

}
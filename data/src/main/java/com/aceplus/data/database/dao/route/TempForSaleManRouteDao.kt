package com.aceplus.data.database.dao.route

import com.aceplus.domain.entity.route.TempForSaleManRoute
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface TempForSaleManRouteDao{

    @get:Query("select * from temp_for_sale_man_route")
    val allDataLD: LiveData<List<TempForSaleManRoute>>

    @get:Query("select * from temp_for_sale_man_route")
    val allData: List<TempForSaleManRoute>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<TempForSaleManRoute>)

    @Query("Delete from temp_for_sale_man_route")
    fun deleteAll()

}
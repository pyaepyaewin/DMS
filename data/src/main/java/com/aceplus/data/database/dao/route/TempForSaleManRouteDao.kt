package com.aceplus.data.database.dao.route

import com.aceplus.domain.entity.route.TempForSaleManRoute
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface TempForSaleManRouteDao {

    @get:Query("select * from temp_for_sale_man_route")
    val allDataLD: LiveData<List<TempForSaleManRoute>>

    @get:Query("select * from temp_for_sale_man_route")
    val allData: List<TempForSaleManRoute>

    @Query("select * from temp_for_sale_man_route where sale_man_id=:saleManId and customer_id=:customerId")
    fun dataById(saleManId: String, customerId: String): List<TempForSaleManRoute>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<TempForSaleManRoute>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(tempForSaleManRoute: TempForSaleManRoute)

    @Query("Delete from temp_for_sale_man_route")
    fun deleteAll()

    @Query("Update temp_for_sale_man_route set  arrival_time=:currentDate and departure_time=:currentDate where customer_id=:customerId and sale_man_id=:saleManId")
    fun updateArrivalAndDepartureTime(saleManId: String, customerId: String, currentDate: String)

    @Query("Update temp_for_sale_man_route set departure_time = :currentDate where customer_id = :customerId and sale_man_id = :saleManId")
    fun updateDepartureTime(saleManId: String, customerId: String, currentDate: String)

}
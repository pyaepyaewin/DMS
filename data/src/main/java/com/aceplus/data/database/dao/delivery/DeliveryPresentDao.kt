package com.aceplus.data.database.dao.delivery

import com.aceplus.domain.entity.delivery.DeliveryPresent
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface DeliveryPresentDao {

    @get:Query("select * from delivery_present")
    val allDataLD: LiveData<List<DeliveryPresent>>

    @get:Query("select * from delivery_present")
    val allData: List<DeliveryPresent>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<DeliveryPresent>)

    @Query("Delete from delivery_present")
    fun deleteAll()

}
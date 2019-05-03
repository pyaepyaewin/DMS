package com.aceplus.data.database.dao.delivery

import com.aceplus.domain.entity.delivery.Delivery
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface DeliveryDao {

    @get:Query("select * from delivery")
    val allDataLD: LiveData<List<Delivery>>

    @get:Query("select * from delivery")
    val allData: List<Delivery>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<Delivery>)

    @Query("Delete from delivery")
    fun deleteAll()

}
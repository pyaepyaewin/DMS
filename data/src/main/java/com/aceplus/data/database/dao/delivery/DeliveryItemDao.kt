package com.aceplus.data.database.dao.delivery

import com.aceplus.domain.entity.delivery.DeliveryItem
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.aceplus.domain.model.delivery.DeliverItem


@Dao
interface DeliveryItemDao {

    @get:Query("select * from delivery_item")
    val allDataLD: LiveData<List<DeliveryItem>>

    @get:Query("select * from delivery_item")
    val allData: List<DeliveryItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<DeliveryItem>)

    @Query("Delete from delivery_item")
    fun deleteAll()

    @Query("select * from delivery_item where delivery_flag = 0 and id =  :deliveryId and received_quantity <> order_quantity ")
    fun deliveryItemData(deliveryId:Int): List<DeliveryItem>

}
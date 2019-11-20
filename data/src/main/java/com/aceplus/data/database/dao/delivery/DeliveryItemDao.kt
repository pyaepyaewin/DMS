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

    @Query("select * from delivery_item where delivery_item.delivery_id =  :deliveryId and received_quantity <> order_quantity ")
    fun deliveryItemData(deliveryId:Int): List<DeliveryItem>

    //@Query("update delivery_item set received_quantity =  string(:soldQty), order_quantity = string(int(order_quantity) - :soldQty) where delivery_item.stock_id = :stockID")
   // fun updateDeliveryItemQty(soldQty: Int, stockID: String):DeliveryItem

    @Query("update delivery_item set delivery_flag = 1  where order_quantity <  1 and stock_id = :stockId")
    fun updateCheckDeliveryItem(stockId: String)

}
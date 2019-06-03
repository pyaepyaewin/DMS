package com.aceplus.data.database.dao.delivery

import com.aceplus.domain.entity.delivery.DeliveryItemUpload
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface DeliveryItemUploadDao {

    @get:Query("select * from delivery_item_upload")
    val allDataLD: LiveData<List<DeliveryItemUpload>>

    @get:Query("select * from delivery_item_upload")
    val allData: List<DeliveryItemUpload>

    @Query("select * from delivery_item_upload where delivery_id = :invoice_no")
    fun allDataById(invoice_no: Int): List<DeliveryItemUpload>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<DeliveryItemUpload>)

    @Query("Delete from delivery_item_upload")
    fun deleteAll()


}
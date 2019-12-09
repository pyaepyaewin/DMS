package com.aceplus.data.database.dao.delivery

import com.aceplus.domain.entity.delivery.DeliveryItemUpload
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.aceplus.domain.vo.report.DeliverDetailReport
import com.aceplus.domain.vo.report.TotalAmountForDeliveryReport


@Dao
interface DeliveryItemUploadDao {

    @get:Query("select * from delivery_item_upload")
    val allDataLD: LiveData<List<DeliveryItemUpload>>

    @get:Query("select * from delivery_item_upload")
    val allData: List<DeliveryItemUpload>

    @Query("select * from delivery_item_upload where delivery_id = :invoice_no")
    fun allDataById(invoice_no: String): List<DeliveryItemUpload>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<DeliveryItemUpload>)

    @Query("Delete from delivery_item_upload")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDeliveryItemUpload(cvDeliveryUploadItem: DeliveryItemUpload)

    @Query("select product_name,quantity from product inner  join delivery_item_upload on delivery_item_upload.stock_id = product.id where delivery_id = :invoiceId")
    fun getDeliverDetailReport(invoiceId:String): List<DeliverDetailReport>

    @Query("select * from delivery_item_upload where delivery_id in (:invoiceNo)")
    fun getDeliveryItemList(invoiceNo: List<String>): List<DeliveryItemUpload>

    @Query("select delivery_id,quantity,selling_price from delivery_item_upload left join product on product.id = delivery_item_upload.stock_id where delivery_id in (:list)")
    fun getQtyAndAmount(list: List<String>): List<TotalAmountForDeliveryReport>
}
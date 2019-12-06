package com.aceplus.data.database.dao.delivery

import com.aceplus.domain.entity.delivery.DeliveryUpload
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.aceplus.domain.vo.report.DeliverReport
import com.aceplus.domain.vo.report.IncompleteDeliverReport


@Dao
interface DeliveryUploadDao {

    @get:Query("select * from delivery_upload")
    val allDataLD: LiveData<List<DeliveryUpload>>

    @get:Query("select * from delivery_upload")
    val allData: List<DeliveryUpload>

    @get:Query("select count(*) from delivery_upload")
    val dataCount: Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<DeliveryUpload>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(deliveryUpload: DeliveryUpload)

    @Query("Delete from delivery_upload")
    fun deleteAll()

    @Query("select delivery_upload.invoice_no,customer_name,address from delivery_upload inner join customer on customer.id = delivery_upload.customer_id")
    fun getIncompleteDeliverReport(): List<IncompleteDeliverReport>

    @Query("select * from delivery_upload WHERE  customer_id BETWEEN :fromCusNo AND :toCusNo and date(invoice_date) = date(:newDate)")
    fun getSaleVisitForDeliveryUpload(fromCusNo: Int, toCusNo: Int, newDate: String): List<DeliveryUpload>

}
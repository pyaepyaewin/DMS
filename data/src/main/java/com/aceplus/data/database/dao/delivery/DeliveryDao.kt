package com.aceplus.data.database.dao.delivery

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.aceplus.domain.entity.delivery.Delivery
import com.aceplus.domain.model.report.DeliverDetailReport
import com.aceplus.domain.model.report.DeliverReport


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

    @Query("select invoice.invoice_id,customer_name,address,total_quantity,total_amount from invoice inner  join customer on customer.customer_id = invoice.customer_id inner join delivery on delivery.invoice_no = invoice.invoice_id")
    fun getDeliverReport(): List<DeliverReport>

    @Query("select product_name,total_quantity from product inner  join delivery on delivery.invoice_no = :invoiceId")
    fun getDeliverDetailReport(invoiceId:String): List<DeliverDetailReport>

}
package com.aceplus.data.database.dao.delivery

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.aceplus.domain.entity.delivery.Delivery
import com.aceplus.domain.model.delivery.Deliver
import com.aceplus.domain.vo.customer.DeliveryVO
import com.aceplus.domain.vo.report.DeliverDetailReport
import com.aceplus.domain.vo.report.DeliverReport


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

    @Query("select customer.id as CID ,customer.customer_name,customer.address,delivery.id as DID,delivery.invoice_no,delivery.amount,delivery.paid_amount,delivery.discount,delivery.discount_percent,delivery.sale_man_id,delivery.remark from customer inner  join delivery on delivery.customer_id = customer.id")
    fun getDeliveryData(): List<DeliveryVO>

    @Query("select * from delivery where date(invoice_date) = date(:now) and paid_amount > 0")
    fun getTotalSaleListForEndOdDay(now: String): List<Delivery>

}
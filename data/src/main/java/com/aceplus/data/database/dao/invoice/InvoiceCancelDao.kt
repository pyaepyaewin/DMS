package com.aceplus.data.database.dao.invoice

import com.aceplus.domain.entity.invoice.InvoiceCancel
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.aceplus.domain.model.report.SalesCancelReport


@Dao
interface InvoiceCancelDao {

    @get:Query("select * from invoice_cancel")
    val allDataLD: LiveData<List<InvoiceCancel>>

    @get:Query("select * from invoice_cancel")
    val allData: List<InvoiceCancel>

    @get:Query("select * from invoice_cancel WHERE sale_date >= DATE('now')")
    val allActiveData: List<InvoiceCancel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<InvoiceCancel>)

    @Query("Delete from invoice_cancel")
    fun deleteAll()

    @Query("select invoice_cancel.invoice_id,customer_name,total_amount,total_discount_amount from invoice_cancel inner join customer on customer.id = invoice_cancel.customer_id")
    fun getSalesCancelReport(): List<SalesCancelReport>


}
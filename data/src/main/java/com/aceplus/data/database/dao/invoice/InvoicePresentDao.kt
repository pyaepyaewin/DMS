package com.aceplus.data.database.dao.invoice

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.aceplus.domain.entity.invoice.InvoicePresent
import com.aceplus.domain.vo.report.SaleInvoiceReport


@Dao
interface InvoicePresentDao {

    @get:Query("select * from invoice_present")
    val allDataLD: LiveData<List<InvoicePresent>>

    @get:Query("select * from invoice_present")
    val allData: List<InvoicePresent>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<InvoicePresent>)

    @Query("Delete from invoice_present")
    fun deleteAll()

    @Query("select invoice.invoice_id,customer_name,address,total_amount,total_discount_amount,invoice.sale_date from invoice inner join customer on customer.id = invoice.customer_id where invoice.sale_flag = 1 and invoice.invoice_id not like 'SX%' and invoice.invoice_id not like 'OS%'")
    fun getSaleInvoiceReport(): List<SaleInvoiceReport>

    @Query("Delete from invoice_present where tsale_id=:invoiceID")
    fun deleteAll(invoiceID:String)

    @Query("select invoice.invoice_id,customer_name,address,total_amount,total_discount_amount,invoice.sale_date from invoice inner join customer on customer.id = invoice.customer_id where invoice.invoice_id like 'SX%'")
    fun getSaleExchangeTab2Report(): List<SaleInvoiceReport>



}
package com.aceplus.data.database.dao.invoice

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.aceplus.domain.entity.invoice.Invoice
import com.aceplus.domain.vo.report.SaleInvoiceReport

@Dao
interface InvoiceDao {

    @get:Query("select * from invoice")
    val allDataLD: LiveData<List<Invoice>>

    @get:Query("select * from invoice")
    val allData: List<Invoice>

    @get:Query("select * from invoice where sale_flag = 1")
    val activeData: List<Invoice>

    @get:Query("select count(*) from invoice WHERE invoice_id LIKE 'SX%'")
    val dataCountForSaleExchange: Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(invoice: Invoice)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<Invoice>)

    @Query("Delete from invoice")
    fun deleteAll()

    @Query("select invoice.invoice_id,customer_name,address,total_amount,total_discount_amount,invoice.sale_date from invoice inner join customer on customer.id = invoice.customer_id where invoice.sale_flag = 0 and invoice.invoice_id not like 'SX%' and invoice.invoice_id not like 'OS%'")
    fun getSaleHistoryReport(): List<SaleInvoiceReport>

    @Query("select invoice.invoice_id,customer_name,address,total_amount,total_discount_amount,invoice.sale_date from invoice inner join customer on customer.id = invoice.customer_id where date(invoice.sale_date) between date(:fromDate) and date(:toDate)")
    fun getSaleHistoryReportForDate(fromDate: String, toDate: String): List<SaleInvoiceReport>

    @Query("select count(*) from invoice where invoice_id = :invoiceId")
    fun getInvoiceCountByID(invoiceId: String): Int

}
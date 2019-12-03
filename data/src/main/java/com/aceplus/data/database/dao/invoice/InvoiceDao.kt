package com.aceplus.data.database.dao.invoice

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.aceplus.domain.entity.invoice.Invoice
import com.aceplus.domain.model.sale.salecancel.SaleCancelItem
import com.aceplus.domain.vo.report.SaleInvoiceReport
import android.arch.persistence.room.Update



@Dao
interface InvoiceDao {

    @get:Query("select * from invoice")
    val allDataLD: LiveData<List<Invoice>>

    @get:Query("select * from invoice")
    val allData: List<Invoice>

    @get:Query("select * from invoice")
    val allDatas: Invoice

    @get:Query("select * from invoice where sale_flag = 1")
    val activeData: List<Invoice>

    @Update
    fun update(invoice: Invoice)


    @get:Query("select count(*) from invoice WHERE invoice_id LIKE 'SX%'")
    val dataCountForSaleExchange: Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(invoice: Invoice)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<Invoice>)

    @Query("Delete from invoice")
    fun deleteAll()

    @Query("select invoice.invoice_id,customer_name,address,total_amount,total_discount_amount,invoice.sale_date from invoice inner join customer on customer.id = invoice.customer_id where invoice.sale_flag = 1 and invoice.invoice_id not like 'SX%' and invoice.invoice_id not like 'OS%'")
    fun getSaleHistoryReport(): List<SaleInvoiceReport>

    @Query("select invoice.invoice_id,customer_name,address,total_amount,total_discount_amount,invoice.sale_date from invoice inner join customer on customer.id = invoice.customer_id where date(invoice.sale_date) between date(:fromDate) and date(:toDate)")
    fun getSaleHistoryReportForDate(fromDate: String, toDate: String): List<SaleInvoiceReport>

    @Query("select * from invoice where invoice_id = :invoiceId")
    fun getSaleHistoryReportForPrint(invoiceId:String):Invoice

    @Query("select count(*) from invoice where invoice_id = :invoiceId")
    fun getInvoiceCountByID(invoiceId: String): Int

    @Query("select customer.customer_name,customer.id,invoice.invoice_id,invoice.sale_date,invoice.total_quantity,invoice.total_amount from invoice,customer where date(invoice.sale_date) = date('now') and invoice.invoice_id NOT LIKE 'OS%' and invoice.customer_id=customer.id")
    fun getSaleCancelList():List<SaleCancelItem>

    @Query("Delete from  invoice where invoice_id=:invoiceId")
    fun deleteAll(invoiceId:String)

    @Query("UPDATE invoice SET total_quantity = :totalQty WHERE  invoice_product_id=:invoiceId")
    fun updateTotalQtyForInvoice(invoiceId: String,totalQty:Int)

    @Query("select * from invoice WHERE  invoice_id=:invoiceId")
    fun getSoldInvoice(invoiceId: String):List<Invoice>



    @Query("select * from invoice WHERE  invoice_id=:invoiceId")
    fun getInvoiceCancel(invoiceId: String):Invoice

    @Query("select * from invoice WHERE  customer_id BETWEEN :fromCusNo AND :toCusNo and date(sale_date) = date(:newDate)")
    fun getSaleVisitFromAndToCustomer(fromCusNo: Int, toCusNo: Int, newDate: String): List<Invoice>

    @Query("select * from invoice where date(sale_date) = date(:now) and pay_amount > 0")
    fun getInvoiceListForEndOfDay(now: String): List<Invoice>

    @Query("select * from invoice where date(sale_date) = date(:now) and invoice_id like 'SX%'")
    fun getSaleExchangeListForEndOfDay(now: String): List<Invoice>?

    @Query("select * from invoice where date(sale_date) = date(:now)")
    fun getSaleCountForEndOfDay(now: String): List<Invoice>?

    @Query("select * from invoice where date(sale_date) = date(:now) order by sale_date asc  limit 1")
    fun getStartTime(now: String): List<Invoice>?


}
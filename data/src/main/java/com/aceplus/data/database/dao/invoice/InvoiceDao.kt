package com.aceplus.data.database.dao.invoice

import com.aceplus.domain.entity.invoice.Invoice
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.aceplus.domain.vo.report.SaleInvoiceReport
import com.aceplus.domain.vo.report.SaleTargetVO

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
    fun insertAll(list: List<Invoice>)

    @Query("Delete from invoice")
    fun deleteAll()

    @Query("select invoice.invoice_id,customer_name,address,total_amount,total_discount_amount from invoice inner join customer on customer.id = invoice.customer_id")
    fun getSaleInvoiceReport(): List<SaleInvoiceReport>

}
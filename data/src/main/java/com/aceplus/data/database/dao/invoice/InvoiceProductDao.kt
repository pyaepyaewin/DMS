package com.aceplus.data.database.dao.invoice

import com.aceplus.domain.entity.invoice.InvoiceProduct
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.aceplus.domain.vo.report.SaleInvoiceDetailReport


@Dao
interface InvoiceProductDao {

    @get:Query("select * from invoice_product")
    val allDataLD: LiveData<List<InvoiceProduct>>

    @get:Query("select * from invoice_product")
    val allData: List<InvoiceProduct>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<InvoiceProduct>)

    @Insert
    fun insert(invoiceProduct: InvoiceProduct)

    @Query("Delete from invoice_product")
    fun deleteAll()

    @Query("select * from invoice_product WHERE invoice_product_id = :invoice_id")
    fun allDataById(invoice_id: String): List<InvoiceProduct>

    @Query("select product.product_name,invoice_product.sale_quantity,invoice_product.discount_amount,invoice_product.total_amount from invoice_product inner join product on product.id = invoice_product.product_id where invoice_product.invoice_product_id == :invoiceId")
    fun getSaleInvoiceDetailReport(invoiceId:String): List<SaleInvoiceDetailReport>

}
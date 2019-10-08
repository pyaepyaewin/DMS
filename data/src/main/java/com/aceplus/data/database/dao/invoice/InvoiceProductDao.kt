package com.aceplus.data.database.dao.invoice

import com.aceplus.domain.entity.invoice.InvoiceProduct
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.aceplus.domain.model.report.SaleInvoiceDetailReport
import io.reactivex.Observable


@Dao
interface InvoiceProductDao {

    @get:Query("select * from invoice_product")
    val allDataLD: LiveData<List<InvoiceProduct>>

    @get:Query("select * from invoice_product")
    val allData: List<InvoiceProduct>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<InvoiceProduct>)

    @Query("Delete from invoice_product")
    fun deleteAll()

    @Query("select * from invoice_product WHERE invoice_product_id = :invoice_id")
    fun allDataById(invoice_id: String): List<InvoiceProduct>

    @Query("select product.product_name,product.sold_quantity,invoice_product.discount_amount,invoice_product.total_amount from product inner join invoice_product on invoice_product.product_id = product.id inner join invoice  where invoice.invoice_id == :invoiceId")
    fun getSaleInvoiceDetailReport(invoiceId:String): List<SaleInvoiceDetailReport>

}
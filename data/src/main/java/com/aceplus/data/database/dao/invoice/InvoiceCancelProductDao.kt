package com.aceplus.data.database.dao.invoice

import com.aceplus.domain.entity.invoice.InvoiceCancelProduct
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.aceplus.domain.vo.report.SaleInvoiceDetailReport

@Dao
interface InvoiceCancelProductDao {

    @get:Query("select * from invoice_cancel_product")
    val allDataLD: LiveData<List<InvoiceCancelProduct>>

    @get:Query("select * from invoice_cancel_product")
    val allData: List<InvoiceCancelProduct>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<InvoiceCancelProduct>)

    @Query("Delete from invoice_cancel_product")
    fun deleteAll()

    @Query("select * from invoice_cancel_product where invoice_product_id=:id")
    fun allDataById(id: String?): List<InvoiceCancelProduct>

    @Query("select product.product_name,product.sold_quantity,invoice_cancel_product.discount_amount,invoice_cancel_product.total_amount from product inner join invoice_cancel_product on invoice_cancel_product.product_id = product.id where invoice_cancel_product.invoice_product_id == :invoiceId")
    fun getSaleCancelDetailReport(invoiceId:String): List<SaleInvoiceDetailReport>


}
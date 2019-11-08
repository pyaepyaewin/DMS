package com.aceplus.data.database.dao.invoice

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.aceplus.domain.entity.invoice.InvoiceProduct
import com.aceplus.domain.model.SoldProduct
import com.aceplus.domain.model.sale.salecancel.SaleCancelDetailItem
import com.aceplus.domain.model.sale.salecancel.SoldProductDataClass
import com.aceplus.domain.vo.SoldProductInfo
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
    fun getSaleInvoiceDetailReport(invoiceId: String): List<SaleInvoiceDetailReport>

    @Query("select IP.invoice_product_id from invoice_product as IP,invoice as I WHERE IP.invoice_product_id=I.invoice_product_id and I.invoice_id = :invoiceID")
    fun getProductIdList(invoiceID:String):List<String>

//    @Query("select P.product_name,P.um,IP.sale_quantity,IP.s_price,IP.promotion_price from invoice_product as IP LEFT JOIN product as P ON P.id=IP.product_id where IP.invoice_product_id=:invoiceId")
//    fun getSaleCancelDetailList(invoiceId:String):List<SoldProductInfo>

}
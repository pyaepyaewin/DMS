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
import com.aceplus.domain.vo.report.TargetAndActualSaleForProduct
import com.aceplus.domain.vo.report.TargetAndSaleForSaleMan


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

    @Query("select product.id,product.product_id,product.selling_price,product.purchase_price,product.discount_type,product.remaining_quantity,product.category_id,product.group_id,product.class_id,product.um,product.product_name,invoice_product.sale_quantity,invoice_product.discount_amount,invoice_product.total_amount,invoice_product.s_price,invoice_product.discount_percent,invoice_product.promotion_price,invoice_product.item_discount_amount,invoice_product.item_discount_percent from product inner join invoice_product on product.id = invoice_product.product_id where invoice_product.invoice_product_id == :invoiceId")
    fun getSaleInvoiceDetailReport(invoiceId: String): List<SaleInvoiceDetailReport>

    @Query("select IP.product_id from invoice_product as IP,invoice as I WHERE IP.invoice_product_id=I.invoice_id and I.invoice_id = :invoiceID")
    fun getProductIdList(invoiceID:String):List<String>

    @Query("Delete from invoice_product where invoice_product_id=:invoiceId")
    fun deleteAll(invoiceId:String)

    @Query("UPDATE invoice_product SET sale_quantity=:qty WHERE product_id=:productId and invoice_product_id=:invoiceId")
    fun updateQtyForInvoiceProduct(invoiceId: String,productId: String,qty:Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDeliveryData(cvInvoiceProduct: InvoiceProduct)

    @Query("Delete from invoice_product where invoice_product_id=:invoiceId and id in (:productIdList)")
    fun deleteInvoiceProductForLongClick(invoiceId:String,productIdList: List<Int>)

    @Query("select * from invoice_product where product_id = :id group by product_id")
    fun getInvoiceProductList(id: String): List<InvoiceProduct>?

    @Query(" select distinct(invoice_product.total_amount),product.product_id,invoice_product.sale_quantity from invoice_product,product where product.id = invoice_product.product_id and  product.category_id = :categoryId")
    fun getCategoryListFromInvoiceProduct(categoryId: String): List<TargetAndSaleForSaleMan>?

    @Query(" select distinct(invoice_product.total_amount),product.product_id,invoice_product.sale_quantity from invoice_product,product where product.id = invoice_product.product_id and  product.group_id = :groupId")
    fun getGroupListFromInvoiceProduct(groupId: String): List<TargetAndSaleForSaleMan>?

    @Query(" select distinct(invoice_product.total_amount),product.product_id,invoice_product.sale_quantity from invoice_product,product,invoice where product.id = invoice_product.product_id and  invoice.customer_id = :customerId")
    fun getCustomerSaleTargetAndSaleIdList(customerId: String): List<TargetAndSaleForSaleMan>?

//    @Query("select P.product_name,P.um,IP.sale_quantity,IP.s_price,IP.promotion_price from invoice_product as IP LEFT JOIN product as P ON P.id=IP.product_id where IP.invoice_product_id=:invoiceId")
//    fun getSaleCancelDetailList(invoiceId:String):List<SoldProductInfo>

}
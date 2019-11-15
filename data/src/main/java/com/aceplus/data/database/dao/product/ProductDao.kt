package com.aceplus.data.database.dao.product

import com.aceplus.domain.entity.product.Product
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.aceplus.domain.model.sale.salecancel.SaleCancelDetailItem
import com.aceplus.domain.model.sale.salecancel.SoldProductDataClass
import com.aceplus.domain.vo.report.ProductBalanceReport


@Dao
interface ProductDao {

    @get:Query("select * from product")
    val allDataLD: LiveData<List<Product>>

    @get:Query("select * from product")
    val allData: List<Product>

    @get:Query("select p.id, p.product_id, p.product_name, p.category_id, p.group_id, p.total_quantity, p.remaining_quantity, p.selling_price, p.purchase_price, p.discount_type, u.name as um, p.sold_quantity, p.order_quantity, p.exchange_quantity, p.return_quantity, p.delivery_quantity, p.present_quantity, p.device_issue_status, p.class_id from product as p, um as u where p.um = u.id")
    val allProductData: List<Product>
//
//    @Query("select invoice_product.product_id from invoice_product,invoice where invoice_product.invoice_product_id= invoice.invoice_product_id and invoice.invoice_id=:invoiceId")
//    fun getProductId(invoiceId:String):List<String>

    @Query("SELECT * FROM product WHERE id = :productID")
    fun getProductByID(productID: Int): List<Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<Product>)

    @Query("update product set remaining_quantity = remaining_quantity - :soldQty, sold_quantity = sold_quantity + :soldQty where product.id = :productID")
    fun updateProductRemainingQty(soldQty: Int, productID: Int)

    @Query("Delete from product")
    fun deleteAll()

    @Query("select product_name,total_quantity,order_quantity,sold_quantity,exchange_quantity,return_quantity,delivery_quantity,present_quantity,remaining_quantity from product")
    fun getProductBalanceReport(): List<ProductBalanceReport>

    @Query("select p.id, p.product_id, p.product_name, p.category_id, p.group_id, p.total_quantity, p.remaining_quantity, p.selling_price, p.purchase_price, p.discount_type, u.name as um, p.sold_quantity, p.order_quantity, p.exchange_quantity, p.return_quantity, p.delivery_quantity, p.present_quantity, p.device_issue_status, p.class_id,IP.promotion_price ,IP.sale_quantity from product as p, um as u,invoice_product as IP where p.um = u.id and p.id in (:productIDList) and p.id=IP.product_id")
    fun allProductData(productIDList: List<String>): List<SaleCancelDetailItem>

    @Query("select * from product where id = :stockId")
    fun deliveryProductDataList(stockId:String): List<Product>


}
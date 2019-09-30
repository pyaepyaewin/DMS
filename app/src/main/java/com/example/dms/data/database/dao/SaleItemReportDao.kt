package com.example.dms.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.dms.data.database.table.InvoiceItemReport
import com.example.dms.data.database.table.InvoiceReport
import io.reactivex.Observable

@Dao
interface SaleItemReportDao {
//    @Query("select checkOut.product_id, checkOut.um, checkOut.qty,checkOut.price, checkOut.promoPrice,
//            checkOut.amount from checkOut,product where " +
//        "checkOut.product_id = product.Product_id and checkOut.invoice_ID = :invoiceID")
//    fun getSaleItemReport(invoiceID: String): Observable<List<InvoiceItemReport>>

    //    val Id: String,
//    val Product_id: String,
//    val Product_name: String,
//    val category_id: String,
//    val class_id: String,
//    val device_issue_status: Int,
//    val group_id: String,
//    val product_type_id: String,
//    val purchase_price: String,
//    val remaining_qty: Int,
//    val selling_price: String,
//    val total_qty: Int,
//    val um_id: String
//    @Query("select * from product where Product_id = :invoiceID")
//    fun getSaleItemReport(invoiceID: String): Observable<List<InvoiceItemReport>>
    @Query("select Product_name,um,qty,price,promoPrice,amount from"+"product inner join invoice_item on" +
            " product.Product_id=invoice_item.productId where invoice_item.invoiceId=:invoiceID")
    fun getSaleItemReport(invoiceID: String): Observable<List<InvoiceItemReport>>

}


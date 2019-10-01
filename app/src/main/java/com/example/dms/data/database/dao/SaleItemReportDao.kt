package com.example.dms.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.dms.data.database.table.InvoiceItem
import com.example.dms.data.database.table.InvoiceItemReport
import com.example.dms.data.database.table.InvoiceReport
import io.reactivex.Observable

@Dao
interface SaleItemReportDao {
//    @Query("select Product_name,um,qty,price,promoPrice,amount from product inner join invoice_item on product.Product_id=invoice_item.productId where invoice_item.invoiceId=:invoiceID")
//    fun getSaleItemReport(invoiceID: String): Observable<List<InvoiceItemReport>>

//    @Query("select product.Product_name,invoice_item.um,invoice_item.qty,invoice_item.price from product,invoice_item on product.Product_id=invoice_item.productId where invoice_item.invoiceId=:invoiceID")
//    fun getSaleItemReport(invoiceID: String): Observable<List<InvoiceItemReport>>


    @Query("select product.Product_name,invoice_item.um,invoice_item.qty,invoice_item.price from product,invoice_item where product.Product_id=invoice_item.productId and invoice_item.invoiceId=:invoiceID")
    fun getSaleItemReport(invoiceID: String): Observable<List<InvoiceItemReport>>

    @Query("select * from invoice_item")
    fun allData():List<InvoiceItem>
//
//}
//
//    @Query("select product.Product_name from invoice_item inner join product on invoice_item.productId == product.Id where invoice_item.invoiceId == :invoiceID")
//    fun getSaleItemReport(invoiceID: String): Observable<List<InvoiceItemReport>>
}
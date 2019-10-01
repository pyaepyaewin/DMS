package com.example.dms.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.dms.data.database.table.InvoiceReport
import io.reactivex.Observable
@Dao
interface SaleReportDao {

//    @Query("select id, CUSTOMER_NAME, date,netAmt as amount, discPercent as discountPrice, "+"discAmt as discountAmount from invoice inner join customer on customer. CUSTOMER_ID = invoice.cid")
//    fun getInvoiceReport(): Observable<List<InvoiceReport>>


    @Query("select invoice.id, customer.CUSTOMER_NAME, invoice.date,invoice.netAmt as amount, invoice.discPercent as discountPrice, "+"invoice.discAmt as discountAmount from invoice,customer where customer.CUSTOMER_ID = invoice.cid")
    fun getInvoiceReport(): Observable<List<InvoiceReport>>
}

package com.example.dms.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.dms.data.database.table.InvoiceReport
import io.reactivex.Observable
@Dao
interface SaleReportDao {

    @Query("select invoice.id, CUSTOMER_NAME, date,netAmt, discPercent, "+"discAmt from invoice inner join customer on customer. CUSTOMER_ID = invoice.cid")
    fun getInvoiceReport(): Observable<List<InvoiceReport>>
}

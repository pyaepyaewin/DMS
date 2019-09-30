package com.example.dms.data.repositories

import com.example.dms.data.database.table.CheckOut
import com.example.dms.data.database.table.InvoiceItemReport
import com.example.dms.data.database.table.InvoiceReport
import io.reactivex.Observable

interface ItemReportRepository {
    fun getSaleItemReport(invoiceID: String): Observable<List<InvoiceItemReport>>
}
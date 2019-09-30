package com.example.dms.data.repositories

import com.example.dms.data.database.MyDatabase
import com.example.dms.data.database.table.CheckOut
import com.example.dms.data.database.table.InvoiceItemReport
import com.example.dms.data.database.table.InvoiceReport
import io.reactivex.Observable

class ItemReportRepositoryImpl (private val database: MyDatabase): ItemReportRepository {
    override fun getSaleItemReport(invoiceID: String): Observable<List<InvoiceItemReport>> {
        return database.saleItemReportDao().getSaleItemReport(invoiceID)
    }


}
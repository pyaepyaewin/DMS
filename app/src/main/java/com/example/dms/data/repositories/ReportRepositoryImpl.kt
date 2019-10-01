package com.example.dms.data.repositories

import android.content.Context
import com.example.dms.data.database.MyDatabase
import com.example.dms.data.database.table.InvoiceReport
import io.reactivex.Observable

class ReportRepositoryImpl(val database:MyDatabase):ReportRepository {
    override fun getReportData(): Observable<List<InvoiceReport>> {
        val allInvoiceList = database.saleItemReportDao().allData()
        return database.saleReportDao().getInvoiceReport()
    }

}
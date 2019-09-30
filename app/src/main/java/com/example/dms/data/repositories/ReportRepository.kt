package com.example.dms.data.repositories

import com.example.dms.data.database.table.InvoiceReport
import io.reactivex.Observable

interface ReportRepository {
    fun getReportData(): Observable<List<InvoiceReport>>
}
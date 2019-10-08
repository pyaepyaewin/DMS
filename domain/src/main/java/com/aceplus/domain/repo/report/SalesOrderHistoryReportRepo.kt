package com.aceplus.domain.repo.report

import com.aceplus.domain.model.report.SalesOrderHistoryReport
import io.reactivex.Observable

interface SalesOrderHistoryReportRepo {
    fun salesOrderHistoryReport():Observable<List<SalesOrderHistoryReport>>
}
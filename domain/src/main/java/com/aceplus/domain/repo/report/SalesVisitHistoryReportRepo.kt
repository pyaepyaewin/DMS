package com.aceplus.domain.repo.report
import com.aceplus.domain.model.report.SalesVisitHistoryReport
import io.reactivex.Observable

interface SalesVisitHistoryReportRepo {
    fun salesVisitHistoryReport():Observable<List<SalesVisitHistoryReport>>
}
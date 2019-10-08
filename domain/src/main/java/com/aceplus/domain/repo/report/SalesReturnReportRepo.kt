package com.aceplus.domain.repo.report

import com.aceplus.domain.model.report.SalesReturnReport
import io.reactivex.Observable

interface SalesReturnReportRepo {
    fun salesReturnReport(): Observable<List<SalesReturnReport>>
}
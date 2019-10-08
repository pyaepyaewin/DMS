package com.aceplus.domain.repo.report

import com.aceplus.domain.model.report.SalesCancelReport
import io.reactivex.Observable

interface SalesCancelReportRepo {
    fun salesCancelReport(): Observable<List<SalesCancelReport>>
}
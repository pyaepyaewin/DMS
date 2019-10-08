package com.aceplus.domain.repo.report

import com.aceplus.domain.model.report.DeliverReport
import io.reactivex.Observable

interface DeliverReportRepo {
    fun deliverReport(): Observable<List<DeliverReport>>
}
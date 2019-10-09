package com.aceplus.domain.repo.report

import com.aceplus.domain.model.report.DeliverDetailReport
import com.aceplus.domain.model.report.DeliverReport
import io.reactivex.Observable

interface DeliverReportRepo {
    fun deliverReport(): Observable<List<DeliverReport>>
    fun deliverDetailReport(invoiceId:String): Observable<List<DeliverDetailReport>>

}
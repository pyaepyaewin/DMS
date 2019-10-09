package com.aceplus.domain.repo.report

import com.aceplus.domain.model.report.SaleInvoiceDetailReport
import com.aceplus.domain.model.report.SalesCancelReport
import io.reactivex.Observable

interface SalesCancelReportRepo {
    fun salesCancelReport(): Observable<List<SalesCancelReport>>
    fun salesCancelDetailReport(invoiceId: String): Observable<List<SaleInvoiceDetailReport>>
}
package com.aceplus.data.repoimpl.report

import com.aceplus.data.database.MyDatabase
import com.aceplus.domain.model.report.SaleInvoiceDetailReport
import com.aceplus.domain.model.report.SalesCancelReport
import com.aceplus.domain.repo.report.SalesCancelReportRepo
import io.reactivex.Observable

class SalesCancelReportRepoImpl(private val db:MyDatabase): SalesCancelReportRepo {
    override fun salesCancelReport(): Observable<List<SalesCancelReport>> {
        return Observable.just(db.invoiceCancelDao().getSalesCancelReport())
    }

    override fun salesCancelDetailReport(invoiceId: String): Observable<List<SaleInvoiceDetailReport>> {
        return Observable.just(db.invoiceCancelProductDao().getSaleCancelDetailReport(invoiceId))
    }

}
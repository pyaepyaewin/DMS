package com.aceplus.domain.repo.report

import com.aceplus.domain.model.report.SalesReturnDetailReport
import com.aceplus.domain.model.report.SalesReturnReport
import com.aceplus.domain.model.sale.salereturn.SaleReturnDetail
import com.aceplus.domain.model.sale.salereturn.SaleReturnDetailreport
import io.reactivex.Observable

interface SalesReturnReportRepo {
    fun salesReturnReport(): Observable<List<SalesReturnReport>>
    fun salesReturnDetailReport(invoiceId:String): Observable<List<SalesReturnDetailReport>>
}
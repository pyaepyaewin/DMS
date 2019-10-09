package com.aceplus.domain.repo.report

import com.aceplus.domain.model.report.PreOrderDetailReport
import com.aceplus.domain.model.report.PreOrderReport
import io.reactivex.Observable

interface PreOrderReportRepo {
    fun preOrderReport():Observable<List<PreOrderReport>>
    fun preOrderDetailReport(invoiceId:String):Observable<List<PreOrderDetailReport>>
}
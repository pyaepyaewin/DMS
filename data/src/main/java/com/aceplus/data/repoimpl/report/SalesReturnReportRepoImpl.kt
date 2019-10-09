package com.aceplus.data.repoimpl.report

import com.aceplus.data.database.MyDatabase
import com.aceplus.domain.model.report.SalesReturnDetailReport
import com.aceplus.domain.model.report.SalesReturnReport
import com.aceplus.domain.repo.report.SalesReturnReportRepo
import io.reactivex.Observable

class SalesReturnReportRepoImpl(private val db: MyDatabase) : SalesReturnReportRepo {
    override fun salesReturnReport(): Observable<List<SalesReturnReport>> {
        return Observable.just(db.saleReturnDao().getSalesReturnReport())
    }
    override fun salesReturnDetailReport(invoiceId: String): Observable<List<SalesReturnDetailReport>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
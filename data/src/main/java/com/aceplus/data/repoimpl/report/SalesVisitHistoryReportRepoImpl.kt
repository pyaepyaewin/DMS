package com.aceplus.data.repoimpl.report
import com.aceplus.data.database.MyDatabase
import com.aceplus.domain.model.report.SalesVisitHistoryReport
import com.aceplus.domain.repo.report.SalesVisitHistoryReportRepo

class SalesVisitHistoryReportRepoImpl(private val db: MyDatabase): SalesVisitHistoryReportRepo {
    override fun salesVisitHistoryReport(): io.reactivex.Observable<List<SalesVisitHistoryReport>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
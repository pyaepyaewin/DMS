package com.aceplus.data.repoimpl.report
import com.aceplus.data.database.MyDatabase
import com.aceplus.domain.model.report.SalesVisitHistoryReport
import com.aceplus.domain.repo.report.SalesVisitHistoryReportRepo
import io.reactivex.Observable

class SalesVisitHistoryReportRepoImpl(private val db: MyDatabase): SalesVisitHistoryReportRepo {
    override fun salesVisitHistoryReport(): Observable<List<SalesVisitHistoryReport>> {
        return Observable.just(db.customerVisitRecordReportDao().getSalesVisitHistoryReport())
    }
}
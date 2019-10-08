package com.aceplus.data.repoimpl.report

import com.aceplus.data.database.MyDatabase
import com.aceplus.domain.model.report.SalesOrderHistoryReport
import com.aceplus.domain.repo.report.SalesOrderHistoryReportRepo
import io.reactivex.Observable

class SalesOrderHistoryReportRepoImpl(private val db:MyDatabase): SalesOrderHistoryReportRepo {
    override fun salesOrderHistoryReport(): Observable<List<SalesOrderHistoryReport>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
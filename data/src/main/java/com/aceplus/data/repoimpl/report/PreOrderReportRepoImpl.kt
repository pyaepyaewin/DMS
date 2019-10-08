package com.aceplus.data.repoimpl.report

import com.aceplus.data.database.MyDatabase
import com.aceplus.domain.model.report.PreOrderReport
import com.aceplus.domain.repo.report.PreOrderReportRepo
import io.reactivex.Observable

class PreOrderReportRepoImpl(private val db:MyDatabase): PreOrderReportRepo {
    override fun preOrderReport(): Observable<List<PreOrderReport>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
package com.aceplus.data.repoimpl.report

import com.aceplus.data.database.MyDatabase
import com.aceplus.domain.model.report.UnsellReasonReport
import com.aceplus.domain.repo.report.UnsellReasonReportRepo
import io.reactivex.Observable

class UnsellReasonReportRepoImpl(private val db: MyDatabase) : UnsellReasonReportRepo {
    override fun unSellReasonReport(): Observable<List<UnsellReasonReport>> {
        return Observable.just(db.customerFeedbackDao().getUnSellReasonReport())
    }
}
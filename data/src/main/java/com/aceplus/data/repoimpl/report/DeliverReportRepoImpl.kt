package com.aceplus.data.repoimpl.report

import com.aceplus.data.database.MyDatabase
import com.aceplus.domain.model.report.DeliverReport
import com.aceplus.domain.repo.report.DeliverReportRepo
import io.reactivex.Observable

class DeliverReportRepoImpl(private val db:MyDatabase): DeliverReportRepo{
    override fun deliverReport(): Observable<List<DeliverReport>> {
        return Observable.just(db.deliveryDao().getDeliverReport())
    }
}
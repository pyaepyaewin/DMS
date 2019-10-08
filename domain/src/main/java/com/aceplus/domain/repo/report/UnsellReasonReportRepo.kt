package com.aceplus.domain.repo.report

import com.aceplus.domain.model.report.UnsellReasonReport
import io.reactivex.Observable

interface UnsellReasonReportRepo {
    fun unSellReasonReport(): Observable<List<UnsellReasonReport>>

}
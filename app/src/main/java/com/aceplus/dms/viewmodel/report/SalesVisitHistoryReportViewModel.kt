package com.aceplus.dms.viewmodel.report

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.aceplus.domain.model.report.SalesVisitHistoryReport
import com.aceplus.domain.repo.report.SalesVisitHistoryReportRepo
import com.aceplus.shared.viewmodel.BaseViewModel
import com.kkk.githubpaging.network.rx.SchedulerProvider

class SalesVisitHistoryReportViewModel(
    private val salesVisitHistoryReportRepo: SalesVisitHistoryReportRepo,
    private val schedulerProvider: SchedulerProvider
):BaseViewModel() {
    var salesVisitHistoryReportErrorState = MutableLiveData<String>()
    var salesVisitHistoryReportSuccessState = MutableLiveData<List<SalesVisitHistoryReport>>()
    fun salesVisitHistoryReport() {

        launch {
            salesVisitHistoryReportRepo.salesVisitHistoryReport()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe({
                    salesVisitHistoryReportSuccessState.postValue(it)
                    Log.d("Testing", "$it")
                }, {
                    salesVisitHistoryReportErrorState.value = it.localizedMessage
                })
        }

    }
}
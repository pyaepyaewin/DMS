package com.aceplus.dms.viewmodel.report

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.aceplus.domain.model.report.SalesOrderHistoryReport
import com.aceplus.domain.repo.report.SalesOrderHistoryReportRepo
import com.aceplus.shared.viewmodel.BaseViewModel
import com.kkk.githubpaging.network.rx.SchedulerProvider

class SalesOrderHistoryReportViewModel(
    private val salesOrderHistoryReportRepo: SalesOrderHistoryReportRepo,
    private val schedulerProvider: SchedulerProvider
) :BaseViewModel(){
    var salesOrderHistoryReportErrorState = MutableLiveData<String>()
    var salesOrderHistoryReportSuccessState = MutableLiveData<List<SalesOrderHistoryReport>>()
    fun salesOrderHistoryReport() {

        launch {
            salesOrderHistoryReportRepo.salesOrderHistoryReport()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe({
                    salesOrderHistoryReportSuccessState.postValue(it)
                    Log.d("Testing", "$it")
                }, {
                    salesOrderHistoryReportErrorState.value = it.localizedMessage
                })
        }

    }

}
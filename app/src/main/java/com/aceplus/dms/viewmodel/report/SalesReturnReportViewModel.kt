package com.aceplus.dms.viewmodel.report

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.aceplus.domain.model.report.SalesReturnDetailReport
import com.aceplus.domain.model.report.SalesReturnReport
import com.aceplus.domain.repo.report.SalesReturnReportRepo
import com.aceplus.shared.viewmodel.BaseViewModel
import com.kkk.githubpaging.network.rx.SchedulerProvider

class SalesReturnReportViewModel(
    private val salesReturnReportRepo: SalesReturnReportRepo,
    private val schedulerProvider: SchedulerProvider
):BaseViewModel() {
    var salesReturnReportErrorState = MutableLiveData<String>()
    var salesReturnReportSuccessState = MutableLiveData<List<SalesReturnReport>>()
    var salesReturnDetailReportSuccessState = MutableLiveData<List<SalesReturnDetailReport>>()

    fun salesReturnReport() {

        launch {
            salesReturnReportRepo.salesReturnReport()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe({
                    salesReturnReportSuccessState.postValue(it)
                    Log.d("Testing", "$it")
                }, {
                    salesReturnReportErrorState.value = it.localizedMessage
                })
        }

    }
    fun salesReturnDetailReport(invoiceId:String) {

        launch {
            salesReturnReportRepo.salesReturnDetailReport(invoiceId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe({
                    salesReturnDetailReportSuccessState.postValue(it)
                    Log.d("Testing", "$it")
                }, {
                    salesReturnReportErrorState.value = it.localizedMessage
                })
        }

    }
}
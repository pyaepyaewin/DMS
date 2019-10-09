package com.aceplus.dms.viewmodel.report

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.aceplus.domain.model.report.SaleInvoiceDetailReport
import com.aceplus.domain.model.report.SalesCancelReport
import com.aceplus.domain.repo.report.SalesCancelReportRepo
import com.aceplus.shared.viewmodel.BaseViewModel
import com.kkk.githubpaging.network.rx.SchedulerProvider

class SalesCancelReportViewModel(
    private val salesCancelReportRepo: SalesCancelReportRepo,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {
    var salesCancelReportErrorState = MutableLiveData<String>()
    var salesCancelReportSuccessState = MutableLiveData<List<SalesCancelReport>>()
    var saleCancelDetailReportSuccessState = MutableLiveData<List<SaleInvoiceDetailReport>>()

    fun salesCancelReport() {

        launch {
            salesCancelReportRepo.salesCancelReport()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe({
                    salesCancelReportSuccessState.postValue(it)
                    Log.d("Testing", "$it")
                }, {
                    salesCancelReportErrorState.value = it.localizedMessage
                })
        }

    }

    fun saleCancelDetailReport(invoiceId: String) {
        launch {
            salesCancelReportRepo.salesCancelDetailReport(invoiceId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe({
                    saleCancelDetailReportSuccessState.postValue(it)
                    Log.d("Testing", "$it")
                }, {
                    salesCancelReportErrorState.value = it.localizedMessage
                })
        }

    }

}
package com.aceplus.dms.viewmodel.report

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.aceplus.domain.model.report.DeliverDetailReport
import com.aceplus.domain.model.report.DeliverReport
import com.aceplus.domain.repo.report.DeliverReportRepo
import com.aceplus.shared.viewmodel.BaseViewModel
import com.kkk.githubpaging.network.rx.SchedulerProvider

class DeliverReportViewModel(
    private val deliverReportRepo: DeliverReportRepo,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel(){
    var deliverReportErrorState = MutableLiveData<String>()
    var deliverReportSuccessState = MutableLiveData<List<DeliverReport>>()
    var deliverDetailReportSuccessState = MutableLiveData<List<DeliverDetailReport>>()
    fun deliverReport() {

        launch {
            deliverReportRepo.deliverReport()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe({
                    deliverReportSuccessState.postValue(it)
                    Log.d("Testing", "$it")
                }, {
                    deliverReportErrorState.value = it.localizedMessage
                })
        }

    }

    fun deliverDetailReport(invoiceId:String){
        launch {
            deliverReportRepo.deliverDetailReport(invoiceId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe({
                    deliverDetailReportSuccessState.postValue(it)
                    Log.d("Testing","$it")
                },{
                    deliverReportErrorState.value = it.localizedMessage
                })
        }

    }

}
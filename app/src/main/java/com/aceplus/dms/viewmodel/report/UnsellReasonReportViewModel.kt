package com.aceplus.dms.viewmodel.report

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.aceplus.domain.model.report.UnsellReasonReport
import com.aceplus.domain.repo.report.UnsellReasonReportRepo
import com.aceplus.shared.viewmodel.BaseViewModel
import com.kkk.githubpaging.network.rx.SchedulerProvider

class UnsellReasonReportViewModel(
    private val unSellReasonReportRepo: UnsellReasonReportRepo,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {
    var unSellReasonReportErrorState = MutableLiveData<String>()
    var unSellReasonReportSuccessState = MutableLiveData<List<UnsellReasonReport>>()
    fun unSellReasonReport() {

        launch {
            unSellReasonReportRepo.unSellReasonReport()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe({
                    unSellReasonReportSuccessState.postValue(it)
                    Log.d("Testing", "$it")
                }, {
                    unSellReasonReportErrorState.value = it.localizedMessage
                })
        }

    }
}
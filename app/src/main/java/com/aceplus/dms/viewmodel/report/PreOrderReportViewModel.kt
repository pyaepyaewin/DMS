package com.aceplus.dms.viewmodel.report

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.aceplus.domain.model.report.PreOrderReport
import com.aceplus.domain.repo.report.PreOrderReportRepo
import com.aceplus.shared.viewmodel.BaseViewModel
import com.kkk.githubpaging.network.rx.SchedulerProvider

class PreOrderReportViewModel(
    private val preOrderReportRepo: PreOrderReportRepo,
    private val schedulerProvider: SchedulerProvider
):BaseViewModel() {
    var preOrderReportErrorState = MutableLiveData<String>()
    var preOrderReportSuccessState = MutableLiveData<List<PreOrderReport>>()
    fun preOrderReport() {

        launch {
            preOrderReportRepo.preOrderReport()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe({
                    preOrderReportSuccessState.postValue(it)
                    Log.d("Testing", "$it")
                }, {
                    preOrderReportErrorState.value = it.localizedMessage
                })
        }

    }
}
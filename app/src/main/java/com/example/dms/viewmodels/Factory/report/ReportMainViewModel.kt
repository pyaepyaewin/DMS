package com.example.dms.viewmodels.Factory.report

import androidx.lifecycle.MutableLiveData
import com.example.dms.data.database.table.InvoiceReport
import com.example.dms.data.repositories.ReportRepository
import io.reactivex.android.schedulers.AndroidSchedulers

class ReportMainViewModel(private val reportRepository: ReportRepository): ReportBaseViewModel() {

    var errorState = MutableLiveData<String>()
    var successState = MutableLiveData<List<InvoiceReport>>()

    fun getInvoiceReport(){

        launch {
            reportRepository.getReportData()
                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    successState.postValue(it)
                },{
                    errorState.value = it.localizedMessage
                })
        }

    }

}
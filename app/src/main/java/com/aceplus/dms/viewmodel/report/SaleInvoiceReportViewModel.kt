package com.aceplus.dms.viewmodel.report

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.aceplus.domain.entity.customer.Customer
import com.aceplus.domain.model.report.SaleInvoiceDetailReport
import com.aceplus.domain.model.report.SaleInvoiceReport
import com.aceplus.domain.repo.report.SaleInvoiceReportRepo
import com.aceplus.shared.viewmodel.BaseViewModel
import com.kkk.githubpaging.network.rx.SchedulerProvider

class SaleInvoiceReportViewModel(
    private val saleInvoiceReportRepo: SaleInvoiceReportRepo,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel(){
    var saleInvoiceReportErrorState = MutableLiveData<String>()
    var saleInvoiceReportSuccessState = MutableLiveData<List<SaleInvoiceReport>>()
    var saleInvoiceDetailReportSuccessState = MutableLiveData<List<SaleInvoiceDetailReport>>()
    var customerDataList = MutableLiveData<List<Customer>>()

    fun loadCustomer() {
        launch {
            saleInvoiceReportRepo.getAllCustomerData()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {
                    customerDataList.postValue(it)
                }
        }
    }
    fun saleInvoiceReport(){
        launch {
            saleInvoiceReportRepo.saleInvoiceReport()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe({
                    saleInvoiceReportSuccessState.postValue(it)
                    Log.d("Testing","$it")
                },{
                    saleInvoiceReportErrorState.value = it.localizedMessage
                })
        }

    }
    fun saleInvoiceDetailReport(invoiceId:String){
        launch {
            saleInvoiceReportRepo.saleInvoiceDetailReport(invoiceId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe({
                    saleInvoiceDetailReportSuccessState.postValue(it)
                    Log.d("Testing","$it")
                },{
                    saleInvoiceReportErrorState.value = it.localizedMessage
                })
        }

    }
}
package com.example.dms.viewmodels.Factory.ItemReport

import androidx.lifecycle.MutableLiveData
import com.example.dms.data.database.table.InvoiceItemReport
import com.example.dms.data.database.table.InvoiceReport
import com.example.dms.data.repositories.ItemReportRepository
import io.reactivex.android.schedulers.AndroidSchedulers

class ItemReportMainViewModel (private val itemReportRepository: ItemReportRepository): ItemReportBaseViewModel() {

    var errorState = MutableLiveData<String>()
    var successState = MutableLiveData<List<InvoiceItemReport>>()

    fun getInvoiceItemReport(invoiceID: String) {

        launch {
            itemReportRepository.getSaleItemReport(invoiceID)
                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    successState.postValue(it)
                }, {
                    errorState.value = it.localizedMessage
                })
        }

    }
}

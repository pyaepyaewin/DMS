package com.aceplus.dms.viewmodel.report

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.aceplus.domain.entity.product.Product
import com.aceplus.domain.repo.report.ProductBalanceReportRepo
import com.aceplus.shared.viewmodel.BaseViewModel
import com.kkk.githubpaging.network.rx.SchedulerProvider

class ProductBalanceReportViewModel(
    private val productBalanceReportRepo: ProductBalanceReportRepo,
    private val schedulerProvider: SchedulerProvider
):BaseViewModel() {
    var productBalanceReportErrorState = MutableLiveData<String>()
    var productBalanceReportSuccessState = MutableLiveData<List<Product>>()
    fun productBalanceReport() {

        launch {
            productBalanceReportRepo.productBalanceReport()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe({
                    productBalanceReportSuccessState.postValue(it)
                    Log.d("Testing", "$it")
                }, {
                    productBalanceReportErrorState.value = it.localizedMessage
                })
        }

    }
}
package com.aceplus.dms.viewmodel.customer.sale

import com.aceplus.domain.repo.CustomerVisitRepo
import com.aceplus.domain.vo.SoldProductInfo
import com.aceplus.shared.viewmodel.BaseViewModel
import com.kkk.githubpaging.network.rx.SchedulerProvider

class SaleCheckoutViewModel(
    private val customerVisitRepo: CustomerVisitRepo,
    private val schedulerProvider: SchedulerProvider
): BaseViewModel() {

    fun calculateFinalAmount(){

        var amountAndPercentage: Map<String, Double> = mapOf()
        var sameCategoryProducts: ArrayList<SoldProductInfo> = ArrayList()

        // ToDo - show final amount

    }

}
package com.aceplus.dms.viewmodel.customer.sale

import com.aceplus.domain.repo.CustomerVisitRepo
import com.aceplus.shared.viewmodel.BaseViewModel
import com.kkk.githubpaging.network.rx.SchedulerProvider

class SalesReturnViewModel(
    private val customerVisitRepo: CustomerVisitRepo,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {

    fun getSaleManID(): String{
        val saleManData = customerVisitRepo.getSaleManData()
        return saleManData.id
    }

    fun getRouteID(): Int{
        return customerVisitRepo.getRouteScheduleIDV2()
    }

    fun getLastCountForInvoiceNumber(mode: String) = customerVisitRepo.getLastCountForInvoiceNumber(mode)

}
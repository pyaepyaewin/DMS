package com.aceplus.dms.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.aceplus.domain.entity.CompanyInformation
import com.aceplus.domain.entity.customer.Customer
import com.aceplus.domain.repo.CustomerVisitRepo
import com.aceplus.domain.vo.RelatedDataForPrint
import com.aceplus.shared.viewmodel.BaseViewModel
import com.kkk.githubpaging.network.rx.SchedulerProvider

class PrintInvoiceViewModel(private val customerVisitRepo: CustomerVisitRepo, private val schedulerProvider: SchedulerProvider): BaseViewModel() {

    var taxInfo = MutableLiveData<Triple<String, Int, Int>>()
    var salePersonName = MutableLiveData<String>()
//    var relatedDataForPrint1 = MutableLiveData<Triple<Customer, Int, String>>()
    var relatedDataForPrint = MutableLiveData<RelatedDataForPrint>()

    fun getSalePersonName(salePersonID: String){
        launch {
            customerVisitRepo.getSaleManName(salePersonID)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe{
                    var salePersonName = ""
                    for (name in it){
                        salePersonName = name ?: "----"
                    }
                    this.salePersonName.postValue(salePersonName)
                }
        }
    }

    fun getTaxInfo(){
        launch {
            customerVisitRepo.getCompanyInfo()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe{
                    var taxType = ""
                    var taxPercent = 0
                    var branchCode = 0
                    for (companyInfo in it){
                        taxType = companyInfo.tax_type ?: ""
                        taxPercent = companyInfo.tax ?: 0
                        branchCode = companyInfo.branch_id ?: 0
                    }
                    taxInfo.postValue(Triple(taxType, taxPercent, branchCode))
                }
        }
    }

    fun getRelatedDataAndPrint(customerID: String, saleManID: String){

        var customer: Customer? = null
        var routeID = 0
        var customerTownShipName: String? = null
        var companyInfo: CompanyInformation? = null

        launch {
            customerVisitRepo.getCustomerByID(customerID.toInt())
                .flatMap {
                    customer = it
                    return@flatMap customerVisitRepo.getRouteID(saleManID)
                }
                .flatMap {
                    for (i in it){
                        routeID = i
                    }
                    return@flatMap customerVisitRepo.getCustomerTownshipName(customerID.toInt())
                }
                .flatMap {
                    customerTownShipName = it
                    return@flatMap customerVisitRepo.getCompanyInfo()
                }
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe{
                    for (i in it){
                        companyInfo = i
                    }
                }
        }

        if (customer != null && customerTownShipName != null && companyInfo != null)
            relatedDataForPrint.postValue(RelatedDataForPrint(customer!!, routeID, customerTownShipName!!, companyInfo!!))

    }

}
package com.aceplus.dms.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.aceplus.dms.utils.Utils
import com.aceplus.domain.entity.product.Product
import com.aceplus.domain.repo.CustomerVisitRepo
import com.aceplus.shared.viewmodel.BaseViewModel
import com.kkk.githubpaging.network.rx.SchedulerProvider

class VanIssueViewModel(
    private val customerVisitRepo: CustomerVisitRepo,
    private val schedulerProvider: SchedulerProvider
): BaseViewModel() {

    var productDataList = MutableLiveData<Pair<List<Product>, List<String>>>()


    fun getLocationCode(): Int{
        return customerVisitRepo.getLocationCode()
    }

    fun getSaleManID(): String {
        val saleManData = customerVisitRepo.getSaleManData()
        return saleManData.id
    }

    fun getInvoiceNumber(saleManId: String, locationNumber: Int, invoiceMode: String): String {
        return Utils.getInvoiceNo(
            saleManId,
            locationNumber.toString(),
            invoiceMode,
            customerVisitRepo.getLastCountForInvoiceNumber(invoiceMode)
        )
    }

    fun loadProductList() {
        launch {
            customerVisitRepo.getAllProductData()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {
                    val productNameList = mutableListOf<String>()
                    for (product in it) {
                        productNameList.add(product.product_name.toString())
                    }
                    productDataList.postValue(Pair(it, productNameList))
                }
        }
    }

    fun saveData(
        invoiceNo: String
    ){



    }

}
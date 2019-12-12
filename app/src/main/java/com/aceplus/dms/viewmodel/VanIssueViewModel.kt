package com.aceplus.dms.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.aceplus.dms.utils.Utils
import com.aceplus.domain.entity.deviceissue.DeviceIssueRequest
import com.aceplus.domain.entity.deviceissue.DeviceIssueRequestItem
import com.aceplus.domain.entity.product.Product
import com.aceplus.domain.repo.CustomerVisitRepo
import com.aceplus.domain.vo.SoldProductInfo
import com.aceplus.shared.viewmodel.BaseViewModel
import com.kkk.githubpaging.network.rx.SchedulerProvider

class VanIssueViewModel(
    private val customerVisitRepo: CustomerVisitRepo,
    private val schedulerProvider: SchedulerProvider
): BaseViewModel() {

    var productDataList = MutableLiveData<Pair<List<Product>, List<String>>>()
    var errorMessage = MutableLiveData<String>()
    var successState = MutableLiveData<Int>()


    fun getLocationCode(): Int{
        return customerVisitRepo.getLocationCode()
    }

    fun getRouteID(): Int {
        return customerVisitRepo.getRouteScheduleIDV2()
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
        invoiceNo: String,
        date: String,
        routeID: Int,
        remark: String?,
        productList: ArrayList<SoldProductInfo>
    ){
        launch {
            customerVisitRepo.getDeviceIssueRequestByID(invoiceNo)
                .flatMap {

                    if (it.isEmpty()){

                        val deviceIssueRequest = DeviceIssueRequest()
                        val deviceIssueItemList = ArrayList<DeviceIssueRequestItem>()

                        deviceIssueRequest.invoice_no = invoiceNo
                        deviceIssueRequest.date = date
                        deviceIssueRequest.route_id = routeID
                        deviceIssueRequest.remark = remark

                        customerVisitRepo.insertDeviceIssueRequest(deviceIssueRequest)

                        for (soldProduct in productList){
                            val deviceIssueItem = DeviceIssueRequestItem()
                            deviceIssueItem.invoice_no = invoiceNo
                            deviceIssueItem.quantity = soldProduct.quantity.toString()
                            deviceIssueItem.stock_id = soldProduct.product.id
                            deviceIssueItemList.add(deviceIssueItem)
                        }

                        customerVisitRepo.insertAllDeviceIssueRequestItem(deviceIssueItemList)

                    }
                    else
                        errorMessage.postValue("Found duplicate invoice!")

                    return@flatMap customerVisitRepo.getDeviceIssueRequestByID(invoiceNo)
                }
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe{

                    if (it.isNotEmpty()) successState.postValue(1)

                }
        }
    }

}
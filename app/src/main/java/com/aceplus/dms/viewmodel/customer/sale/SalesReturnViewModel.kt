package com.aceplus.dms.viewmodel.customer.sale

import android.arch.lifecycle.MutableLiveData
import com.aceplus.domain.entity.product.Product
import com.aceplus.domain.repo.CustomerVisitRepo
import com.aceplus.domain.vo.SoldProductInfo
import com.aceplus.shared.viewmodel.BaseViewModel
import com.kkk.githubpaging.network.rx.SchedulerProvider

class SalesReturnViewModel(
    private val customerVisitRepo: CustomerVisitRepo,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {

    var productDataList = MutableLiveData<Pair<List<Product>, List<String>>>()
    var taxType = MutableLiveData<String>()
    var calculatedProductList = MutableLiveData<Triple<ArrayList<SoldProductInfo>, Double, Double>>()

    var taxInfo = Pair("", 0)


    fun getSaleManID(): String{
        val saleManData = customerVisitRepo.getSaleManData()
        return saleManData.id
    }

    fun getRouteID(): Int{
        return customerVisitRepo.getRouteScheduleIDV2()
    }

    fun getLastCountForInvoiceNumber(mode: String) = customerVisitRepo.getLastCountForInvoiceNumber(mode)

    fun loadProductList() {
        launch {
            customerVisitRepo.getAllProductData()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {
                    val productNameList = mutableListOf<String>()
                    for (product in it) {
                        productNameList.add(product.product_name!!)
                    }
                    productDataList.postValue(Pair(it, productNameList))
                }
        }
    }

    fun getTaxInfo(){
        launch {
            customerVisitRepo.getCompanyInfo()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe{
                    for (i in it){
                        taxInfo = Pair(i.tax_type!!, i.tax!!)
                        taxType.postValue(i.tax_type)
                    }
                }
        }
    }

    fun calculateSelectedProductData(productList: ArrayList<SoldProductInfo>){

        val calculatedSoldProductList = ArrayList<SoldProductInfo>()
        var netAmount = 0.0
        var taxAmount: Double

        for (product in productList){

            val price = product.product.selling_price.toString().toDouble()
            val totalAmt = price * product.quantity
            val netAmt = totalAmt - product.discountAmount - product.extraDiscountAmount // Checking required - disc & extra disc amount

            netAmount += netAmt

            product.totalAmt = totalAmt
            calculatedSoldProductList.add(product)

        }

        taxAmount = calculateTaxAmt(netAmount, taxInfo.second)
        calculatedProductList.postValue(Triple(calculatedSoldProductList, netAmount, taxAmount))

    }

    private fun calculateTaxAmt(netAmount: Double, taxPercent: Int): Double{

        return netAmount / 21

    }

    fun saveData(isSaleExchange: Boolean){

        // ToDo - save data

    }

}
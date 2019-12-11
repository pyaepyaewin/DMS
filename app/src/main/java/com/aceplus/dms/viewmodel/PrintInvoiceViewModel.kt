package com.aceplus.dms.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.aceplus.domain.entity.CompanyInformation
import com.aceplus.domain.entity.customer.Customer
import com.aceplus.domain.entity.product.Product
import com.aceplus.domain.entity.promotion.Promotion
import com.aceplus.domain.entity.sale.salereturn.SaleReturn
import com.aceplus.domain.repo.CustomerVisitRepo
import com.aceplus.domain.vo.RelatedDataForPrint
import com.aceplus.domain.vo.SaleExchangeProductInfo
import com.aceplus.domain.vo.SoldProductInfo
import com.aceplus.shared.viewmodel.BaseViewModel
import com.kkk.githubpaging.network.rx.SchedulerProvider

class PrintInvoiceViewModel(private val customerVisitRepo: CustomerVisitRepo, private val schedulerProvider: SchedulerProvider): BaseViewModel() {

    var taxInfo = MutableLiveData<Triple<String, Int, String>>()
    var salePersonName = MutableLiveData<String>()
    var relatedDataForPrint = MutableLiveData<RelatedDataForPrint>()
    var saleReturn = MutableLiveData<SaleReturn>()
    var saleReturnProducts = MutableLiveData<List<SaleExchangeProductInfo>>()
    var exchangeProducts = MutableLiveData<List<SaleExchangeProductInfo>>()

    fun getSalePersonName(salePersonID: String){
        launch {
            customerVisitRepo.getSaleManName(salePersonID)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe{
                    var salePersonName = it ?: "...."
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
                    var branchCode = ""
                    for (companyInfo in it){
                        taxType = companyInfo.tax_type ?: ""
                        taxPercent = companyInfo.tax ?: 0
                        branchCode = companyInfo.branch_code ?: ""
                    }
                    taxInfo.postValue(Triple(taxType, taxPercent, branchCode))
                }
        }
    }

    fun getRelatedDataAndPrint(customerID: String, saleManID: String, orderSaleManID: String?, returnInvoiceNo: String?){

        var customer: Customer? = null
        var routeID = 0
        var routeName: String? = "...."
        var customerTownShipName: String? = null
        var companyInfo: CompanyInformation? = null
        var salePersonName: String? = null
        var orderSalePersonName: String? = null
        var saleReturnInvoice: SaleReturn? = null

        launch {
            customerVisitRepo.getCustomerByID(customerID.toInt())
                .flatMap {
                    customer = it
                    return@flatMap customerVisitRepo.getRouteID(saleManID)
                }
                .flatMap {
                    for (i in it){
                        routeID = i.toInt()
                    }
                    return@flatMap customerVisitRepo.getRouteNameByID(routeID)
                }
                .flatMap {
                    routeName = it
                    return@flatMap customerVisitRepo.getCustomerTownshipName(customerID.toInt())
                }
                .flatMap {
                    customerTownShipName = it
                    return@flatMap customerVisitRepo.getCompanyInfo()
                }
                .flatMap {
                    for (i in it){
                        companyInfo = i
                    }
                    return@flatMap customerVisitRepo.getSaleManName(saleManID)
                }
                .flatMap {
                    salePersonName = it
                    return@flatMap customerVisitRepo.getSaleManName(orderSaleManID)
                }
                .flatMap {
                    if (!orderSaleManID.isNullOrBlank())
                        orderSalePersonName = it
                    return@flatMap customerVisitRepo.getSaleReturnInfo(returnInvoiceNo)
                }
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe{

                    saleReturnInvoice = it

                    if (customer != null && customerTownShipName != null && companyInfo != null)
                        relatedDataForPrint.postValue(RelatedDataForPrint(customer!!, routeName, customerTownShipName!!, companyInfo!!, orderSalePersonName, salePersonName, saleReturnInvoice))
                }
        }

    }

    //ToDo - Need to check - think it's wrong logic!
    fun arrangeProductList(soldProductList: ArrayList<SoldProductInfo>, promotionList: ArrayList<Promotion>): ArrayList<SoldProductInfo>{

        val positionList: ArrayList<Int> = ArrayList()
        val newSoldProductList: ArrayList<SoldProductInfo> = ArrayList()
        val newPresentList: ArrayList<Promotion> = ArrayList()

        for (i in promotionList.indices){

            val stockId1 = promotionList[i].promotion_product_id

            for ((indexForNew, promotion) in newPresentList.withIndex()){
                var stockId = promotion.promotion_product_id
                if (stockId != stockId1 && (indexForNew + 1) == newPresentList.size){
                    val newPromotion = Promotion() //Check currency_id, price, promoPlanId, promotion price, product name // ToDo - Check point !!!
                    newPromotion.promotion_quantity = 0
                    newPromotion.promotion_product_id = promotionList[i].promotion_product_id

                    newPresentList.add(newPromotion)
                }
            }

            if (newPresentList.size == 0){
                val newPromotion = Promotion() //Check currency_id, price, promoPlanId, promotion price, product name // ToDo - Check point !!!
                newPromotion.promotion_quantity = 0
                newPromotion.promotion_product_id = promotionList[i].promotion_product_id

                newPresentList.add(newPromotion)
            }

        }

        val tempPresentList: ArrayList<Promotion> = ArrayList()
        tempPresentList.addAll(promotionList)

        for (promotion in tempPresentList){

            val stockId = promotion.promotion_product_id

            for (i in newPresentList.indices){
                val stockId1 = newPresentList[i].promotion_product_id
                if (stockId == stockId1){
                    val promotionQty = promotion.promotion_quantity
                    newPresentList[i].promotion_quantity += promotionQty
                    promotionList.remove(promotion)
                }
            }

        }

        for (i in soldProductList.indices){

            val soldProductStockId = soldProductList[i].product.id

            newSoldProductList.add(soldProductList[i])

            if (newPresentList.isNotEmpty()){
                for (j in newPresentList.indices){
                    val promoProductId = newPresentList[j].promotion_product_id

                    if (soldProductStockId == promoProductId){
                        val promoProduct = SoldProductInfo(Product(), false)
                        promoProduct.product.id = promoProductId
                        promoProduct.quantity = newPresentList[j].promotion_quantity
                        promoProduct.product.purchase_price = "0.0"
                        promoProduct.product.selling_price = "0.0"
                        //promoProduct.product.product_name = newPresentList[j].promotion_product_name // ToDo - Check point !!!
                        promoProduct.promotionPrice = 0.0

                        newSoldProductList.add(promoProduct)
                        positionList.add(j)
                        break
                    }

                }
            }

        }

        for (i in positionList.size downTo 1){
            val pos = positionList[i - 1]
            newPresentList.removeAt(pos)
        }

        return newSoldProductList

    }

    fun getSaleReturnInfo(saleReturnInvoiceNo: String){
        launch {
            customerVisitRepo.getSaleReturnInfo(saleReturnInvoiceNo)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe{
                    this.saleReturn.postValue(it)
                }
        }
    }

    fun getSaleReturnProductInfo(saleReturnInvoiceNo: String){
        launch {
            customerVisitRepo.getSaleReturnProductInfo(saleReturnInvoiceNo)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe{
                    this.saleReturnProducts.postValue(it)
                }
        }
    }

    fun getExchangeProductInfo(soldProductList: ArrayList<SoldProductInfo>){

        val saleExchangeProductList = ArrayList<SaleExchangeProductInfo>()
        for (soldProduct in soldProductList){
            val product = SaleExchangeProductInfo(
                soldProduct.product.product_name,
                soldProduct.quantity,
                soldProduct.product.selling_price
            )
            saleExchangeProductList.add(product)
        }
        this.exchangeProducts.postValue(saleExchangeProductList)

    }

}
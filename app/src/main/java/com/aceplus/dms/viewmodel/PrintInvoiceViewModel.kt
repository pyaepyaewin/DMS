package com.aceplus.dms.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.aceplus.domain.entity.CompanyInformation
import com.aceplus.domain.entity.customer.Customer
import com.aceplus.domain.entity.product.Product
import com.aceplus.domain.entity.promotion.Promotion
import com.aceplus.domain.repo.CustomerVisitRepo
import com.aceplus.domain.vo.RelatedDataForPrint
import com.aceplus.domain.vo.SoldProductInfo
import com.aceplus.shared.viewmodel.BaseViewModel
import com.kkk.githubpaging.network.rx.SchedulerProvider

class PrintInvoiceViewModel(private val customerVisitRepo: CustomerVisitRepo, private val schedulerProvider: SchedulerProvider): BaseViewModel() {

    var taxInfo = MutableLiveData<Triple<String, Int, Int>>()
    var salePersonName = MutableLiveData<String>()
    var relatedDataForPrint = MutableLiveData<RelatedDataForPrint>()
    var saleReturnProducts = MutableLiveData<List<Product>>()
    var exchangeProducts = MutableLiveData<List<Product>>()

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

    fun getRelatedDataAndPrint(customerID: String, saleManID: String, orderSaleManID: String?){

        var customer: Customer? = null
        var routeID = 0
        var routeName: String? = "...."
        var customerTownShipName: String? = null
        var companyInfo: CompanyInformation? = null
        var orderSalePersonName: String? = null

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
                    return@flatMap customerVisitRepo.getSaleManName(orderSaleManID!!)
                }
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe{
                    if (!orderSaleManID.isNullOrBlank()){
                        for (i in it){
                            orderSalePersonName = i
                        }
                    }
                    if (customer != null && customerTownShipName != null && companyInfo != null)
                        relatedDataForPrint.postValue(RelatedDataForPrint(customer!!, routeName, customerTownShipName!!, companyInfo!!, orderSalePersonName))
                }
        }

    }

    fun arrangeProductList(soldProductList: ArrayList<SoldProductInfo>, promotionList: ArrayList<Promotion>): ArrayList<SoldProductInfo>{

        val positionList: ArrayList<Int> = ArrayList()
        val newSoldProductList: ArrayList<SoldProductInfo> = ArrayList()
        val newPresentList: ArrayList<Promotion> = ArrayList()

        for (i in promotionList.indices){

            val stockId1 = promotionList[i].promotion_product_id

            for ((indexForNew, promotion) in newPresentList.withIndex()){
                var stockId = promotion.promotion_product_id
                if (stockId != stockId1 && (indexForNew + 1) == newPresentList.size){
                    val newPromotion = Promotion() // Check currency_id, price, promoPlanId, promotion price, product name // ToDo - Check point !!!
                    newPromotion.promotion_quantity = 0
                    newPromotion.promotion_product_id = promotionList[i].promotion_product_id

                    newPresentList.add(newPromotion)
                }
            }

            if (newPresentList.size == 0){
                val newPromotion = Promotion() // Check currency_id, price, promoPlanId, promotion price, product name // ToDo - Check point !!!
                newPromotion.promotion_quantity = 0
                newPromotion.promotion_product_id = promotionList[i].promotion_product_id

                newPresentList.add(newPromotion)
            }

        }

        for (promotion in promotionList){

            val stockId = promotion.promotion_product_id

            for (i in newPresentList.indices){
                val stockId1 = newPresentList[i].promotion_product_id
                if (stockId == stockId1){
                    val promotionQty = promotion.promotion_quantity
                    newPresentList[i].promotion_quantity += promotionQty
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

        for (i in positionList.size downTo 0){
            val pos = positionList[i - 1]
            newPresentList.removeAt(pos)
        }

        return newSoldProductList

    }

    fun getSaleReturnProductInfo(saleReturnInvoiceNo: String){
        launch {
            customerVisitRepo.getSaleReturnProductInfo(saleReturnInvoiceNo)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe{

                    val saleReturnProductList = ArrayList<Product>()
                    for (i in it){
                        val product = Product()
                        product.product_name = i.product_name
                        product.total_quantity = i.quantity
                        product.selling_price = i.price.toString()
                        saleReturnProductList.add(product)
                    }
                    this.saleReturnProducts.postValue(saleReturnProductList)

                }
        }
    }

    fun getExchangeProductInfo(soldProductList: ArrayList<SoldProductInfo>){

        val saleExchangeProductList = ArrayList<Product>()
        for (soldProduct in soldProductList){
            val product = Product()
            product.product_name = soldProduct.product.product_name
            product.total_quantity = soldProduct.quantity
            product.selling_price = soldProduct.product.selling_price
            saleExchangeProductList.add(product)
        }
        this.exchangeProducts.postValue(saleExchangeProductList)

    }

}
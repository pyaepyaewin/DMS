package com.aceplus.dms.viewmodel.customer.sale

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.aceplus.dms.utils.Utils
import com.aceplus.domain.entity.product.Product
import com.aceplus.domain.VO.SoldProductInfo
import com.aceplus.domain.entity.promotion.Promotion
import com.aceplus.domain.repo.CustomerVisitRepo
import com.aceplus.shared.viewmodel.BaseViewModel
import com.kkk.githubpaging.network.rx.SchedulerProvider
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class SaleViewModel(
    private val customerVisitRepo: CustomerVisitRepo,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {

    var productDataList = MutableLiveData<Pair<List<Product>, List<String>>>()
    var soldProductList = MutableLiveData<List<SoldProductInfo>>()
    var updatedData = MutableLiveData<Pair<SoldProductInfo, List<Promotion>>>()

    var mapGift: HashMap<Int, ArrayList<Int>> = HashMap()
    var mapPercent: HashMap<Int, ArrayList<Int>> = HashMap()
    private var tempSoldProductList: List<SoldProductInfo> = listOf()
    private var tempPromotionList: ArrayList<Promotion> = ArrayList()

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

    fun calculateClassDiscountByPrice(){
        launch {
            customerVisitRepo.getClassDiscountByPrice(Utils.getCurrentDate(true))
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe{ discPriceList ->
                    for (discPrice in discPriceList){
                        launch {
                            customerVisitRepo.getClassDiscountByPriceItem(discPrice.id)
                                .subscribeOn(schedulerProvider.io())
                                .observeOn(schedulerProvider.mainThread())
                                .subscribe{ discPriceItemList ->
                                    val temp: ArrayList<Int> = ArrayList()
                                    for (discPriceItem in discPriceItemList){
                                        if(discPrice.discount_type.equals("P", true)) mapPercent[discPrice.id] = temp
                                        else if (discPrice.discount_type.equals("G", true)) mapGift[discPrice.id] = temp
                                    }
                                }
                        }
                    }
                }
        }
    }

    fun calculatePromotionPriceAndGift(soldProductInfo: SoldProductInfo, soldProductList: ArrayList<SoldProductInfo>, promotionList: ArrayList<Promotion>){

        this.tempSoldProductList = soldProductList
        this.tempPromotionList = promotionList

        var promotionPrice = 0.0

        soldProductInfo.promotionPlanId = null

        launch {
            customerVisitRepo.getCurrentDatePromotion(Utils.getCurrentDate(true))
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe{ promoDateList ->
                    for (promoDate in promoDateList){
                        val promotionPlanId = promoDate.promotion_plan_id
                        launch {
                            customerVisitRepo.getPromotionPriceByID(promotionPlanId!!, soldProductInfo.product.sold_quantity, soldProductInfo.product.id.toString())
                                .subscribeOn(schedulerProvider.io())
                                .observeOn(schedulerProvider.mainThread())
                                .subscribe{ promoPriceList ->
                                    for (promoPrice in promoPriceList){
                                        promotionPrice = promoPrice.promotion_price!!.toDouble()
                                        soldProductInfo.promotionPrice = promotionPrice
                                    }
                                }
                        }
                        if (promotionPrice == 0.0){
                            val productToBuy = ArrayList<String>()
                            launch {
                                customerVisitRepo.getPromotionGiftByPlanID(promotionPlanId!!)
                                    .subscribeOn(schedulerProvider.io())
                                    .observeOn(schedulerProvider.mainThread())
                                    .subscribe{ promoGiftList ->
                                        for (promoGift in promoGiftList){
                                            promoGift.stock_id?.let { productToBuy.add(it) }
                                        }
                                    }
                            }
                            var count = 0
                            for(soldProduct in tempSoldProductList){
                                launch {
                                    customerVisitRepo.getPromotionToBuyProduct(promotionPlanId!!, soldProductInfo)
                                        .subscribeOn(schedulerProvider.io())
                                        .observeOn(schedulerProvider.mainThread())
                                        .subscribe{
                                            count += it.size
                                        }
                                }
                            }
                            if (count == productToBuy.size){
                                var flag = false
                                for (promotion in tempPromotionList){
                                    // If promotion.planID == planID
                                    // flag = true
                                    // break
                                }
                                if (!flag){
                                    // addPromotionProduct(soldProduct, promotionPlanId)
                                }
                            } else{
                                var flag = false
                                for (promotion in tempPromotionList){
                                    // If promotion.planID == planID
                                    // flag = true
                                    // break
                                }
                                if (flag) {
                                    // removePromotionProduct(promotionPlanId)
                                }
                            }
                        }
                    }
                }
        }

        updatedData.postValue(Pair(soldProductInfo, this.tempPromotionList))

    }

}
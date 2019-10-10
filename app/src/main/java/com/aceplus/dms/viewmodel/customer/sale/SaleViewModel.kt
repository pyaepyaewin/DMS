package com.aceplus.dms.viewmodel.customer.sale

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.aceplus.dms.utils.Utils
import com.aceplus.domain.entity.product.Product
import com.aceplus.domain.VO.SoldProductInfo
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

    var mapGift: HashMap<Int, ArrayList<Int>> = HashMap()
    var mapPercent: HashMap<Int, ArrayList<Int>> = HashMap()

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

    fun calculatePromotionPriceAndGift(soldProductInfo: SoldProductInfo){

        var promotion_price = 0.0

        soldProductInfo.promotionPlanId = null

        launch {
            customerVisitRepo.getCurrentDatePromotion(Utils.getCurrentDate(true))
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe{ promoDateList ->
                    if (promoDateList.isNotEmpty()){
                        var stock_id_new = 0
                        for (promoDate in promoDateList){
                            val promotionPlanId = promoDate.promotion_plan_id
                            launch {
                                customerVisitRepo.getProductByID(soldProductInfo.product.id)
                                    .subscribeOn(schedulerProvider.io())
                                    .observeOn(schedulerProvider.mainThread())
                                    .subscribe{
                                        if (it.isNotEmpty()){
                                            for (i in it){
                                                stock_id_new = i.id
                                            }
                                        }
                                    }
                            }
                            launch {
                                // Wrong - Stock_id and product_id
                                customerVisitRepo.getPromotionPriceByID(promotionPlanId!!, soldProductInfo.product.sold_quantity, soldProductInfo.product.product_id!!)
                                    .subscribeOn(schedulerProvider.io())
                                    .observeOn(schedulerProvider.mainThread())
                                    .subscribe{
                                    }
                                //Need to add getting promo price
                            }
                        }
                    }
                }
        }
        // Testing promo price table - stock_id type
        launch {
            customerVisitRepo.getAllPromoPrice()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe{
                    Log.i("Testing", "Row - ${it.size}")
                    for (i in it){
                        Log.i("Testing", "Stock ID - ${i.stock_id}")
                    }
                }
        }

    }

}
package com.aceplus.dms.viewmodel.customer.sale

import android.arch.lifecycle.MutableLiveData
import com.aceplus.dms.utils.Utils
import com.aceplus.domain.entity.product.Product
import com.aceplus.domain.vo.SoldProductInfo
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
    var promotionList = MutableLiveData<ArrayList<Promotion>>()
    var updatedSoldProduct = MutableLiveData<Pair<SoldProductInfo, Int>>()
    var netAmount = MutableLiveData<Double>()

    private var mapGift: HashMap<Int, ArrayList<Int>> = HashMap()
    private var mapPercent: HashMap<Int, ArrayList<Int>> = HashMap()
    private var tempSoldProductList: ArrayList<SoldProductInfo> = ArrayList()
    private var tempPromotionList: ArrayList<Promotion> = ArrayList()
    private var totalQuantityByCategoryItem = 0
    var percentTotalCount: ArrayList<String> = ArrayList()
    var giftTotalCount: ArrayList<String> = ArrayList()
    var productItemForGift = ArrayList<String>()
    var productItemForPercent = ArrayList<String>()

    var totalQtyForPercentWithProduct = HashMap<String, Int>()
    var totalQtyForPercentWithProduct1 = HashMap<String, Int>()
    var totalAmtForPercentWithProduct1 = HashMap<String, Double>()
    var totalQtyForGiftWithProduct = HashMap<String, Int>()
    var totalQtyForGiftWithProduct1 = HashMap<String, Int>()
    var totalAmtForGiftWithProduct1 = HashMap<String, Double>()

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

    fun calculateSoldProductData( isQtyChange: Boolean, soldProductInfo: SoldProductInfo, soldProductList: ArrayList<SoldProductInfo>, promotionList: ArrayList<Promotion>, position: Int){

        this.tempSoldProductList = soldProductList
        this.tempPromotionList = promotionList

        var currentClassId: String?

        if (isQtyChange){

            this.tempSoldProductList[position] = soldProductInfo

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
                                            soldProductInfo.promotionPlanId = promotionPlanId
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
                                        // ToDo - addPromotionProduct(soldProduct, promotionPlanId)
                                    }
                                } else{
                                    var flag = false
                                    for (promotion in tempPromotionList){
                                        // If promotion.planID == planID
                                        // flag = true
                                        // break
                                    }
                                    if (flag) {
                                        // ToDo - removePromotionProduct(promotionPlanId)
                                    }
                                }
                            }
                        }
                    }
            }

            totalQuantityByCategoryItem += soldProductInfo.quantity
            currentClassId = soldProductInfo.product.class_id // Need to check class_id or stock_id

            launch {
                customerVisitRepo.getClassDiscountByPriceOnClassID(currentClassId!!)
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.mainThread())
                    .subscribe{
                        if (it.isNotEmpty()){
                            for (classDiscountByPrice in it){

                                val discountType = classDiscountByPrice.discount_type
                                val id = classDiscountByPrice.id

                                if (discountType.equals("P", true)){

                                    val tempList = mapPercent[id]
                                    if (tempList != null){

                                        if (!percentTotalCount.contains(currentClassId) && tempList.size > 1){

                                            if (totalQtyForPercentWithProduct1.containsKey(soldProductInfo.product.class_id)){
                                                val value = totalQtyForPercentWithProduct1[soldProductInfo.product.class_id]
                                                val amt = totalAmtForPercentWithProduct1[soldProductInfo.product.class_id]
                                                totalQtyForPercentWithProduct1[soldProductInfo.product.class_id!!] = soldProductInfo.quantity + value!!
                                                totalAmtForPercentWithProduct1[soldProductInfo.product.class_id!!] = amt!! + soldProductInfo.product.selling_price!!.toDouble()
                                            } else{
                                                totalQtyForPercentWithProduct1[soldProductInfo.product.class_id!!] = soldProductInfo.quantity
                                                totalAmtForPercentWithProduct1[soldProductInfo.product.class_id!!] = soldProductInfo.product.selling_price!!.toDouble()
                                            }

                                        } else if (tempList.size == 1 && !productItemForPercent.contains(soldProductInfo.product.product_name)){

                                            productItemForPercent.add(soldProductInfo.product.product_name!!)
                                            if (totalQtyForPercentWithProduct.containsKey(soldProductInfo.product.class_id)){
                                                val value = totalQtyForPercentWithProduct[soldProductInfo.product.class_id]
                                                totalQtyForPercentWithProduct[soldProductInfo.product.class_id!!] = soldProductInfo.quantity + value!!
                                            } else{
                                                totalQtyForPercentWithProduct[soldProductInfo.product.class_id!!] = soldProductInfo.quantity
                                            }

                                        } else if (percentTotalCount.contains(currentClassId)){

                                            launch {
                                                customerVisitRepo.getClassDiscountByPriceItemCountOnClassID(soldProductInfo.product.class_id!!)
                                                    .subscribeOn(schedulerProvider.io())
                                                    .observeOn(schedulerProvider.mainThread())
                                                    .subscribe{ count ->
                                                        if (count >= 2){
                                                            if (totalQtyForPercentWithProduct1.containsKey(soldProductInfo.product.class_id!!)){
                                                                val value = totalQtyForPercentWithProduct1[soldProductInfo.product.class_id!!]
                                                                val amt = totalAmtForPercentWithProduct1[soldProductInfo.product.class_id!!]
                                                                totalQtyForPercentWithProduct1[soldProductInfo.product.class_id!!] = soldProductInfo.quantity + value!!
                                                                totalAmtForPercentWithProduct1[soldProductInfo.product.class_id!!] = amt!! + soldProductInfo.product.selling_price!!.toDouble()
                                                            } else{
                                                                totalQtyForPercentWithProduct1[soldProductInfo.product.class_id!!] = soldProductInfo.quantity
                                                                totalAmtForPercentWithProduct1[soldProductInfo.product.class_id!!] = soldProductInfo.product.selling_price!!.toDouble()
                                                            }
                                                        }
                                                    }
                                            }

                                        }

                                    }

                                } else if (discountType.equals("G", true)){

                                    val tempList = mapGift[id]
                                    if (tempList != null){

                                        if (!giftTotalCount.contains(currentClassId) && tempList.size > 1){

                                            if (totalQtyForGiftWithProduct1.containsKey(soldProductInfo.product.class_id)){
                                                val value = totalQtyForGiftWithProduct1[soldProductInfo.product.class_id]
                                                val amt = totalAmtForGiftWithProduct1[soldProductInfo.product.class_id]
                                                totalQtyForGiftWithProduct1[soldProductInfo.product.class_id!!] = soldProductInfo.quantity + value!!
                                                totalAmtForGiftWithProduct1[soldProductInfo.product.class_id!!] = amt!! + soldProductInfo.product.selling_price!!.toDouble()
                                            } else{
                                                totalQtyForGiftWithProduct1[soldProductInfo.product.class_id!!] = soldProductInfo.quantity
                                                totalAmtForGiftWithProduct1[soldProductInfo.product.class_id!!] = soldProductInfo.product.selling_price!!.toDouble()
                                            }

                                        } else if (tempList.size == 1 && !productItemForGift.contains(soldProductInfo.product.product_name)){

                                            productItemForGift.add(soldProductInfo.product.product_name!!)
                                            if (totalQtyForGiftWithProduct.containsKey(soldProductInfo.product.class_id)){
                                                val value = totalQtyForGiftWithProduct[soldProductInfo.product.class_id]
                                                totalQtyForGiftWithProduct[soldProductInfo.product.class_id!!] = soldProductInfo.quantity + value!!
                                            } else{
                                                totalQtyForGiftWithProduct[soldProductInfo.product.class_id!!] = soldProductInfo.quantity
                                            }

                                        } else if (giftTotalCount.contains(currentClassId)){

                                            launch {
                                                customerVisitRepo.getClassDiscountByPriceItemCountOnClassID(soldProductInfo.product.class_id!!)
                                                    .subscribeOn(schedulerProvider.io())
                                                    .observeOn(schedulerProvider.mainThread())
                                                    .subscribe{ count ->
                                                        if (count >= 2){
                                                            if (totalQtyForGiftWithProduct1.containsKey(soldProductInfo.product.class_id!!)){
                                                                val value = totalQtyForGiftWithProduct1[soldProductInfo.product.class_id!!]
                                                                val amt = totalAmtForGiftWithProduct1[soldProductInfo.product.class_id!!]
                                                                totalQtyForGiftWithProduct1[soldProductInfo.product.class_id!!] = soldProductInfo.quantity + value!!
                                                                totalAmtForGiftWithProduct1[soldProductInfo.product.class_id!!] = amt!! + soldProductInfo.product.selling_price!!.toDouble()
                                                            } else{
                                                                totalQtyForGiftWithProduct1[soldProductInfo.product.class_id!!] = soldProductInfo.quantity
                                                                totalAmtForGiftWithProduct1[soldProductInfo.product.class_id!!] = soldProductInfo.product.selling_price!!.toDouble()
                                                            }
                                                        }
                                                    }
                                            }

                                        }

                                    }

                                }

                            }
                        }
                    }
            }

        }

        var promoPrice = soldProductInfo.product.selling_price!!.toDouble()
        if (soldProductInfo.promotionPrice != 0.0) promoPrice = soldProductInfo.promotionPrice

        var  priceByCategoryQTY = 0.0
        var priceByClassDiscount = 0.0

        if (totalQuantityByCategoryItem != 0 && soldProductInfo.quantity != 0){

            val categoryId = soldProductInfo.product.category_id

            // ToDo - Find discount by category QTY

            if (priceByCategoryQTY != 0.0){
                promoPrice = priceByCategoryQTY
                soldProductInfo.discountWithCategoryItem = priceByCategoryQTY  // Not sure to add this
            }

            soldProductInfo.promoPriceByDiscount = promoPrice

            if (isQtyChange){

                // ToDo - Find discount by class

                if (priceByClassDiscount != 0.0){
                    promoPrice = priceByClassDiscount
                    soldProductInfo.priceByClassDiscount = priceByClassDiscount.toInt()
                    soldProductInfo.promoPriceByDiscount = promoPrice
                }

            }

        }

        if (soldProductInfo.focPercent != 0.0 || soldProductInfo.focAmount != 0.0){
            if (soldProductInfo.isFocTypePercent){
                val itemDiscount = promoPrice * soldProductInfo.focPercent / 100
                promoPrice -= itemDiscount
                soldProductInfo.itemDiscountAmount = itemDiscount
            } else{
                promoPrice -= soldProductInfo.focAmount
                soldProductInfo.itemDiscountAmount = soldProductInfo.focAmount
            }
            soldProductInfo.promoPriceByDiscount = promoPrice
        }

        var totalAmount = promoPrice * soldProductInfo.quantity

        if (soldProductInfo.isFocIsChecked){
            totalAmount = 0.0
            soldProductInfo.focQuantity = soldProductInfo.quantity
        }

        soldProductInfo.totalAmt = totalAmount

        updatedSoldProduct.postValue(Pair(soldProductInfo, position))

        soldProductList[position] = soldProductInfo
        calculateNetAmount(soldProductList)

    }

    fun calculateNetAmount(soldProductList: ArrayList<SoldProductInfo>){
        var netAmount = 0.0
        for (soldProduct in soldProductList){
            netAmount += soldProduct.totalAmt
        }
        this.netAmount.postValue(netAmount)
    }

}

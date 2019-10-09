package com.aceplus.dms.viewmodel.customer.sale

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.aceplus.data.utils.Constant
import com.aceplus.dms.R
import com.aceplus.dms.R.drawable.customer
import com.aceplus.dms.ui.activities.LoginActivity
import com.aceplus.dms.utils.Utils
import com.aceplus.domain.entity.customer.Customer
import com.aceplus.domain.entity.customer.CustomerFeedback
import com.aceplus.domain.entity.customer.DidCustomerFeedback
import com.aceplus.domain.entity.product.Product
import com.aceplus.domain.model.SoldProductInfo
import com.aceplus.domain.repo.CustomerVisitRepo
import com.aceplus.shared.utils.GPSTracker
import com.aceplus.shared.viewmodel.BaseViewModel
import com.kkk.githubpaging.network.rx.SchedulerProvider
import java.text.SimpleDateFormat
import java.util.*
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

}
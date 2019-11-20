package com.aceplus.dms.viewmodel.salecancelviewmodel

import android.arch.lifecycle.MutableLiveData
import com.aceplus.domain.entity.CompanyInformation
import com.aceplus.domain.entity.invoice.InvoiceProduct
import com.aceplus.domain.entity.product.Product
import com.aceplus.domain.entity.promotion.Promotion
import com.aceplus.domain.model.sale.salecancel.SaleCancelDetailItem
import com.aceplus.domain.model.sale.salecancel.SoldProductDataClass
import com.aceplus.domain.repo.salecancelrepo.SaleCancelRepo
import com.aceplus.domain.vo.SoldProductInfo
import com.aceplus.shared.viewmodel.BaseViewModel
import com.kkk.githubpaging.network.rx.SchedulerProvider
import io.reactivex.android.schedulers.AndroidSchedulers

class SaleCancelDetailViewModel(
    private val saleCancelRepo: SaleCancelRepo,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {
    var calculatedSoldProductList = MutableLiveData<Pair<ArrayList<SoldProductInfo>, Double>>()

    var productIdListSuccessState = MutableLiveData<List<String>>()
    var productIdListErrorState = MutableLiveData<String>()
    fun loadSoldProductIdList(invoiceID: String) {
        launch {
            saleCancelRepo.getProductIdList(invoiceID)
                .subscribeOn(schedulerProvider.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    productIdListSuccessState.value = it

                }, {
                    productIdListErrorState.value = it.localizedMessage
                })
        }
    }

    var soldProductListSuccessState = MutableLiveData<List<SaleCancelDetailItem>>()
    var soldProductListErrorState = MutableLiveData<String>()
    fun loadSoldProductList(productIdList: List<String>) {
        launch {
            saleCancelRepo.getSoldProductList(productIdList)
                .subscribeOn(schedulerProvider.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    soldProductListSuccessState.value = it

                }, {
                    soldProductListErrorState.value = it.localizedMessage
                })
        }
    }

    fun deleteInvoice(invoiceID: String) {
        saleCancelRepo.deleteInvoiceData(invoiceID)
    }

    fun deleteInvoiceProduct(invoiceId: String) {
        saleCancelRepo.deleteInvoiceProduct(invoiceId)
    }
    fun updateQty(invoiceId: String, productId: String, qty: Int)
    {
        saleCancelRepo.updateQuantity(invoiceId,productId,qty)
    }


    fun calculateSoldProductData(soldProductList: ArrayList<SoldProductInfo>){

        val calculatedSoldProductList = ArrayList<SoldProductInfo>()

        for (soldProductInfo in soldProductList){

            var promoPrice = soldProductInfo.product.selling_price!!.toDouble()
            if (soldProductInfo.promotionPrice != 0.0) promoPrice = soldProductInfo.promotionPrice

            soldProductInfo.promoPriceByDiscount = promoPrice

            var totalAmount = promoPrice * soldProductInfo.quantity

            soldProductInfo.totalAmt = totalAmount
            calculatedSoldProductList.add(soldProductInfo)

        }

        val netAmount = calculateNetAmount(calculatedSoldProductList)
        this.calculatedSoldProductList.postValue(Pair(calculatedSoldProductList, netAmount))

    }
    private fun calculateNetAmount(soldProductList: ArrayList<SoldProductInfo>): Double{
        var netAmount = 0.0
        for (soldProduct in soldProductList){
            netAmount += soldProduct.totalAmt
        }
        return netAmount
    }

}


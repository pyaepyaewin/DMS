package com.aceplus.dms.viewmodel.salecancelviewmodel

import android.arch.lifecycle.MutableLiveData
import com.aceplus.domain.entity.invoice.Invoice
import com.aceplus.domain.entity.invoice.InvoiceCancel
import com.aceplus.domain.entity.invoice.InvoiceCancelProduct
import com.aceplus.domain.entity.product.Product
import com.aceplus.domain.entity.promotion.Promotion
import com.aceplus.domain.entity.promotion.PromotionDate
import com.aceplus.domain.entity.promotion.PromotionPrice
import com.aceplus.domain.model.sale.salecancel.SaleCancelDetailItem
import com.aceplus.domain.model.sale.salecancel.SaleCancelItem
import com.aceplus.domain.repo.salecancelrepo.SaleCancelRepo
import com.aceplus.domain.vo.SoldProductInfo
import com.aceplus.shared.viewmodel.BaseViewModel
import com.kkk.githubpaging.network.rx.SchedulerProvider
import io.reactivex.android.schedulers.AndroidSchedulers

class SaleCancelViewModel(
    private val saleCancelRepo: SaleCancelRepo,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {
    var saleCancelSuccessState = MutableLiveData<List<SaleCancelItem>>()
    var saleCancelErrorState = MutableLiveData<String>()
    fun loadSaleCancelList() {
        launch {
            saleCancelRepo.getSaleCancelList()
                .subscribeOn(schedulerProvider.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    saleCancelSuccessState.postValue(it)

                }, {
                    saleCancelErrorState.value = it.localizedMessage
                })
        }
    }

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
    fun loadSoldProductList(productIdList: List<String>,invoiceID: String) {
        launch {
            saleCancelRepo.getSoldProductList(productIdList,invoiceID)
                .subscribeOn(schedulerProvider.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    soldProductListSuccessState.value = it
                    //soldProductListSuccessState.value=null

                }, {
                    soldProductListErrorState.value = it.localizedMessage
                })
        }
    }


    var promotionDateSuccessState = MutableLiveData<List<PromotionDate>>()
    var promotionDateErrorState = MutableLiveData<String>()
    fun loadPromotionDateList(currentDate: String) {
        launch {
            saleCancelRepo.getPromotionDateList(currentDate)
                .subscribeOn(schedulerProvider.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    promotionDateSuccessState.value = it

                }, {
                    promotionDateErrorState.value = it.localizedMessage
                })
        }
    }
    var promotionPriceSuccessState = MutableLiveData<List<PromotionPrice>>()
    var promotionPriceErrorState = MutableLiveData<String>()
    fun loadPromotionPriceList(promotionPlanId: String, buy_qty: Int, stockID: String) {
        launch {
            saleCancelRepo.getPromotionPriceById(promotionPlanId,buy_qty,stockID)
                .subscribeOn(schedulerProvider.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    promotionPriceSuccessState.value = it

                }, {
                    promotionPriceErrorState.value = it.localizedMessage
                })
        }
    }
    var invoiceCancelSuccessState = MutableLiveData<Invoice>()
    var invoiceCancelErrorState = MutableLiveData<String>()
    fun loadInvoiceCancel(invoiceId: String) {
        launch {
            saleCancelRepo.getInvoiceCancel(invoiceId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    invoiceCancelSuccessState.value = it
                    invoiceCancelSuccessState.value=null

                }, {
                    invoiceCancelErrorState.value = it.localizedMessage
                })
        }
    }

    fun saveDeleteInvoice(
        soldProductList: ArrayList<SoldProductInfo>,
        invoice: Invoice,
        invoiceID: String
    ) {
        var totalQty=0
        val invoiceProductList: ArrayList<InvoiceCancelProduct> = ArrayList()

        for (soldProduct in soldProductList) {

            val invoiceCancelProduct = InvoiceCancelProduct()

            invoiceCancelProduct.invoice_product_id = invoiceID
            invoiceCancelProduct.product_id = soldProduct.product.id
            invoiceCancelProduct.volume_discount_percent = soldProduct.discountPercent
            invoiceCancelProduct.discount_amount = soldProduct.discountAmount
            invoiceCancelProduct.discount_percent = soldProduct.discountPercent
            invoiceCancelProduct.exclude = soldProduct.exclude?.toString()
            invoiceCancelProduct.extra_discount = soldProduct.extraDiscount
            invoiceCancelProduct.p_price = soldProduct.product.purchase_price!!.toDouble()
            invoiceCancelProduct.sale_quantity = soldProduct.quantity.toDouble()
            invoiceCancelProduct.promotion_plan_id = soldProduct.promotionPlanId.toInt()
            //invoiceCancelProduct.serial_id = soldProduct.serialList[0]
            invoiceCancelProduct.total_amount = soldProduct.totalAmount
            invoiceCancelProduct.s_price = soldProduct.product.selling_price!!.toDouble()
            if (soldProduct.promotionPlanId.toString().isNullOrEmpty())
                invoiceCancelProduct.promotion_plan_id = soldProduct.promotionPlanId.toInt()
            invoiceProductList.add(invoiceCancelProduct)

            totalQty+=soldProduct.quantity
           saleCancelRepo.updateProductRemainingQty(soldProduct)
        }

//        saleCancelRepo.updateProductRemainingQty(
//            soldProductList[0].quantity,
//            soldProductList[0].product.id
//        )
        var invoiceCancel = InvoiceCancel()
        invoiceCancel.id = invoice.invoice_id
        invoiceCancel.invoice_id = invoice.invoice_product_id.toString()
        invoiceCancel.customer_id = invoice.customer_id
        invoiceCancel.sale_date = invoice.sale_date
        invoiceCancel.total_amount = invoice.total_amount?.toDouble()
        invoiceCancel.total_discount_amount = invoice.total_discount_amount
        invoiceCancel.pay_amount = invoice.pay_amount?.toDouble()
        invoiceCancel.refund_amount = invoice.refund_amount?.toDouble()
        invoiceCancel.receipt_person_name = invoice.receipt_person_name
        invoiceCancel.sale_person_id = invoice.sale_person_id?.toInt()
        invoiceCancel.due_date = invoice.due_date
        invoiceCancel.cash_or_credit = invoice.cash_or_credit
        invoiceCancel.location_code = invoice.location_code
        invoiceCancel.device_id = invoice.device_id
        invoiceCancel.invoice_time = invoice.invoice_time
        invoiceCancel.package_invoice_number = invoice.package_invoice_number.toString()
        invoiceCancel.package_status = invoice.package_status.toString()
        invoiceCancel.volume_amount = invoice.volume_amount.toInt()
        invoiceCancel.package_grade = invoice.package_grade
        invoiceCancel.invoice_product_id = invoice.invoice_product_id
        invoiceCancel.total_quantity = invoice.total_quantity.toInt()
        invoiceCancel.invoice_status = invoice.invoice_status
        invoiceCancel.total_discount_percent = invoice.total_discount_percent
        invoiceCancel.rate = invoice.rate
        invoiceCancel.tax_amount = invoice.tax_amount
        invoiceCancel.bank_name = invoice.bank_name
        invoiceCancel.bank_account_no = invoice.bank_account_no
        invoiceCancel.sale_flag = invoice.sale_flag.toString()


        saleCancelRepo.insertInvoiceCancel(invoiceCancel, invoiceProductList)

        saleCancelRepo.deleteInvoiceProduct(invoiceID)
        saleCancelRepo.deleteInvoiceData(invoiceID)
        saleCancelRepo.deleteInvoicePresent(invoiceID)



    }

    fun calculateSoldProductData(soldProductList: ArrayList<SoldProductInfo>) {

        val calculatedSoldProductList = ArrayList<SoldProductInfo>()

        for (soldProductInfo in soldProductList) {

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

    private fun calculateNetAmount(soldProductList: ArrayList<SoldProductInfo>): Double {
        var netAmount = 0.0
        for (soldProduct in soldProductList) {
            netAmount += soldProduct.totalAmt
        }
        return netAmount
    }


}


package com.aceplus.dms.viewmodel.salecancelviewmodel

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import com.aceplus.domain.entity.CompanyInformation
import com.aceplus.domain.entity.invoice.Invoice
import com.aceplus.domain.entity.invoice.InvoiceProduct
import com.aceplus.domain.model.forApi.invoice.InvoiceDetail
import com.aceplus.domain.repo.salecancelrepo.SaleCancelRepo
import com.aceplus.domain.vo.SoldProductInfo
import com.aceplus.shared.viewmodel.BaseViewModel
import com.kkk.githubpaging.network.rx.SchedulerProvider
import io.reactivex.android.schedulers.AndroidSchedulers

class SaleCancelCheckOutViewModel(
    private val saleCancelRepo: SaleCancelRepo,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {
    var invoice = MutableLiveData<Invoice>()

    var taxPercentSuccessState = MutableLiveData<List<CompanyInformation>>()
    var taxPercentErrorState = MutableLiveData<String>()
    fun loadTaxPercent() {
        launch {
            saleCancelRepo.getTaxPercent()
                .subscribeOn(schedulerProvider.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    taxPercentSuccessState.value = it

                }, {
                    taxPercentErrorState.value = it.localizedMessage
                })
        }
    }

    fun getSaleManID(): String {
        val saleManData = saleCancelRepo.getSaleManData()
        return saleManData.id

    }


    var soldInvoiceListSuccessState = MutableLiveData<List<Invoice>>()
    var soldInvoiceListErrorState = MutableLiveData<String>()
    fun loadSoldInvoiceData(invoiceID: String) {
        launch {
            saleCancelRepo.getSoldInvoice(invoiceID)
                .subscribeOn(schedulerProvider.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    soldInvoiceListSuccessState.value = it

                }, {
                    soldInvoiceListErrorState.value = it.localizedMessage
                })
        }
    }

    fun deleteInvoiceData(invoiceID: String) {
        saleCancelRepo.deleteInvoiceData(invoiceID)
    }

    fun deleteInvoiceProductData(invoiceID: String) {
        saleCancelRepo.deleteInvoiceProduct(invoiceID)
    }

    fun deleteInvoicePresenttData(invoiceID: String) {
        saleCancelRepo.deleteInvoicePresent(invoiceID)
    }

    fun deleteInvoiceProductForLongClick(invoiceId: String, productIdList: List<Int>) {
        saleCancelRepo.deleteInvoiceProductForLongClick(invoiceId, productIdList)
    }

    fun updateProductRemainingQuantity(soldProductInfo: SoldProductInfo) {
        saleCancelRepo.updateProductRemainingQtyForSaleCancel(soldProductInfo)
    }

    @SuppressLint("CheckResult")
    fun saveCheckoutData(
        id: String,
        saleDate: String,
        invoiceId: String,
        payAmount: Double,
        refundAmount: Double,
        receiptPerson: String,
        salePersonId: String,
        invoiceTime: String,
        dueDate: String,
        deviceId: String,
        cashOrLoanOrBank: String,
        soldProductList: ArrayList<SoldProductInfo>,
        totalAmount: Double,
        taxAmt: Double,
        bank: String,
        acc: String,
        totalDiscountAmount: Double,
        totalVolumeDiscountPercent: Double,
        productIdList: List<Int>
    ) {

        var totalQtyForInvoice = 0
        var totalAmountForInvoice = 0.0
        var totalDiscountAmount = 0.0
        val invoiceProductList: ArrayList<InvoiceProduct> = ArrayList()
        val invoiceDetailList: ArrayList<InvoiceDetail> = ArrayList()
        val invoiceList: MutableList<Invoice> = mutableListOf()

        for (soldProduct in soldProductList) {

            val invoiceDetail = InvoiceDetail()
            invoiceDetail.tsaleId = invoiceId
            invoiceDetail.productId = soldProduct.product.id
            invoiceDetail.qty = soldProduct.quantity
            invoiceDetail.discountAmt = soldProduct.discountAmount
            invoiceDetail.amt = soldProduct.totalAmt
            invoiceDetail.discountPercent = soldProduct.discountPercent
            invoiceDetail.s_price = soldProduct.product.selling_price!!.toDouble()
            invoiceDetail.p_price = soldProduct.product.purchase_price!!.toDouble()
            invoiceDetail.promotionPrice = soldProduct.promotionPrice
            invoiceDetail.itemDiscountPercent = soldProduct.focPercent
            invoiceDetail.itemDiscountAmount = soldProduct.focAmount

            if (!soldProduct.promotionPlanId.isNullOrEmpty())
                invoiceDetail.promotion_plan_id =
                    soldProduct.promotionPlanId.toInt()
            if (soldProduct.exclude != null && !soldProduct.exclude.equals("")) {
                invoiceDetail.exclude = soldProduct.exclude
            }
            invoiceDetailList.add(invoiceDetail)


            val invoiceProduct = InvoiceProduct()
            invoiceProduct.invoice_product_id = invoiceId
            invoiceProduct.product_id = soldProduct.product.id.toString()
            invoiceProduct.sale_quantity = soldProduct.quantity.toString()
            invoiceProduct.discount_amount = soldProduct.discountAmount.toString()
            invoiceProduct.total_amount = soldProduct.totalAmt
            invoiceProduct.discount_percent = soldProduct.discountPercent
            invoiceProduct.s_price = soldProduct.product.selling_price!!.toDouble()
            invoiceProduct.p_price = soldProduct.product.purchase_price!!.toDouble()
//            invoiceProduct.promotion_price =
//                soldProduct.promoPriceByDiscount
            invoiceProduct.item_discount_percent = soldProduct.focPercent
            invoiceProduct.item_discount_amount = soldProduct.focAmount
            invoiceProduct.exclude = "${soldProduct.exclude}"
            var promoPrice = soldProduct.promotionPrice
            if (promoPrice == 0.0) {
                promoPrice = soldProduct.product.selling_price!!.toDouble()
            }
            totalQtyForInvoice += soldProduct.quantity
            if (soldProduct.totalAmt != 0.0) {
                invoiceProductList.add(invoiceProduct)
            }

            var unsoldQty=soldProduct.currentProductQty-soldProduct.quantity
          // saleCancelRepo.updateProductRemainingQtyForLongClickDelete(unsoldQty,productIdList)
            saleCancelRepo.insertInvoiceProduct(invoiceProductList)

            val invoice = Invoice()

            invoice.invoice_id = invoiceId
            invoice.customer_id = id
            invoice.sale_date = saleDate
            invoice.total_amount = totalAmount.toString()
            invoice.total_discount_amount = totalDiscountAmount
            invoice.pay_amount = payAmount.toString()
            invoice.refund_amount = refundAmount.toString()
            invoice.receipt_person_name = receiptPerson
            invoice.sale_person_id = salePersonId
            invoice.due_date = dueDate
            invoice.cash_or_credit = cashOrLoanOrBank
            invoice.location_code = ""
            invoice.device_id = deviceId
            invoice.invoice_time = invoiceTime
            invoice.package_invoice_number = 0
            invoice.package_status = 0
            invoice.volume_amount = 0.0
            invoice.package_grade = ""
            invoice.invoice_product_id = 0
            invoice.total_quantity =
                totalQtyForInvoice.toDouble()
            invoice.invoice_status = cashOrLoanOrBank
            invoice.total_discount_percent =
                totalVolumeDiscountPercent.toString()
            invoice.rate = "1"
            invoice.tax_amount = taxAmt
            invoice.bank_name = bank
            invoice.bank_account_no = acc
            invoice.sale_flag = 0
            //invoiceList.add(invoice)
            saleCancelRepo.insertInvoice(invoice)
        }


    }
}



//if (soldProduct.quantity < soldProduct.currentProductQty) {
//    var unsoldQty = soldProduct.currentProductQty - soldProduct.quantity
//    saleCancelRepo.updateProductRemainingQtyForUnsoldProduct(
//        unsoldQty,
//        soldProduct.product.id.toString()
//    )
//
//} else {
//    var soldQty = soldProduct.quantity - soldProduct.currentProductQty
//    saleCancelRepo.updateProductRemainingQtyForSoldProduct(
//        soldQty,
//        soldProduct.product.id.toString()
//    )
//}



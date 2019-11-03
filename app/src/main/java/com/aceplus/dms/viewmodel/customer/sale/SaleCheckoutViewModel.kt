package com.aceplus.dms.viewmodel.customer.sale

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.aceplus.dms.utils.Utils
import com.aceplus.domain.entity.customer.Customer
import com.aceplus.domain.entity.invoice.Invoice
import com.aceplus.domain.entity.invoice.InvoiceProduct
import com.aceplus.domain.entity.promotion.Promotion
import com.aceplus.domain.model.forApi.invoice.InvoiceDetail
import com.aceplus.domain.repo.CustomerVisitRepo
import com.aceplus.domain.vo.SoldProductInfo
import com.aceplus.shared.viewmodel.BaseViewModel
import com.kkk.githubpaging.network.rx.SchedulerProvider

class SaleCheckoutViewModel(private val customerVisitRepo: CustomerVisitRepo, private val schedulerProvider: SchedulerProvider): BaseViewModel() {

    var invoice = MutableLiveData<Invoice>()

    fun getSaleManID(): String{
        val saleManData = customerVisitRepo.getSaleManData()
        return saleManData.id
    }

    fun getRouteID(): Int{
        return customerVisitRepo.getRouteScheduleIDV2()
    }

    @SuppressLint("CheckResult")
    fun calculateFinalAmount(soldProductList: ArrayList<SoldProductInfo>){

        var amountAndPercentage: Map<String, Double> = mapOf()
        var sameCategoryProducts: ArrayList<SoldProductInfo> = ArrayList()

        var exclude = 0
        var volDisFilterId = 0
        var soldPrice = 0.0
        var totalBuyAmtInclude = 0.0
        var totalBuyAmtExclude = 0.0
        var discountPercent = 0.0

        launch {
            customerVisitRepo.getVolumeDiscountFilterByDate(Utils.getCurrentDate(true))
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe{
                    for (i in it){

                        volDisFilterId = i.id
                        exclude = i.exclude?.toInt() ?: 0

                        for (soldProduct in soldProductList){

                            var categoryProduct: String? = null
                            var category: String? = null

                            customerVisitRepo.getProductByID(soldProduct.product.id)
                                .subscribeOn(schedulerProvider.io())
                                .observeOn(schedulerProvider.mainThread())
                                .subscribe{ productList ->
                                    for ( p in productList){
                                        categoryProduct = p.category_id
                                    }
                                }

                            customerVisitRepo.getVolumeDiscountFilterItem(volDisFilterId)
                                .subscribeOn(schedulerProvider.io())
                                .observeOn(schedulerProvider.mainThread())
                                .subscribe{ volumeDiscFilterItemList ->
                                    for (v in volumeDiscFilterItemList){
                                        category = v.category_id
                                    }
                                }

                            if (category == categoryProduct)
                                sameCategoryProducts.add(soldProduct)

                        }

                        for (aSameCategoryProduct in sameCategoryProducts){

                            var buy_amt = 0.0
                            // ToDo

                        }

                    }
                }
        }

    }

    @SuppressLint("CheckResult")
    fun saveCheckoutData(
        customerId: Int,
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
        promotionList: ArrayList<Promotion>,
        totalAmount: Double,
        taxAmt: Double,
        bank: String,
        acc: String
    ){

        var totalQtyForInvoice = 0
        val invoiceDetailList: ArrayList<InvoiceDetail> = ArrayList()
        val invoice = Invoice()

        launch {
            customerVisitRepo.getInvoiceCountByID(invoiceId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe{

                    if (it == 0){

                        val invoiceProductList: ArrayList<InvoiceProduct> = ArrayList()

                        for (soldProduct in soldProductList){

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
                            invoiceDetail.exclude = soldProduct.exclude
                            invoiceDetail.itemDiscountPercent = soldProduct.focPercent
                            invoiceDetail.itemDiscountAmount = soldProduct.focAmount

                            if (!soldProduct.promotionPlanId.isNullOrEmpty())
                                invoiceDetail.promotion_plan_id = soldProduct.promotionPlanId.toInt()

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
                            invoiceProduct.promotion_price = soldProduct.promoPriceByDiscount
                            invoiceProduct.item_discount_percent = soldProduct.focPercent
                            invoiceProduct.item_discount_amount = soldProduct.focAmount
                            invoiceProduct.exclude = "${soldProduct.exclude}"

                            if (!soldProduct.promotionPlanId.isNullOrEmpty())
                                invoiceProduct.promotion_plan_id = soldProduct.promotionPlanId.toInt()

                            totalQtyForInvoice += soldProduct.quantity

                            if (soldProduct.totalAmt != 0.0){
                                invoiceProductList.add(invoiceProduct)
                                customerVisitRepo.updateProductRemainingQty(soldProduct) // Need to remind !!!
                            }

                            if (soldProduct.focQuantity > 0 && soldProduct.totalAmt == 0.0){
                                // ToDo - add promotion list
                            }

                        }

                        customerVisitRepo.insertAllInvoiceProduct(invoiceProductList)

                        for (promotion in promotionList){
                            // ToDo - update qty
                            // ToDo - insert invoice present
                        }

                        /*totalDiscountAmount = volDisAmount
                        totalVolumeDiscountPercent = volDisPercent*/
                        invoice.invoice_id = invoiceId
                        invoice.customer_id = customerId.toString()
                        invoice.sale_date = saleDate
                        invoice.total_amount = totalAmount.toString()
                        invoice.total_discount_amount = 0.0 // Need to check
                        invoice.pay_amount = payAmount.toString()
                        invoice.refund_amount = refundAmount.toString()
                        invoice.receipt_person_name = receiptPerson
                        invoice.sale_person_id = salePersonId
                        invoice.due_date = dueDate
                        invoice.cash_or_credit = cashOrLoanOrBank
                        invoice.location_code = "" // Need to add
                        invoice.device_id = deviceId
                        invoice.invoice_time = invoiceTime
                        invoice.package_invoice_number = 0 // Need to add
                        invoice.package_status = 0 // Need to check
                        invoice.volume_amount = 0.0 // Need to check
                        invoice.package_grade = "" // Need to check
                        invoice.invoice_product_id = 0 // Need to check
                        invoice.total_quantity = totalQtyForInvoice.toDouble() // Check int or double
                        invoice.invoice_status = cashOrLoanOrBank
                        invoice.total_discount_percent = "0.0"  // Need to check
                        invoice.rate = "1"
                        invoice.tax_amount = taxAmt
                        invoice.bank_name = bank
                        invoice.bank_account_no = acc
                        invoice.sale_flag = 0 // Need to check

                        customerVisitRepo.insertNewInvoice(invoice)
                        this.invoice.postValue(invoice)

                        // ToDo - for sale return

                        customerVisitRepo.getAllInvoice()
                            .subscribeOn(schedulerProvider.io())
                            .observeOn(schedulerProvider.mainThread())
                            .subscribe{ invoiceList ->
                                Log.d("Testing", "Invoice count = ${invoiceList.size}")
                            }

                        customerVisitRepo.getAllInvoiceProduct()
                            .subscribeOn(schedulerProvider.io())
                            .observeOn(schedulerProvider.mainThread())
                            .subscribe{ invoiceProductList ->
                                Log.d("Testing", "Invoice product count = ${invoiceProductList.size}")
                            }

                    } else Log.d("Testing", "Found same invoice id")

                }
        }

    }

    fun updateDepartureTimeForSaleManRoute(saleManId: String, customerId: String){
        customerVisitRepo.updateDepartureTimeForSaleManRoute(
            saleManId,
            customerId,
            Utils.getCurrentDate(true)
        )
    }

    fun updateSaleVisitRecord(customerId: Int){
        customerVisitRepo.updateSaleVisitRecord(customerId, "1", "1")
    }

}
package com.aceplus.dms.viewmodel.customer.sale

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import android.widget.Toast
import com.aceplus.dms.utils.Utils
import com.aceplus.domain.entity.invoice.Invoice
import com.aceplus.domain.entity.invoice.InvoiceProduct
import com.aceplus.domain.entity.promotion.Promotion
import com.aceplus.domain.model.forApi.invoice.InvoiceDetail
import com.aceplus.domain.repo.CustomerVisitRepo
import com.aceplus.domain.vo.SoldProductInfo
import com.aceplus.shared.viewmodel.BaseViewModel
import com.kkk.githubpaging.network.rx.SchedulerProvider

class SaleCheckoutViewModel(private val customerVisitRepo: CustomerVisitRepo, private val schedulerProvider: SchedulerProvider): BaseViewModel() {

    var locationData = MutableLiveData<Pair<Int, String>>()

    fun getLocation(){

        launch {
            customerVisitRepo.getAllLocation()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe{
                    var locationCode = 0
                    var locationCodeName = ""
                    for (location in it){
                        locationCode = location.location_id.toInt()
                        locationCodeName = location.location_name.toString()
                    }
                    locationData.postValue(Pair(locationCode, locationCodeName))
                }
        }

    }

    fun calculateFinalAmount(){

        var amountAndPercentage: Map<String, Double> = mapOf()
        var sameCategoryProducts: ArrayList<SoldProductInfo> = ArrayList()

        // ToDo - show final amount

    }

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

                        val index: ArrayList<Int> = ArrayList()
                        val tempSoldProduct: ArrayList<SoldProductInfo> = ArrayList()
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
                                customerVisitRepo.updateProductRemainingQty(soldProduct) // Need to repair !!!
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

}
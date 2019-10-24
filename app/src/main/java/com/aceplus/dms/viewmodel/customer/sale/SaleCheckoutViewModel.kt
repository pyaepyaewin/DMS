package com.aceplus.dms.viewmodel.customer.sale

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

class SaleCheckoutViewModel(
    private val customerVisitRepo: CustomerVisitRepo,
    private val schedulerProvider: SchedulerProvider
): BaseViewModel() {

    private val invoice = Invoice()

    fun calculateFinalAmount(){

        var amountAndPercentage: Map<String, Double> = mapOf()
        var sameCategoryProducts: ArrayList<SoldProductInfo> = ArrayList()

        // ToDo - show final amount

    }

    fun saveCheckoutData(
        customerId: String,
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

        launch {
            customerVisitRepo.getInvoiceCountByID(invoiceId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe{

                    if (it == 0){

                        val index: ArrayList<Int> = ArrayList()
                        val tempSoldProduct: ArrayList<SoldProductInfo> = ArrayList()

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
                            invoiceProduct.promotion_plan_id = soldProduct.promotionPlanId.toInt()
                            invoiceProduct.promotion_price = soldProduct.promoPriceByDiscount
                            invoiceProduct.item_discount_percent = soldProduct.focPercent
                            invoiceProduct.item_discount_amount = soldProduct.focAmount
                            invoiceProduct.exclude = soldProduct.exclude.toString()

                            totalQtyForInvoice += soldProduct.quantity

                            if (soldProduct.totalAmt != 0.0){
                                customerVisitRepo.insertInvoiceProduct(invoiceProduct)
                                customerVisitRepo.updateProductRemainingQty(soldProduct)
                            }

                            if (soldProduct.focQuantity > 0 && soldProduct.totalAmt == 0.0){
                                // ToDo - add promotion list
                            }

                        }

                        for (promotion in promotionList){
                            // ToDo - update qty
                        }

                        /*totalDiscountAmount = volDisAmount
                        totalVolumeDiscountPercent = volDisPercent*/
                        invoice.invoice_id = invoiceId
                        invoice.customer_id = customerId
                        invoice.sale_date = saleDate
                        invoice.total_amount = totalAmount.toString()
                        invoice.total_quantity = totalQtyForInvoice.toDouble() // Check int or double
                        invoice.pay_amount = payAmount.toString()
                        invoice.refund_amount = refundAmount.toString()
                        invoice.receipt_person_name = receiptPerson
                        invoice.sale_person_id = salePersonId
                        invoice.location_code = "" // Need to add
                        invoice.device_id = deviceId
                        invoice.invoice_time = invoiceTime
                        invoice.customer_id = "1"
                        invoice.invoice_status = cashOrLoanOrBank
                        //invoice.setDiscountPercent(totalVolumeDiscountPercent)
                        invoice.rate = "1"
                        invoice.tax_amount = taxAmt
                        invoice.due_date = dueDate

                        if (bank.isNotBlank()) invoice.bank_name = bank
                        if (acc.isNotBlank()) invoice.bank_account_no = acc


                    } else Log.d("Testing", "Found same invoice id")

                }
        }

    }

}
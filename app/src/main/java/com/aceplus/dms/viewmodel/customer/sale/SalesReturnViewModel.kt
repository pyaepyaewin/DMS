package com.aceplus.dms.viewmodel.customer.sale

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.aceplus.dms.utils.Utils
import com.aceplus.domain.entity.invoice.Invoice
import com.aceplus.domain.entity.invoice.InvoiceProduct
import com.aceplus.domain.entity.product.Product
import com.aceplus.domain.entity.sale.salereturn.SaleReturn
import com.aceplus.domain.entity.sale.salereturn.SaleReturnDetail
import com.aceplus.domain.repo.CustomerVisitRepo
import com.aceplus.domain.vo.SoldProductInfo
import com.aceplus.shared.viewmodel.BaseViewModel
import com.kkk.githubpaging.network.rx.SchedulerProvider

class SalesReturnViewModel(
    private val customerVisitRepo: CustomerVisitRepo,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {

    var productDataList = MutableLiveData<Pair<List<Product>, List<String>>>()
    var taxType = MutableLiveData<String>()
    var calculatedProductList = MutableLiveData<Triple<ArrayList<SoldProductInfo>, Double, Double>>()
    var saleReturnSuccessState = MutableLiveData<Any>()

    var taxInfo = Pair("", 0)


    fun getSaleManID(): String{
        val saleManData = customerVisitRepo.getSaleManData()
        return saleManData.id
    }

    fun getRouteID(): Int{
        return customerVisitRepo.getRouteScheduleIDV2()
    }

    fun getLastCountForInvoiceNumber(mode: String) = customerVisitRepo.getLastCountForInvoiceNumber(mode)

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

    fun getTaxInfo(){
        launch {
            customerVisitRepo.getCompanyInfo()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe{
                    for (i in it){
                        taxInfo = Pair(i.tax_type!!, i.tax!!)
                        taxType.postValue(i.tax_type)
                    }
                }
        }
    }

    fun calculateSelectedProductData(productList: ArrayList<SoldProductInfo>){

        val calculatedSoldProductList = ArrayList<SoldProductInfo>()
        var netAmount = 0.0
        var taxAmount: Double

        for (product in productList){

            val price = product.product.selling_price.toString().toDouble()
            val totalAmt = price * product.quantity
            val netAmt = totalAmt - product.discountAmount - product.extraDiscountAmount // Checking required - disc & extra disc amount

            netAmount += netAmt

            product.totalAmt = totalAmt
            calculatedSoldProductList.add(product)

        }

        taxAmount = calculateTaxAmt(netAmount, taxInfo.second)
        calculatedProductList.postValue(Triple(calculatedSoldProductList, netAmount, taxAmount))

    }

    private fun calculateTaxAmt(netAmount: Double, taxPercent: Int): Double{

        return netAmount / 21

    }

    @SuppressLint("CheckResult")
    fun saveData(
        isSaleExchange: Boolean,
        saleReturnID: String,
        salePersonID: Int,
        customerID: Int,
        locationID: Int,
        netAmount: Double,
        payAmount: Double,
        deviceID: String,
        discAmount: Double,
        taxAmount: Double,
        taxPercent: Int,
        selectedProductList: List<SoldProductInfo>
    ){

        customerVisitRepo.getSaleReturnCountByID(saleReturnID)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.mainThread())
            .subscribe{

                if (it == 0){

                    val saleReturn = SaleReturn()
                    saleReturn.sale_return_id = saleReturnID
                    saleReturn.sale_man_id = salePersonID
                    saleReturn.customer_id = customerID
                    saleReturn.location_id = locationID
                    saleReturn.amount = netAmount
                    saleReturn.pay_amount = payAmount
                    saleReturn.pc_address = deviceID
                    saleReturn.return_date = Utils.getCurrentDate(false)
                    saleReturn.invoice_status = if (payAmount >= netAmount) "CA" else "CR"
                    saleReturn.discount = discAmount
                    saleReturn.tax = taxAmount
                    saleReturn.tax_percent = taxPercent.toDouble()

                    customerVisitRepo.insertSaleReturn(saleReturn) // Need to check cuz I changed delete flag type

                    val saleReturnDetailList = ArrayList<SaleReturnDetail>()

                    for (product in selectedProductList){

                        val saleReturnDetail = SaleReturnDetail()
                        saleReturnDetail.sale_return_id = saleReturnID
                        saleReturnDetail.product_id = product.product.id
                        saleReturnDetail.price = product.product.selling_price?.toDouble() ?: 0.0
                        saleReturnDetail.quantity = product.quantity
                        saleReturnDetail.remark = product.remark

                        saleReturnDetailList.add(saleReturnDetail)

                    }

                    customerVisitRepo.insertAllSaleReturnDetail(saleReturnDetailList) // Need to check cuz I changed delete flag type

                    for (saleReturnDetail in saleReturnDetailList){

                        customerVisitRepo.updateRemainingQtyWithExchangeOrReturn(
                            isSaleExchange,
                            saleReturnDetail.quantity,
                            saleReturnDetail.product_id
                        )

                        /*customerVisitRepo.getProductByID(saleReturnDetail.product_id)
                            .subscribeOn(schedulerProvider.io())
                            .observeOn(schedulerProvider.mainThread())
                            .subscribe{
                                Log.d("Testing", "${it[0].product_name}\tRemaining Qty = ${it[0].remaining_quantity}\t${if (isSaleExchange) "Exchange Qty" else "Return Qty"} = ${if (isSaleExchange) it[0].exchange_quantity else it[0].return_quantity}")
                            }*/

                    }

                    if (isSaleExchange){

                        saleReturnSuccessState.postValue(payAmount + discAmount)

                    } else{

                        val invoice = Invoice()
                        invoice.invoice_id = saleReturnID
                        invoice.sale_date = Utils.getCurrentDate(true)
                        invoice.customer_id = customerID.toString()
                        invoice.location_code = locationID.toString()
                        invoice.total_amount = netAmount.toString()
                        invoice.pay_amount = payAmount.toString()
                        invoice.total_discount_amount = discAmount
                        invoice.device_id = deviceID
                        invoice.invoice_status = saleReturn.invoice_status
                        invoice.sale_person_id = salePersonID.toString()

                        saleReturnSuccessState.postValue(invoice)

                    }

                } else Log.d("Testing", "Found same invoice id")

            }

    }

}
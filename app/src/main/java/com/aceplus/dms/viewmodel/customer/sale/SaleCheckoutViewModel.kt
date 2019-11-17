package com.aceplus.dms.viewmodel.customer.sale

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.aceplus.data.utils.Constant
import com.aceplus.dms.utils.Utils
import com.aceplus.domain.entity.SMSRecord
import com.aceplus.domain.entity.invoice.Invoice
import com.aceplus.domain.entity.invoice.InvoiceProduct
import com.aceplus.domain.entity.preorder.PreOrder
import com.aceplus.domain.entity.preorder.PreOrderProduct
import com.aceplus.domain.entity.promotion.Promotion
import com.aceplus.domain.model.forApi.invoice.InvoiceDetail
import com.aceplus.domain.model.forApi.preorder.*
import com.aceplus.domain.repo.CustomerVisitRepo
import com.aceplus.domain.vo.CalculatedFinalData
import com.aceplus.domain.vo.SoldProductInfo
import com.aceplus.shared.viewmodel.BaseViewModel
import com.kkk.githubpaging.network.rx.SchedulerProvider
import kotlin.math.roundToInt

class SaleCheckoutViewModel(private val customerVisitRepo: CustomerVisitRepo, private val schedulerProvider: SchedulerProvider): BaseViewModel() {

    var invoice = MutableLiveData<Invoice>()
    var finalData = MutableLiveData<CalculatedFinalData>()
    var messageInfo = MutableLiveData<Pair<String, String>>()
    var uploadResult = MutableLiveData<Boolean>()

    fun getSaleManID(): String{
        val saleManData = customerVisitRepo.getSaleManData()
        return saleManData.id
    }

    fun getRouteID(): Int{
        return customerVisitRepo.getRouteScheduleIDV2()
    }

    fun getInvoiceNumber(saleManId:String,locationNumber:Int,invoiceMode:String): String{
        return Utils.getInvoiceNo(
            saleManId,
            locationNumber.toString(),
            invoiceMode,
            customerVisitRepo.getLastCountForInvoiceNumber(invoiceMode)
        )
    }

    @SuppressLint("CheckResult")
    fun calculateFinalAmount(soldProductList: ArrayList<SoldProductInfo>, totalAmount: Double){

        var amountAndPercentage: MutableMap<String, Double> = mutableMapOf()
        var sameCategoryProducts: ArrayList<SoldProductInfo> = ArrayList()

        var exclude: Int? = 0
        var volDisFilterId = 0
        var soldPrice = 0.0
        var totalBuyAmtInclude = 0.0
        var totalBuyAmtExclude = 0.0
        var discountPercent = 0.0

        var totalVolumeDiscount = 0.0
        var totalVolumeDiscountPercent = 0.0

        var taxType = ""
        var taxPercent = 0

        launch {
            customerVisitRepo.getVolumeDiscountFilterByDate(Utils.getCurrentDate(true))
                .flatMap {

                    for (i in it){

                        volDisFilterId = i.id
                        exclude = i.exclude?.toInt()

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

                            soldPrice = if (aSameCategoryProduct.promotionPrice == 0.0){
                                aSameCategoryProduct.product.selling_price?.toDouble() ?: 0.0
                            } else{
                                aSameCategoryProduct.promotionPrice
                            }

                            var buyAmt = soldPrice * aSameCategoryProduct.quantity
                            aSameCategoryProduct.totalAmt = buyAmt

                            totalBuyAmtInclude += aSameCategoryProduct.totalAmt

                            if (aSameCategoryProduct.promotionPrice == 0.0)
                                totalBuyAmtExclude += aSameCategoryProduct.totalAmt

                        }

                        if (exclude == 0){

                            customerVisitRepo.getDiscountPercentFromVolumeDiscountFilterItem(volDisFilterId, totalBuyAmtInclude)
                                .subscribeOn(schedulerProvider.io())
                                .observeOn(schedulerProvider.mainThread())
                                .subscribe{ discList ->
                                    if (discList.isEmpty()) exclude = null
                                    for (disc in discList){
                                        discountPercent = disc.discount_percent?.toDouble() ?: 0.0
                                    }
                                }

                            amountAndPercentage["Percentage"] = discountPercent
                            var itemTotalDis = totalBuyAmtInclude * (discountPercent / 100)
                            amountAndPercentage["Amount"] = itemTotalDis

                            // Check what's this for ... exceed?
                            if (discountPercent > 0){
                                for (soldProduct in sameCategoryProducts){
                                    soldProduct.exclude = exclude
                                    val discountAmount = soldProduct.totalAmt * (discountPercent / 100)
                                    soldProduct.discountPercent = discountPercent
                                    soldProduct.discountAmount = discountAmount
                                    //soldProduct.totalAmt = soldProduct.totalAmount // Check point
                                }
                            }

                        } else{

                            customerVisitRepo.getDiscountPercentFromVolumeDiscountFilterItem(volDisFilterId, totalBuyAmtExclude)
                                .subscribeOn(schedulerProvider.io())
                                .observeOn(schedulerProvider.mainThread())
                                .subscribe{ discList ->
                                    if (discList.isEmpty()) exclude = null
                                    for (disc in discList){
                                        discountPercent = disc.discount_percent?.toDouble() ?: 0.0
                                    }
                                }

                            amountAndPercentage["Percentage"] = discountPercent
                            var itemTotalDis = totalBuyAmtInclude * (discountPercent / 100)
                            amountAndPercentage["Amount"] = itemTotalDis

                            // Check what's this for ... exceed?
                            if (discountPercent > 0){
                                for (soldProduct in sameCategoryProducts){
                                    soldProduct.exclude = exclude
                                    val discountAmount = soldProduct.totalAmt * (discountPercent / 100)
                                    soldProduct.discountPercent = discountPercent
                                    soldProduct.discountAmount = discountAmount
                                    //soldProduct.totalAmt = soldProduct.totalAmount // Check point
                                }
                            }

                        }

                    }

                    return@flatMap customerVisitRepo.getVolumeDiscountByDate(Utils.getCurrentDate(true))
                }
                .flatMap {

                    var volDisId = 0
                    var buyAmt = 0.0

                    for (i in it){
                        volDisId = i.id
                        exclude = i.exclude?.toInt()

                        if (exclude == 0){
                            buyAmt = totalAmount
                        } else{
                            var noPromoBuyAmt = 0.0
                            for (soldProduct in soldProductList){
                                if (soldProduct.promotionPrice == 0.0 && soldProduct.discountPercent == 0.0)
                                    noPromoBuyAmt += soldProduct.totalAmt
                            }
                            buyAmt = noPromoBuyAmt
                        }

                        customerVisitRepo.getDiscountPercentFromVolumeDiscountFilterItem(volDisId, buyAmt)
                            .subscribeOn(schedulerProvider.io())
                            .observeOn(schedulerProvider.mainThread())
                            .subscribe{ volDiscFilterItemList ->
                                for (volDiscFilterItem in volDiscFilterItemList){
                                    val discountPercentForVolDis = volDiscFilterItem.discount_percent?.toDouble() ?: 0.0
                                    totalVolumeDiscount = buyAmt * (discountPercentForVolDis / 100)
                                    totalVolumeDiscountPercent = discountPercentForVolDis
                                }
                            }
                    }

                    return@flatMap customerVisitRepo.getCompanyInfo()
                }
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe{ companyInfoList ->

                    for(company in companyInfoList){
                        taxPercent = company.tax ?: 0
                        taxType = company.tax_type ?: ""
                    }

                    val finalData = CalculatedFinalData(amountAndPercentage, totalVolumeDiscount, totalVolumeDiscountPercent, taxType, taxPercent)
                    this.finalData.postValue(finalData)

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
        acc: String,
        totalDiscountAmount: Double,
        totalVolumeDiscountPercent: Double
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

                            invoiceDetailList.add(invoiceDetail) // To Check // For what?


                            val invoiceProduct = InvoiceProduct()
                            invoiceProduct.invoice_product_id = invoiceId
                            invoiceProduct.product_id = soldProduct.product.id.toString()
                            invoiceProduct.sale_quantity = soldProduct.quantity.toString()
                            invoiceProduct.discount_amount = soldProduct.discountAmount.toString()
                            invoiceProduct.total_amount = soldProduct.totalAmt
                            invoiceProduct.discount_percent = soldProduct.discountPercent
                            invoiceProduct.s_price = soldProduct.product.selling_price!!.toDouble()
                            invoiceProduct.p_price = soldProduct.product.purchase_price!!.toDouble()
                            invoiceProduct.promotion_price = soldProduct.promoPriceByDiscount // Check promo price or promo price by disc
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

                        invoice.invoice_id = invoiceId
                        invoice.customer_id = customerId.toString()
                        invoice.sale_date = saleDate
                        invoice.total_amount = totalAmount.toString()
                        invoice.total_discount_amount = totalDiscountAmount // Need to check
                        invoice.pay_amount = payAmount.toString()
                        invoice.refund_amount = refundAmount.toString()
                        invoice.receipt_person_name = receiptPerson
                        invoice.sale_person_id = salePersonId
                        invoice.due_date = dueDate
                        invoice.cash_or_credit = cashOrLoanOrBank
                        invoice.location_code = "" // Need to add - route id
                        invoice.device_id = deviceId
                        invoice.invoice_time = invoiceTime
                        invoice.package_invoice_number = 0 // Need to add
                        invoice.package_status = 0 // Need to check
                        invoice.volume_amount = 0.0 // Need to check
                        invoice.package_grade = "" // Need to check
                        invoice.invoice_product_id = 0 // Need to check
                        invoice.total_quantity = totalQtyForInvoice.toDouble() // Check int or double
                        invoice.invoice_status = cashOrLoanOrBank
                        invoice.total_discount_percent = totalVolumeDiscountPercent.toString()  // Need to check
                        invoice.rate = "1"
                        invoice.tax_amount = taxAmt
                        invoice.bank_name = bank
                        invoice.bank_account_no = acc
                        invoice.sale_flag = 0 // Need to check

                        customerVisitRepo.insertNewInvoice(invoice)
                        this.invoice.postValue(invoice)

                        // ToDo - for sale return

                        customerVisitRepo.getAllInvoice()
                            .flatMap { invoiceList ->
                                Log.d("Testing", "Invoice count = ${invoiceList.size}")
                                return@flatMap customerVisitRepo.getAllInvoiceProduct()
                            }
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

    fun saveOrderData(
        invoiceId: String,
        customerId: Int,
        salePersonId: String,
        deviceId: String,
        preOrderDate: String,
        deliveryDate: String,
        advancedPaymentAmount: Double,
        totalAmount: Double,
        locationID: Int,
        totalDiscountAmount: Double,
        totalVolumeDiscountPercent: Double,
        taxAmt: Double,
        remark: String,
        bank: String,
        acc: String,
        cashOrLoanOrBank: String,
        soldProductList: ArrayList<SoldProductInfo>,
        promotionList: ArrayList<Promotion>
    ){

        launch {
            customerVisitRepo.getOrderInvoiceCountByID(invoiceId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe{

                    if (it == 0){

                        val invoice = Invoice()
                        val preOrder = PreOrder()
                        val preOrderProductList: ArrayList<PreOrderProduct> = ArrayList()

                        for (soldProduct in soldProductList){

                            val preOrderProduct = PreOrderProduct()
                            preOrderProduct.sale_order_id = invoiceId
                            preOrderProduct.product_id = soldProduct.product.id.toString()
                            preOrderProduct.order_quantity = soldProduct.quantity.toString()
                            preOrderProduct.price = soldProduct.product.selling_price
                            preOrderProduct.total_amount = soldProduct.totalAmt
                            preOrderProduct.promotion_price = soldProduct.promoPriceByDiscount.toString() // Check promo price or promo price by disc
                            preOrderProduct.promotion_plan_id = soldProduct.promotionPlanId
                            preOrderProduct.volume_discount = soldProduct.discountAmount.toString()
                            preOrderProduct.volume_discount_percent = soldProduct.discountPercent.toString()
                            //preOrderProduct.item_discount_percent = soldProduct.itemDiscountAmount.toString() // ToDo -  Check
                            //preOrderProduct.item_discount_amount // ToDo -  Check
                            preOrderProduct.exclude = "${soldProduct.exclude}"

                            preOrderProductList.add(preOrderProduct)

                            // ToDo - something

                        }

                        customerVisitRepo.insertAllPreOrderProduct(preOrderProductList)


                        for (promotion in promotionList){
                            // ToDo - something for promotion
                        }

                        invoice.invoice_id = invoiceId
                        invoice.customer_id = customerId.toString()
                        invoice.sale_date = preOrderDate
                        invoice.total_amount = totalAmount.toString()
                        invoice.total_discount_amount = totalDiscountAmount // Need to check
                        invoice.pay_amount = advancedPaymentAmount.toString()
                        invoice.refund_amount = "0.0"
                        invoice.sale_person_id = salePersonId
                        invoice.location_code = locationID.toString()
                        invoice.device_id = deviceId
                        invoice.invoice_status = cashOrLoanOrBank
                        invoice.total_discount_percent = totalVolumeDiscountPercent.toString()
                        invoice.rate = "1"
                        invoice.tax_amount = taxAmt
                        invoice.due_date = deliveryDate
                        // To check - No currency found

                        preOrder.invoice_id = invoiceId
                        preOrder.customer_id = customerId.toString()
                        preOrder.sale_man_id = salePersonId
                        preOrder.dev_id = deviceId
                        preOrder.pre_order_date = preOrderDate
                        preOrder.expected_delivery_date = deliveryDate
                        preOrder.advance_payment_amount = advancedPaymentAmount.toString()
                        preOrder.net_amount = totalAmount.toString() // To Check
                        preOrder.location_id = locationID.toString()
                        preOrder.discount = totalDiscountAmount.toString() // To Check
                        preOrder.discount_percent = totalVolumeDiscountPercent.toString() // To Check
                        preOrder.tax_amount = taxAmt.toString()
                        preOrder.bank_name = bank
                        preOrder.bank_account_no = acc
                        preOrder.remark = remark
                        preOrder.delete_flag = 0
                        preOrder.sale_flag = 0

                        customerVisitRepo.insertPreOrder(preOrder)
                        this.invoice.postValue(invoice)


                        customerVisitRepo.getAllPreOrder()
                            .flatMap { preOrderList ->
                                Log.d("Testing", "Pre Order count = ${preOrderList.size}")
                                return@flatMap customerVisitRepo.getAllPreOrderProduct()
                            }
                            .subscribeOn(schedulerProvider.io())
                            .observeOn(schedulerProvider.mainThread())
                            .subscribe{ preOrderProductList ->
                                Log.d("Testing", "Pre Order Product count = ${preOrderProductList.size}")
                            }

                    } else Log.d("Testing", "Found same invoice id")

                }
        }

    }

    @SuppressLint("CheckResult")
    fun getMessageInfo(
        phoneNo: String,
        invoiceId: String,
        promotionList: ArrayList<Promotion>,
        remark: String
    ){

        var message = ""
        val preOrderPresentApiList = ArrayList<PreOrderPresentApi>()
        var preOrder: PreOrder? = null

        launch {
            customerVisitRepo.getPreOrderByID(invoiceId)
                .flatMap { preOrderList ->

                    if (preOrderList.isNotEmpty()){

                        preOrder = preOrderList[0]

                        message += "Inv: ${preOrder?.invoice_id}" +
                                "\nCus: ${preOrder?.customer_id}, SM: ${preOrder?.sale_man_id}" +
                                "\nSO: ${preOrder?.pre_order_date}"

                        for (promotion in promotionList){

                            val preOrderPresentApi = PreOrderPresentApi()
                            preOrderPresentApi.saleOrderId = invoiceId
                            preOrderPresentApi.productId = promotion.promotion_product_id
                            preOrderPresentApi.quantity = promotion.promotion_quantity

                            message += "\nPromotion product name by id\t${promotion.promotion_quantity}"

                            preOrderPresentApiList.add(preOrderPresentApi) // To check for what

                        }

                    }
                    return@flatMap customerVisitRepo.getPreOrderProductByInvoiceID(invoiceId)
                }
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe{ preOrderProductList ->

                    for (preOrderProduct in preOrderProductList){
                        message += "\n${preOrderProduct.product_id}\t${preOrderProduct.order_quantity}"
                    }

                    message += "\nRemark: $remark\nDL: ${preOrder?.expected_delivery_date}"

                    messageInfo.postValue(Pair(phoneNo, message))
                    Log.d("Testing", message)
                }
        }

    }

    fun saveSmsRecord(smsRecord: SMSRecord) = customerVisitRepo.insertSmsRecord(smsRecord)

    fun getPreOrderRequest(saleManId: String, routeID: String){

        val preOrderApiList = ArrayList<PreOrderApi>()
        val preOrderPresentApiList = ArrayList<PreOrderPresentApi>()

        launch {
            customerVisitRepo.getAllPreOrder()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe{ preOrderList ->

                    for (preOrder in preOrderList){

                        val preOrderApi = PreOrderApi()
                        preOrderApi.id = preOrder.invoice_id
                        preOrderApi.customerId = preOrder.customer_id?.toInt() ?: 0
                        preOrderApi.saleManId = preOrder.sale_man_id
                        preOrderApi.deviceId = preOrder.dev_id
                        preOrderApi.saleOrderDate = preOrder.pre_order_date
                        preOrderApi.expectedDeliveredDate = preOrder.expected_delivery_date
                        preOrderApi.advancedPaymentAmt = preOrder.advance_payment_amount?.toDouble() ?: 0.0
                        preOrderApi.netAmt = preOrder.net_amount?.toDouble() ?: 0.0
                        preOrderApi.locationId = preOrder.location_id?.toInt() ?: 0
                        preOrderApi.discount = preOrder.discount?.toDouble() ?: 0.0
                        preOrderApi.discountPer = preOrder.discount_percent?.toDouble() ?: 0.0
                        preOrderApi.taxAmount = preOrder.tax_amount?.toDouble() ?: 0.0
                        preOrderApi.remark = preOrder.remark
                        preOrderApi.bankName = preOrder.bank_name
                        preOrderApi.bankAccountNo = preOrder.bank_account_no

                        val preOrderDetailApiList = ArrayList<PreOrderDetailApi>()

                        customerVisitRepo.getPreOrderPresentByInvoiceID(preOrder.invoice_id)
                            .flatMap { preOrderPresentList ->

                                for (preOrderPresent in preOrderPresentList){
                                    val preOrderPresentApi = PreOrderPresentApi()
                                    preOrderPresentApi.saleOrderId = preOrderPresent.pre_order_id.toString() // Check type
                                    preOrderPresentApi.productId = preOrderPresent.stock_id
                                    preOrderPresentApi.quantity = preOrderPresent.quantity.roundToInt() // Check type
                                    preOrderPresentApi.status = "Y"
                                    preOrderPresentApiList.add(preOrderPresentApi)
                                }

                                return@flatMap customerVisitRepo.getPreOrderProductByInvoiceID2(preOrder.invoice_id)
                            }
                            .subscribeOn(schedulerProvider.io())
                            .observeOn(schedulerProvider.mainThread())
                            .subscribe{ preOrderProductList ->

                                for (preOrderProduct in preOrderProductList){
                                    val preOrderDetailApi = PreOrderDetailApi()
                                    preOrderDetailApi.saleOrderId = preOrderProduct.sale_order_id
                                    preOrderDetailApi.productId = preOrderProduct.product_id?.toInt() ?: 0
                                    preOrderDetailApi.qty = preOrderProduct.order_quantity?.toDouble() ?: 0.0
                                    preOrderDetailApi.promotionPrice = preOrderProduct.promotion_price?.toDouble() ?: 0.0
                                    preOrderDetailApi.volumeDiscount = preOrderProduct.volume_discount?.toDouble() ?: 0.0
                                    preOrderDetailApi.volumeDiscountPer = preOrderProduct.volume_discount_percent?.toDouble() ?: 0.0
                                    preOrderDetailApi.s_Price = preOrderProduct.price?.toDouble() ?: 0.0

                                    if (!preOrderProduct.exclude.isNullOrBlank())
                                        preOrderDetailApi.exclude = preOrderProduct.exclude!!.toInt()

                                    if (!preOrderProduct.promotion_plan_id.isNullOrBlank())
                                        preOrderDetailApi.promotionPlanId = preOrderProduct.promotion_plan_id!!.toInt()

                                    preOrderDetailApiList.add(preOrderDetailApi)
                                }

                                preOrderApi.preOrderDetailList = preOrderDetailApiList
                                preOrderApiList.add(preOrderApi)

                            }

                    }

                    val preOrderRequestDataList = ArrayList<PreOrderRequestData>() // Check which come first
                    Log.d("Testing", preOrderApiList.size.toString()) // To test skipping the looping

                    val preOrderRequestData = PreOrderRequestData()
                    preOrderRequestData.data = preOrderApiList
                    preOrderRequestData.preorderPresent = preOrderPresentApiList
                    preOrderRequestDataList.add(preOrderRequestData) // Check single data list

                    val preOrderRequest = PreOrderRequest()
                    preOrderRequest.siteActivationKey = Constant.SITE_ACTIVATION_KEY
                    preOrderRequest.tabletActivationKey = Constant.TABLET_ACTIVATION_KEY
                    preOrderRequest.userId = saleManId
                    preOrderRequest.salemanId = saleManId
                    preOrderRequest.password = ""
                    preOrderRequest.route = routeID
                    preOrderRequest.data = preOrderRequestDataList

                    uploadPreOrderToServer(preOrderRequest)

                }
        }

    }

    private fun uploadPreOrderToServer(preOrderRequest: PreOrderRequest){

        val paramData = Utils.getJsonString(preOrderRequest)
        Log.d("Testing", "Req String = $paramData")

        launch {
            customerVisitRepo.uploadPreOrderToServer(paramData)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe{

                    if (it.aceplusStatusCode == 200){

                        if (preOrderRequest.data[0].data.isNotEmpty()){
                            for (preOrderApi in preOrderRequest.data[0].data){
                                // Update delete flag in two
                            }
                            for (preOrderPresentApi in preOrderRequest.data[0].preorderPresent){
                                // Update delete flag
                            }
                        }

                        uploadResult.postValue(true)

                    } else{

                        uploadResult.postValue(false)

                    }

                }
        }

    }

}
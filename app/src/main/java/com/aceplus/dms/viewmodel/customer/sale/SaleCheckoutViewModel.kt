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

class SaleCheckoutViewModel(
    private val customerVisitRepo: CustomerVisitRepo,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {

    var invoice = MutableLiveData<Invoice>()
    var finalData = MutableLiveData<CalculatedFinalData>()
    var messageInfo = MutableLiveData<Pair<String, String>>()
    var uploadResult = MutableLiveData<Boolean>()

    fun getSaleManID(): String {
        val saleManData = customerVisitRepo.getSaleManData()
        return saleManData.id
    }

    fun getRouteID(): Int {
        return customerVisitRepo.getRouteScheduleIDV2()
    }

    fun getLocationCode(): Int{
        return customerVisitRepo.getLocationCode()
    }

    fun getInvoiceNumber(saleManId: String, locationNumber: Int, invoiceMode: String): String {
        return Utils.getInvoiceNo(
            saleManId,
            locationNumber.toString(),
            invoiceMode,
            customerVisitRepo.getLastCountForInvoiceNumber(invoiceMode)
        )
    }

    @SuppressLint("CheckResult")
    fun calculateFinalAmount(soldProductList: ArrayList<SoldProductInfo>, totalAmount: Double) {

        var amountAndPercentage: MutableMap<String, Double> = mutableMapOf()
        var sameCategoryProducts: ArrayList<SoldProductInfo> = ArrayList()

        var exclude: Int?
        var volDisFilterId: Int
        var soldPrice: Double
        var totalBuyAmtInclude = 0.0
        var totalBuyAmtExclude = 0.0
        var discountPercent = 0.0

        var totalVolumeDiscount = 0.0
        var totalVolumeDiscountPercent = 0.0

        var taxType = ""
        var taxPercent = 0

        launch {
            //I think volume discount function is being wrong by logic in old project
            //The whole discount calculation need to be fix
            customerVisitRepo.getVolumeDiscountFilterByDate(Utils.getCurrentDate(true))
                .flatMap {

                    for (i in it) {

                        volDisFilterId = i.id
                        exclude = i.exclude?.toInt()

                        customerVisitRepo.getVolumeDiscountFilterItem(volDisFilterId)
                            .flatMap { volumeDiscFilterItemList ->
                                for (v in volumeDiscFilterItemList) {
                                    for (soldProduct in soldProductList){
                                        if (v.category_id == soldProduct.product.category_id)
                                            sameCategoryProducts.add(soldProduct)
                                    }
                                }
                                for (aSameCategoryProduct in sameCategoryProducts) {

                                    soldPrice = if (aSameCategoryProduct.promotionPrice == 0.0) {
                                        aSameCategoryProduct.product.selling_price?.toDouble() ?: 0.0
                                    } else {
                                        aSameCategoryProduct.promotionPrice
                                    }

                                    var buyAmt = soldPrice * aSameCategoryProduct.quantity
                                    aSameCategoryProduct.totalAmt = buyAmt

                                    totalBuyAmtInclude += aSameCategoryProduct.totalAmt

                                    if (aSameCategoryProduct.promotionPrice == 0.0)
                                        totalBuyAmtExclude += aSameCategoryProduct.totalAmt

                                }
                                if (exclude == 0)
                                    return@flatMap customerVisitRepo.getDiscountPercentFromVolumeDiscountFilterItem(volDisFilterId, totalBuyAmtInclude)
                                else
                                    return@flatMap customerVisitRepo.getDiscountPercentFromVolumeDiscountFilterItem(volDisFilterId, totalBuyAmtExclude)
                            }
                            .subscribeOn(schedulerProvider.io())
                            .observeOn(schedulerProvider.mainThread())
                            .subscribe { discList ->
                                if (discList.isEmpty()) exclude = null
                                for (disc in discList) {
                                    discountPercent = disc.discount_percent?.toDouble() ?: 0.0
                                }
                                amountAndPercentage["Percentage"] = discountPercent
                                var itemTotalDis = totalBuyAmtInclude * (discountPercent / 100)
                                amountAndPercentage["Amount"] = itemTotalDis

                                // ToDo - to add exclude and discount
                            }

                        /*Modified function - no more usage (can delete)
                        for (aSameCategoryProduct in sameCategoryProducts) {

                            soldPrice = if (aSameCategoryProduct.promotionPrice == 0.0) {
                                aSameCategoryProduct.product.selling_price?.toDouble() ?: 0.0
                            } else {
                                aSameCategoryProduct.promotionPrice
                            }

                            var buyAmt = soldPrice * aSameCategoryProduct.quantity
                            aSameCategoryProduct.totalAmt = buyAmt

                            totalBuyAmtInclude += aSameCategoryProduct.totalAmt

                            if (aSameCategoryProduct.promotionPrice == 0.0)
                                totalBuyAmtExclude += aSameCategoryProduct.totalAmt

                        }

                        if (exclude == 0) {

                            customerVisitRepo.getDiscountPercentFromVolumeDiscountFilterItem(volDisFilterId, totalBuyAmtInclude)
                                .subscribeOn(schedulerProvider.io())
                                .observeOn(schedulerProvider.mainThread())
                                .subscribe { discList ->
                                    if (discList.isEmpty()) exclude = null
                                    for (disc in discList) {
                                        discountPercent = disc.discount_percent?.toDouble() ?: 0.0
                                    }
                                    amountAndPercentage["Percentage"] = discountPercent
                                    var itemTotalDis = totalBuyAmtInclude * (discountPercent / 100)
                                    amountAndPercentage["Amount"] = itemTotalDis
                                }

                            // Check what's this for ... exceed?
                            if (discountPercent > 0) {
                                for (soldProduct in sameCategoryProducts) {
                                    soldProduct.exclude = exclude
                                    val discountAmount =
                                        soldProduct.totalAmt * (discountPercent / 100)
                                    soldProduct.discountPercent = discountPercent
                                    soldProduct.discountAmount = discountAmount
                                    //soldProduct.totalAmt = soldProduct.totalAmount // Check point
                                }
                            }

                        } else {

                            customerVisitRepo.getDiscountPercentFromVolumeDiscountFilterItem(volDisFilterId, totalBuyAmtExclude)
                                .subscribeOn(schedulerProvider.io())
                                .observeOn(schedulerProvider.mainThread())
                                .subscribe { discList ->
                                    if (discList.isEmpty()) exclude = null
                                    for (disc in discList) {
                                        discountPercent = disc.discount_percent?.toDouble() ?: 0.0
                                    }
                                    amountAndPercentage["Percentage"] = discountPercent
                                    var itemTotalDis = totalBuyAmtInclude * (discountPercent / 100)
                                    amountAndPercentage["Amount"] = itemTotalDis
                                }

                            // Check what's this for ... exceed?
                            if (discountPercent > 0) {
                                for (soldProduct in sameCategoryProducts) {
                                    soldProduct.exclude = exclude
                                    val discountAmount =
                                        soldProduct.totalAmt * (discountPercent / 100)
                                    soldProduct.discountPercent = discountPercent
                                    soldProduct.discountAmount = discountAmount
                                    //soldProduct.totalAmt = soldProduct.totalAmount // Check point
                                }
                            }

                        }*/

                    }

                    return@flatMap customerVisitRepo.getVolumeDiscountByDate(Utils.getCurrentDate(true))
                }
                .flatMap {

                    //Need to fix - wrong logic

                    var volDisId: Int
                    var buyAmt: Double

                    for (i in it) {

                        volDisId = i.id
                        exclude = i.exclude?.toInt()

                        if (exclude == 0) {
                            buyAmt = totalAmount
                        } else {
                            var noPromoBuyAmt = 0.0
                            for (soldProduct in soldProductList) {
                                if (soldProduct.promotionPrice == 0.0 && soldProduct.discountPercent == 0.0)
                                    noPromoBuyAmt += soldProduct.totalAmt
                            }
                            buyAmt = noPromoBuyAmt
                        }

                        customerVisitRepo.getDiscountPercentFromVolumeDiscountFilterItem(volDisId, buyAmt)
                            .subscribeOn(schedulerProvider.io())
                            .observeOn(schedulerProvider.mainThread())
                            .subscribe { volDiscFilterItemList ->
                                for (volDiscFilterItem in volDiscFilterItemList) {
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
                .subscribe { companyInfoList ->

                    for (company in companyInfoList) {
                        taxPercent = company.tax ?: 0
                        taxType = company.tax_type ?: ""
                    }

                    val finalData = CalculatedFinalData(
                        amountAndPercentage,
                        totalVolumeDiscount,
                        totalVolumeDiscountPercent,
                        taxType,
                        taxPercent
                    )
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
        totalDiscountPercent: Double,
        saleReturnInvoiceNo: String?,
        routeID: Int
    ) {

        var totalQtyForInvoice = 0
        val invoice = Invoice()

        launch {
            customerVisitRepo.getInvoiceCountByID(invoiceId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {

                    if (it == 0) {

                        val invoiceProductList: ArrayList<InvoiceProduct> = ArrayList()
                        val tempSoldProduct: ArrayList<SoldProductInfo> = ArrayList()

                        for (soldProduct in soldProductList) {

                            val invoiceProduct = InvoiceProduct()
                            invoiceProduct.invoice_product_id = invoiceId
                            invoiceProduct.product_id = soldProduct.product.id.toString()
                            invoiceProduct.sale_quantity = soldProduct.quantity.toString()
                            invoiceProduct.discount_amount = soldProduct.discountAmount.toString() //Need to add from disc calculation
                            invoiceProduct.total_amount = soldProduct.totalAmt
                            invoiceProduct.discount_percent = soldProduct.discountPercent //Need to add from disc calculation
                            invoiceProduct.s_price = soldProduct.product.selling_price!!.toDouble()
                            invoiceProduct.p_price = soldProduct.product.purchase_price!!.toDouble()
                            invoiceProduct.promotion_price = soldProduct.promoPriceByDiscount //Check promo price or promo price by disc
                            invoiceProduct.item_discount_percent = soldProduct.focPercent //Inserted only one
                            invoiceProduct.item_discount_amount = soldProduct.focAmount //Inserted only one
                            invoiceProduct.exclude = soldProduct.exclude?.toString() ?: "0" //Need to add from disc calculation

                            if (!soldProduct.promotionPlanId.isNullOrEmpty())
                                invoiceProduct.promotion_plan_id = soldProduct.promotionPlanId.toInt()

                            totalQtyForInvoice += soldProduct.quantity

                            if (soldProduct.totalAmt != 0.0) {
                                invoiceProductList.add(invoiceProduct)
                                customerVisitRepo.updateProductRemainingQty(soldProduct) //Need to remind kkk!! //Can move to behind saving product
                            }

                            if (soldProduct.focQuantity > 0 && soldProduct.totalAmt == 0.0) {
                                // ToDo - add promotion list
                                tempSoldProduct.add(soldProduct)
                            }

                        }

                        customerVisitRepo.insertAllInvoiceProduct(invoiceProductList)

                        soldProductList.removeAll(tempSoldProduct)

                        for (promotion in promotionList) {
                            // ToDo - update present qty, remaining qty
                            // ToDo - insert invoice present
                        }

                        invoice.invoice_id = invoiceId
                        invoice.customer_id = customerId.toString()
                        invoice.sale_date = saleDate
                        invoice.total_amount = totalAmount.toString()
                        invoice.total_discount_amount = totalDiscountAmount //Check - salesman disc or total disc
                        invoice.pay_amount = payAmount.toString()
                        invoice.refund_amount = refundAmount.toString()
                        invoice.receipt_person_name = receiptPerson
                        invoice.sale_person_id = salePersonId
                        invoice.due_date = dueDate
                        invoice.cash_or_credit = cashOrLoanOrBank
                        invoice.location_code = routeID.toString()
                        invoice.device_id = deviceId
                        invoice.invoice_time = invoiceTime
                        invoice.package_invoice_number = 0 // Need to add
                        invoice.package_status = 0 //Need to check
                        invoice.volume_amount = 0.0 //Need to check
                        invoice.package_grade = "" //Need to check
                        invoice.invoice_product_id = 0 //should remove - wrong type
                        invoice.total_quantity = totalQtyForInvoice.toDouble() //Check int or double
                        invoice.invoice_status = cashOrLoanOrBank
                        invoice.total_discount_percent = totalDiscountPercent.toString() //Check - salesman disc or total disc
                        invoice.rate = "1"
                        invoice.tax_amount = taxAmt
                        invoice.bank_name = bank
                        invoice.bank_account_no = acc
                        invoice.sale_flag = 0 //Need to check - 1 or 2

                        customerVisitRepo.insertNewInvoice(invoice)
                        this.invoice.postValue(invoice)

                        if (!saleReturnInvoiceNo.isNullOrBlank()){
                            customerVisitRepo.updateSaleIdInSaleReturn(saleReturnInvoiceNo!!, invoiceId)
                        }

                        /*customerVisitRepo.getAllInvoice()
                            .flatMap { invoiceList ->
                                Log.d("Testing", "Invoice count = ${invoiceList.size}")
                                return@flatMap customerVisitRepo.getAllInvoiceProduct()
                            }
                            .subscribeOn(schedulerProvider.io())
                            .observeOn(schedulerProvider.mainThread())
                            .subscribe { invoiceProductList ->
                                Log.d(
                                    "Testing",
                                    "Invoice product count = ${invoiceProductList.size}"
                                )
                            }*/

                    } else Log.d("Testing", "Found same invoice id")

                }
        }

    }

    fun updateDepartureTimeForSaleManRoute(saleManId: String, customerId: String) {
        customerVisitRepo.updateDepartureTimeForSaleManRoute(
            saleManId,
            customerId,
            Utils.getCurrentDate(true)
        )
    }

    fun updateSaleVisitRecord(customerId: Int) {
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
        routeID: Int,
        totalDiscountAmount: Double,
        totalDiscountPercent: Double,
        taxAmt: Double,
        remark: String,
        bank: String,
        acc: String,
        cashOrLoanOrBank: String,
        soldProductList: ArrayList<SoldProductInfo>,
        promotionList: ArrayList<Promotion>
    ) {
        launch {
            customerVisitRepo.getOrderInvoiceCountByID(invoiceId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {

                    if (it == 0) {

                        val invoice = Invoice()
                        val preOrder = PreOrder()
                        val preOrderProductList: ArrayList<PreOrderProduct> = ArrayList()
                        val tempSoldProduct: ArrayList<SoldProductInfo> = ArrayList()

                        for (soldProduct in soldProductList) {

                            val preOrderProduct = PreOrderProduct()
                            preOrderProduct.sale_order_id = invoiceId
                            preOrderProduct.product_id = soldProduct.product.id.toString()
                            preOrderProduct.order_quantity = soldProduct.quantity.toString()
                            preOrderProduct.price = soldProduct.product.selling_price
                            preOrderProduct.total_amount = soldProduct.totalAmt
                            preOrderProduct.promotion_price = soldProduct.promoPriceByDiscount.toString() //Check promo-price or promo-price-by-disc
                            preOrderProduct.promotion_plan_id = soldProduct.promotionPlanId
                            preOrderProduct.volume_discount = soldProduct.discountAmount.toString()
                            preOrderProduct.volume_discount_percent = soldProduct.discountPercent.toString()
                            preOrderProduct.item_discount_percent = soldProduct.itemDiscountAmount.toString() // ToDo -  not set in old project
                            //preOrderProduct.item_discount_amount // ToDo -  not exist in sold product
                            preOrderProduct.exclude = soldProduct.exclude?.toString()

                            if (soldProduct.totalAmt != 0.0){
                                preOrderProductList.add(preOrderProduct)
                                customerVisitRepo.updateOrderQty(soldProduct) //Need to remind kkk!!
                            }

                            if (soldProduct.focQuantity > 0 && soldProduct.totalAmt == 0.0){
                                // ToDo - add promotion list
                                tempSoldProduct.add(soldProduct)
                            }

                        }

                        customerVisitRepo.insertAllPreOrderProduct(preOrderProductList)

                        soldProductList.removeAll(tempSoldProduct)

                        for (promotion in promotionList) {
                            // ToDo - something for promotion
                        }

                        invoice.invoice_id = invoiceId
                        invoice.customer_id = customerId.toString()
                        invoice.sale_date = preOrderDate
                        invoice.total_amount = totalAmount.toString()
                        invoice.total_discount_amount = totalDiscountAmount
                        invoice.pay_amount = advancedPaymentAmount.toString()
                        invoice.refund_amount = "0.0"
                        invoice.sale_person_id = salePersonId
                        invoice.location_code = routeID.toString()
                        invoice.device_id = deviceId
                        invoice.invoice_status = cashOrLoanOrBank
                        invoice.total_discount_percent = totalDiscountPercent.toString()
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
                        preOrder.net_amount = totalAmount.toString()
                        preOrder.location_id = routeID.toString()
                        preOrder.discount = totalDiscountAmount.toString()
                        preOrder.discount_percent = totalDiscountPercent.toString()
                        preOrder.tax_amount = taxAmt.toString()
                        preOrder.bank_name = bank
                        preOrder.bank_account_no = acc
                        preOrder.remark = remark
                        preOrder.delete_flag = 0
                        preOrder.sale_flag = 0

                        customerVisitRepo.insertPreOrder(preOrder)
                        this.invoice.postValue(invoice)

                        /*customerVisitRepo.getAllPreOrder()
                            .flatMap { preOrderList ->
                                Log.d("Testing", "Pre Order count = ${preOrderList.size}")
                                return@flatMap customerVisitRepo.getAllPreOrderProduct()
                            }
                            .subscribeOn(schedulerProvider.io())
                            .observeOn(schedulerProvider.mainThread())
                            .subscribe { preOrderProductList ->
                                Log.d("Testing", "Pre Order Product count = ${preOrderProductList.size}")
                            }*/

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
    ) {

        var message = ""
        val preOrderPresentApiList = ArrayList<PreOrderPresentApi>()
        var preOrder: PreOrder? = null

        launch {
            customerVisitRepo.getActivePreOrderByIDWithName(invoiceId)
                .flatMap { preOrderList ->

                    if (preOrderList.isNotEmpty()) {

                        preOrder = preOrderList[0]

                        message += "Inv: ${preOrder?.invoice_id}" +
                                "\nCus: ${preOrder?.customer_id}, SM: ${preOrder?.sale_man_id}" +
                                "\nSO: ${preOrder?.pre_order_date}"

                        for (promotion in promotionList) {

                            val preOrderPresentApi = PreOrderPresentApi()
                            preOrderPresentApi.saleOrderId = invoiceId
                            preOrderPresentApi.productId = promotion.promotion_product_id
                            preOrderPresentApi.quantity = promotion.promotion_quantity

                            message += "\n${promotion.name}\t${promotion.promotion_quantity}" // To check name by id

                            preOrderPresentApiList.add(preOrderPresentApi) // To check for what

                        }

                    }
                    return@flatMap customerVisitRepo.getActivePreOrderProductByInvoiceIDWithName(invoiceId)
                }
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe { preOrderProductList ->

                    for (preOrderProduct in preOrderProductList) {
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
        val invoiceNoList = ArrayList<String>()
        val preOrderApiList = ArrayList<PreOrderApi>()
        val preOrderPresentApiList = ArrayList<PreOrderPresentApi>()

        launch {
            customerVisitRepo.getAllActivePreOrder()
                .flatMap { preOrderList ->

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

                        invoiceNoList.add(preOrder.invoice_id)
                        preOrderApiList.add(preOrderApi)

                    }

                    return@flatMap customerVisitRepo.getActivePreOrderPresentByInvoiceIDList(invoiceNoList)
                }
                .flatMap { preOrderPresentList ->

                    for (preOrderPresent in preOrderPresentList){
                        val preOrderPresentApi = PreOrderPresentApi()
                        preOrderPresentApi.saleOrderId = preOrderPresent.pre_order_id.toString() // Check type
                        preOrderPresentApi.productId = preOrderPresent.stock_id
                        preOrderPresentApi.quantity = preOrderPresent.quantity.roundToInt() // Check type
                        preOrderPresentApi.status = "Y"
                        preOrderPresentApiList.add(preOrderPresentApi)
                    }

                    return@flatMap customerVisitRepo.getActivePreOrderProductByInvoiceIDList(invoiceNoList)
                }
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe{ preOrderProductList ->

                    for (index in invoiceNoList.indices){

                        val preOrderDetailApiList = ArrayList<PreOrderDetailApi>()

                        for (preOrderProduct in preOrderProductList){

                            if (preOrderApiList[index].id == preOrderProduct.sale_order_id){

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

                        }

                        preOrderApiList[index].preOrderDetailList = preOrderDetailApiList

                    }

                    val preOrderRequestDataList = ArrayList<PreOrderRequestData>()

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
        //Log.d("Testing", "Req String = $paramData")

        launch {
            customerVisitRepo.uploadPreOrderToServer(paramData)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe{

                    if (it.aceplusStatusCode == 200){

                        if (preOrderRequest.data[0].data.isNotEmpty()){
                            for (preOrderApi in preOrderRequest.data[0].data){
                                customerVisitRepo.updateInactivePreOrderAndPreOrderProductByID(preOrderApi.id)
                            }
                            for (preOrderPresentApi in preOrderRequest.data[0].preorderPresent){
                                customerVisitRepo.updateInactivePreOrderPresentByID(preOrderPresentApi.saleOrderId)
                            }
                        }

                        uploadResult.postValue(true)

                    } else{
                        uploadResult.postValue(false)
                        //Log.d("Testing", it.aceplusStatusMessage)
                    }

                }
        }

    }

}
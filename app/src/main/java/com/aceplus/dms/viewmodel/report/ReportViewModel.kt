package com.aceplus.dms.viewmodel.report

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.aceplus.domain.entity.GroupCode
import com.aceplus.domain.entity.customer.Customer
import com.aceplus.domain.entity.delivery.Delivery
import com.aceplus.domain.entity.delivery.DeliveryItemUpload
import com.aceplus.domain.entity.invoice.Invoice
import com.aceplus.domain.entity.preorder.PreOrder
import com.aceplus.domain.entity.preorder.PreOrderProduct
import com.aceplus.domain.entity.product.Product
import com.aceplus.domain.entity.product.ProductCategory
import com.aceplus.domain.entity.product.ProductGroup
import com.aceplus.domain.entity.route.Route
import com.aceplus.domain.entity.route.RouteScheduleV2
import com.aceplus.domain.entity.sale.salereturn.SaleReturnDetail
import com.aceplus.domain.entity.sale.saletarget.SaleTargetCustomer
import com.aceplus.domain.entity.sale.saletarget.SaleTargetSaleMan
import com.aceplus.domain.repo.report.ReportRepo
import com.aceplus.domain.vo.report.*
import com.aceplus.shared.viewmodel.BaseViewModel
import com.kkk.githubpaging.network.rx.SchedulerProvider
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs

class ReportViewModel(
    private val reportRepo: ReportRepo,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {
    //deliver report
    var reportErrorState = MutableLiveData<String>()
    var deliverReportSuccessState = MutableLiveData<Triple<List<IncompleteDeliverReport>,List<DeliveryItemUpload>,List<TotalAmountForDeliveryReport>>>()
    var deliverDetailReportSuccessState = MutableLiveData<List<DeliverDetailReport>>()
    fun loadDeliverReport() {
        var incompleteList:List<IncompleteDeliverReport> = listOf()
        var deliveryUploadList:List<DeliveryItemUpload> = listOf()
        var totalAmountForDeliveryReport:List<TotalAmountForDeliveryReport>
        launch {
            reportRepo.inCompleteDeliverReport()
                .flatMap {
                    incompleteList = it
                    val invoiceNoList:MutableList<String> = mutableListOf()
                    for (data in it){
                        invoiceNoList.add(data.invoiceId)
                    }
                    return@flatMap reportRepo.getDeliveryItemUploadList(invoiceNoList as List<String>)
                }
                .flatMap {
                    deliveryUploadList = it
                    val idList:MutableList<String> = mutableListOf()
                    for (data in it){
                        idList.add(data.delivery_id!!)
                    }
                    return@flatMap reportRepo.getTotalAmountForDeliveryReport(idList as List<String>)
                }
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe{
                    totalAmountForDeliveryReport = it
                    deliverReportSuccessState.postValue(Triple(incompleteList,deliveryUploadList,totalAmountForDeliveryReport))
                }
        }

    }

    fun loadDeliverDetailReport(invoiceId: String) {
        launch {
            reportRepo.deliverDetailReport(invoiceId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe{
                    deliverDetailReportSuccessState.postValue(it)
                }
        }

    }

    //pre order report and sale order history report
    var preOrderReportSuccessState = MutableLiveData<Pair<List<PreOrderReport>,List<PreOrderProduct>>>()
    var preOrderDetailReportSuccessState = MutableLiveData<List<PreOrderDetailReport>>()
    fun loadPreOrderReport() {
        var preOrderReport:List<PreOrderReport> = listOf()
        var preOrderProductList:List<PreOrderProduct>
        launch {
            reportRepo.preOrderReport()
                .flatMap {
                    preOrderReport = it
                    val invoiceIdList = mutableListOf<String>()
                    for (i in it){
                        invoiceIdList!!.add(i.invoiceId)
                    }
                    return@flatMap reportRepo.preOrderQtyReport(invoiceIdList as List<String>)
                }
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe{
                    preOrderProductList = it
                    preOrderReportSuccessState.postValue(Pair(preOrderReport,preOrderProductList))

                }
        }

    }


    fun loadPreOrderDetailReport(invoiceId: String) {
        launch {
            reportRepo.preOrderDetailReport(invoiceId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe({
                    preOrderDetailReportSuccessState.postValue(it)
                }, {
                    reportErrorState.value = it.localizedMessage
                })
        }

    }

    //product balance report
    var productBalanceReportSuccessState = MutableLiveData<List<Product>>()

    fun loadProductBalanceReport() {
        launch {
            reportRepo.productBalanceReport()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe({
                    productBalanceReportSuccessState.postValue(it)
                    Log.d("Testing", "$it")
                }, {
                    reportErrorState.value = it.localizedMessage
                })
        }

    }

    //sale invoice report and sale exchange tab2
    var saleInvoiceReportList = MutableLiveData<List<SaleInvoiceReport>>()
    var saleInvoiceDetailReportSuccessState = MutableLiveData<List<SaleInvoiceDetailReport>>()
    var saleHistoryForPrintData = MutableLiveData<Invoice>()
    fun loadSaleInvoiceList() {
        launch {
            reportRepo.saleInvoiceReport()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {
                    saleInvoiceReportList.postValue(it)
                }
        }
    }

    fun loadSaleExchangeTab2List() {
        launch {
            reportRepo.saleExchangeTab2Report()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {
                    saleInvoiceReportList.postValue(it)
                }
        }
    }

    // sale history report
    fun loadHistoryInvoiceList() {
        launch {
            reportRepo.saleHistoryReport()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {
                    saleInvoiceReportList.postValue(it)
                }
        }
    }
    var saleInvoiceReportForDateList = MutableLiveData<List<SaleInvoiceReport>>()
    fun loadHistoryInvoiceForDateList(fromDate: String, toDate: String) {
        launch {
            reportRepo.saleHistoryReportForDate(fromDate, toDate)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {
                    saleInvoiceReportForDateList.postValue(it)
                }
        }
    }

    fun loadSaleInvoiceDetailReport(invoiceId: String) {
        launch {
            reportRepo.saleInvoiceDetailReport(invoiceId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe({
                    saleInvoiceDetailReportSuccessState.postValue(it)
                    Log.d("Invoice List", "${it.size}")
                }, {
                    reportErrorState.value = it.localizedMessage
                })
        }

    }

    fun loadSaleInvoiceDetailPrint(invoiceId: String) {
        launch {
            reportRepo.saleInvoiceDetlailPrint(invoiceId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe({
                    saleHistoryForPrintData.postValue(it)
                }, {
                    reportErrorState.value = it.localizedMessage
                })
        }

    }

    //sale cancel report
    var salesCancelReport = MutableLiveData<List<SalesCancelReport>>()
    var saleCancelDetailReportSuccessState = MutableLiveData<List<SaleCancelInvoiceDetailReport>>()

    fun loadSalesCancelReport() {
        launch {
            reportRepo.salesCancelReport()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {
                    salesCancelReport.postValue(it)
                }
        }

    }

    fun loadSaleCancelDetailReport(invoiceId: String) {
        launch {
            reportRepo.salesCancelDetailReport(invoiceId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe({
                    saleCancelDetailReportSuccessState.postValue(it)
                }, {
                    reportErrorState.value = it.localizedMessage
                })
        }

    }
    var saleCancelInvoiceReportForDateList = MutableLiveData<List<SalesCancelReport>>()
    fun loadSaleCancelInvoiceForDateList(fromDate: String, toDate: String) {
        launch {
            reportRepo.saleCancelReportForDate(fromDate, toDate)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {
                    saleCancelInvoiceReportForDateList.postValue(it)
                }
        }
    }

    //sale order history report
    var salesOrderHistoryReportSuccessState = MutableLiveData<List<SalesOrderHistoryFullDataReport>>()
    fun loadSalesOrderHistoryReport() {
         launch {
            reportRepo.salesOrderHistoryReport()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe{
                    salesOrderHistoryReportSuccessState.postValue(it)

                }
        }

    }

    var saleOrderHistoryDateFilterList = MutableLiveData<List<SalesOrderHistoryFullDataReport>>()
    fun loadSaleOrderHistoryDateFilterList(fromDate: String, toDate: String) {
        launch {
            reportRepo.saleHistoryDateFilterList(fromDate, toDate)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {
                    saleOrderHistoryDateFilterList.postValue(it)
                }
        }
    }

    //sale return report
    var salesReturnReportSuccessState = MutableLiveData<Pair<List<SalesReturnQtyReport>,List<SaleReturnDetail>>>()
    var salesReturnDetailReportSuccessState = MutableLiveData<List<SalesReturnDetailReport>>()
    fun loadSalesReturnReport() {
        var salesReturnReportList:List<SalesReturnQtyReport> = listOf()
        var salesReturnQtyReportList:List<SaleReturnDetail>
        launch {
            reportRepo.salesReturnQtyReport()
                .flatMap {
                    salesReturnReportList = it
                    val idList:MutableList<String> = mutableListOf()
                    for (i in it){
                        idList.add(i.saleReturnId)
                    }
                    return@flatMap  reportRepo.salesReturnReport(idList as List<String>)
                }
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe{
                    salesReturnQtyReportList = it
                    salesReturnReportSuccessState.postValue(Pair(salesReturnReportList,salesReturnQtyReportList))
                }
        }

    }

    //sale exchange tab1
       fun loadSalesExchangeTab1Report() {
        var salesReturnReportList:List<SalesReturnQtyReport> = listOf()
        var salesReturnQtyReportList:List<SaleReturnDetail>
        launch {
            reportRepo.salesExchangeTab1Report()
                .flatMap {
                    salesReturnReportList = it
                    val idList:MutableList<String> = mutableListOf()
                    for (i in it){
                        idList.add(i.saleReturnId)
                    }
                    return@flatMap  reportRepo.salesReturnReport(idList as List<String>)
                }
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe{
                    salesReturnQtyReportList = it
                    salesReturnReportSuccessState.postValue(Pair(salesReturnReportList,salesReturnQtyReportList))
                }
        }

    }

    fun loadSalesReturnDetailReport(invoiceId: String) {
        launch {
            reportRepo.salesReturnDetailReport(invoiceId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe({
                    salesReturnDetailReportSuccessState.postValue(it)
                }, {
                    reportErrorState.value = it.localizedMessage
                })
        }

    }

    //sale visit history report
    val customerIdCollection = MutableLiveData<CustomerIdsForSaleVisit>()
    fun checkFromAndToCustomer(fromCusNo: Int, toCusNo: Int, newDate: String){
        val invoiceCustomerId = ArrayList<Int>()
        val preOrderCustomerId = ArrayList<Int>()
        val deliveryUploadCustomerId = ArrayList<Int>()
        val saleReturnCustomerId = ArrayList<Int>()
        val didCustomerFeedBackCustomerId = ArrayList<Int>()
        launch {
            reportRepo.invoiceCheckFromAndToCustomer(fromCusNo,toCusNo,newDate)
                .flatMap {
                    for ( i in it){
                        invoiceCustomerId.add(i.customer_id!!.toInt())
                    }
                    return@flatMap reportRepo.preOrderCheckFromAndToCustomer(fromCusNo,toCusNo,newDate)
                }
                .flatMap {
                    for ( i in it){
                        preOrderCustomerId.add(i.customer_id!!.toInt())
                    }
                    reportRepo.deliveryUploadCheckFromAndToCustomer(fromCusNo,toCusNo,newDate)
                }
                .flatMap {
                    for ( i in it){
                        deliveryUploadCustomerId.add(i.customer_id)
                    }
                    reportRepo.saleReturnCheckFromAndToCustomer(fromCusNo,toCusNo,newDate)
                }
                .flatMap {
                    for ( i in it){
                        saleReturnCustomerId.add(i.customer_id)
                    }
                    reportRepo.didCustomerFeedbackCheckFromAndToCustomer(fromCusNo,toCusNo,newDate)
                }
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {
                    for ( i in it){
                        didCustomerFeedBackCustomerId.add(i.customer_no)
                    }
                    val idCollection = CustomerIdsForSaleVisit(invoiceCustomerId,preOrderCustomerId,deliveryUploadCustomerId,saleReturnCustomerId,didCustomerFeedBackCustomerId)
                    customerIdCollection.postValue(idCollection)

                }
        }
    }


    //unSell reason report
    var unSellReasonReportSuccessState = MutableLiveData<List<UnsellReasonReport>>()
    fun loadUnSellReasonReport() {
        launch {
            reportRepo.unSellReasonReport()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe{
                    unSellReasonReportSuccessState.postValue(it)
                }
        }

    }

    //"sale target and sale man" and "customer"
    var categorySaleTargetDataList = MutableLiveData<List<TargetAndSaleForSaleMan>>()
    var groupSaleTargetDataList = MutableLiveData<List<TargetAndSaleForSaleMan>>()
    var customerSaleTargetDataList = MutableLiveData<List<TargetAndSaleForSaleMan>>()
    var productGroupAndCategoryDataList = MutableLiveData<Pair<List<GroupCode>, List<ProductCategory>>>()
    var targetSaleDBList = MutableLiveData<List<SaleTargetSaleMan>>()
    var targetSaleDBListForCustomer = MutableLiveData<List<SaleTargetCustomer>>()
    fun loadTargetSaleDB(customerId:Int){
        launch {
            reportRepo.getTargetSaleDB(customerId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe{
                    targetSaleDBList.postValue(it)
                }

        }
    }

    fun loadTargetSaleDBForCustomer(customerIdFromSpinner:Int){
        launch {
            reportRepo.getTargetSaleDBForCustomer(customerIdFromSpinner)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe{
                    targetSaleDBListForCustomer.postValue(it)
                }

        }
    }

    fun loadCategorySaleTargetAndSaleIdList(categoryId: String) {
         launch {
            reportRepo.getCategoryListFromInvoiceProduct(categoryId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {
                    categorySaleTargetDataList.postValue(it)
                }
        }

    }

    fun loadGroupSaleTargetAndSaleIdList(groupId: String) {
        launch {
            reportRepo.getGroupListFromInvoiceProduct(groupId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe{
                    groupSaleTargetDataList.postValue(it)
                }
        }

    }

    fun loadCustomerSaleTargetAndSaleIdList(customerId: String) {
        launch {
            reportRepo.getCustomerSaleTargetAndSaleIdList(customerId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe{
                    customerSaleTargetDataList.postValue(it)
                }
        }

    }

    fun loadProductGroupAndProductCategoryList() {
        var groupDataList = listOf<GroupCode>()
        var categoryDataList = listOf<ProductCategory>()
        launch {
            reportRepo.getAllGroupData()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {
                    groupDataList = it
                }
            reportRepo.getAllCategoryData()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {
                    categoryDataList = it
                    productGroupAndCategoryDataList.postValue(
                        Pair(
                            groupDataList,
                            categoryDataList
                        )
                    )
                }
        }
    }

    //sale target and actual for customer
    var customerAndProductGroupAndProductCategoryList = MutableLiveData<Triple<List<Customer>, List<GroupCode>, List<ProductCategory>>>()
    fun loadCustomerAndProductGroupAndProductCategoryList() {
        var groupDataList = listOf<GroupCode>()
        var categoryDataList = listOf<ProductCategory>()
        var customerDataList = listOf<Customer>()
        launch {
            reportRepo.getAllCustomerData()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {
                    customerDataList = it
                }
            reportRepo.getAllGroupData()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {
                    groupDataList = it
                }
            reportRepo.getAllCategoryData()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {
                    categoryDataList = it
                    customerAndProductGroupAndProductCategoryList.postValue(
                        Triple(
                            customerDataList,
                            groupDataList,
                            categoryDataList
                        )
                    )
                }
        }
    }

    //sale target and actual sale for product
    var saleTargetAndSaleManForProductReportSuccessState =
        MutableLiveData<Pair<List<SaleTargetSaleMan>, Triple<List<Product>,List<Product>,List<Product>>>>()
    fun loadNameListForSaleTargetProduct() {
        var groupList = listOf<Product>()
        var categoryList = listOf<Product>()
        var stockList = listOf<Product>()
        var saleTargetAndActualForProductReportList = listOf<SaleTargetSaleMan>()
        launch {
            reportRepo.saleTargetSaleManReport()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {
                    saleTargetAndActualForProductReportList = it
                }
            reportRepo.getActualSale1ForSaleTargetProduct()
                .flatMap {
                   groupList = it
                    return@flatMap reportRepo.getActualSale2ForSaleTargetProduct()
                }
                .flatMap {
                    categoryList = it
                    return@flatMap reportRepo.getActualSale3ForSaleTargetProduct()
                }
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {
                    stockList = it
                    saleTargetAndSaleManForProductReportSuccessState.postValue(Pair(saleTargetAndActualForProductReportList, Triple(groupList,categoryList,stockList)))

                }


        }
    }

    val invoiceProductList = MutableLiveData<Pair<List<TargetAndActualSaleForProduct>,List<Product>>>()
    fun loadInvoiceProductList(idList:List<String>){
        val product = ArrayList<TargetAndActualSaleForProduct>()
        var productList = listOf<Product>()
        val productIdList = ArrayList<String>()
        launch {
            reportRepo.getInvoiceProductList(idList)
                .flatMap {
                    var id = ""
                    var totalAmount = 0.0
                    var saleQuantity = 0
                    for (i in it){
                        id = i.product_id!!
                        totalAmount += i.total_amount
                        saleQuantity += i.sale_quantity!!.toInt()
                        productIdList.add(i.product_id!!)
                    }
                    val productForSaleTarget = TargetAndActualSaleForProduct(id,totalAmount,saleQuantity.toString())
                    product.add(productForSaleTarget)
                    return@flatMap reportRepo.getGroupIdFromProduct(productIdList as List<String>)
                }
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {
                    productList = it
                    invoiceProductList.postValue(Pair(product,productList))
                }

        }
    }
    fun loadProductNameFromProduct(stockId:Int):String{
        var name = ""
        launch {
            reportRepo.getProductNameFromProduct(stockId)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.mainThread())
            .subscribe {
               name = it.product_name!!
            }
        }
        return name
    }

    fun loadGroupCodeNameFromGroupCode(groupId:Int):String{
        var name = ""
        launch {
            reportRepo.getGroupCodeNameFromGroupCode(groupId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {
                    name = it.name!!
                }
        }
        return name
    }

    fun loadCategoryNameFromProductCategory(categoryId:Int):String{
        var name = ""
        launch {
            reportRepo.getCategoryNameFromProductCategory(categoryId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {
                    name = it.category_name!!
                }
        }
        return name
    }

    //end of day report
    var endOfDayReportData = MutableLiveData<EndOfDayReport>()
    fun loadEndOFDayReport(now:String) {
        var totalPayAmt = 0.0
        var saleReturnCount = 0
        var saleExchangeCount = 0
        var totalCashAmount = 0.0
        var totalCashCount = 0
        var totalSaleCount = 0
        var customerCount = 0
        var newCustomerCount = 0
        var planCustomerCount = 0
        var notVisitCount = 0
        var saleDate = ""
        var preOrderList = ArrayList<PreOrder>()
        var amtArr = arrayOf(0.0, 0.0)
        val customerIdList = ArrayList<Int>()
        //Time pattern of input time
        val df = SimpleDateFormat("HH:mm:ss")
        //Time pattern of desired output time
        val outputformat = SimpleDateFormat("hh:mm:ss aa")
        var date: Date? = null
        var output: String = ""
        launch {
            reportRepo.getTotalSaleList(now)
                .flatMap {
                    for (i in it!!){
                        totalPayAmt += i.paid_amount!!.toDouble()
                    }
                    return@flatMap reportRepo.getInvoiceListForEndOfDay(now)
                }
                .flatMap {
                    for (i in it){
                        if (i.pay_amount!!.toDouble() > 0.0){
                            if (i.pay_amount!!.isEmpty()) i.pay_amount = "0.0"
                            if (i.refund_amount!!.isEmpty()) i.refund_amount = "0.0"
                            totalPayAmt += (i.pay_amount!!.toDouble() - i.refund_amount!!.toDouble())
                        }
                    }
                    return@flatMap reportRepo.getPreOrderListForEndOfDay(now)

                }
                .flatMap {
                    preOrderList = it as ArrayList<PreOrder>
                    return@flatMap reportRepo.getSaleReturnListForEndOfDay(now)
                }
                .flatMap {
                    saleReturnCount = it.size
                    for (i in it){
                        amtArr += i.pay_amount
                    }
                    return@flatMap reportRepo.getSaleExchangeListForEndOfDay(now)
                }
                .flatMap {
                    saleExchangeCount = it.size
                    for (i in it){
                        if (i.pay_amount!!.toDouble() > 0.0){
                            i.pay_amount = abs(i.pay_amount!!.toDouble()).toString()
                            amtArr[1] += i.pay_amount!!.toDouble()
                        }
                    }
                    return@flatMap reportRepo.getCashReceiveListForEndOfDay(now)
                }
                .flatMap {
                    totalCashCount = it.size
                    for (i in it){
                        totalCashAmount += i.pay_amount
                    }
                    return@flatMap reportRepo.getSaleCountForEndOfDay(now)
                }
                .flatMap {
                    totalSaleCount = it.size
                    return@flatMap reportRepo.getAllCustomerData()
                }
                .flatMap {
                    customerCount = it.size
                    for (i in it){
                        customerIdList.add(i.id)
                    }
                    return@flatMap reportRepo.getDataNotVisitedCountList()
                }
                .flatMap {
                    for (one in it){
                        if (one.customer_id != null) {
                            for (i in customerIdList.indices) {
                                if (one.customer_id == customerIdList[i]) {
                                    customerIdList.removeAt(i)
                                    break
                                }
                            }

                        }
                    }
                    notVisitCount = customerIdList.size
                    return@flatMap reportRepo.getDataForNewCustomerList()
                }

                .flatMap {
                    newCustomerCount = it.size
                    return@flatMap reportRepo.getPlanCustomerList()
                }
                .flatMap {
                    planCustomerCount = it.size
                    return@flatMap reportRepo.getStartTime(now)
                }
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe{
                    for (i in it){
                        try {
                            saleDate = df.format(i.sale_date)
                            date = df.parse(saleDate)
                            output = outputformat.format(date)
                        }catch (e:Exception) {
                            Log.d("error conversion", e.toString())
                        }
                    }
                    val allData = EndOfDayReport(totalPayAmt,preOrderList,amtArr,saleReturnCount,saleExchangeCount,totalCashAmount,totalCashCount,totalSaleCount,customerCount,newCustomerCount,planCustomerCount,notVisitCount,output)
                    endOfDayReportData.postValue(allData)
                }
        }

    }

    var routeNameForEndOfDayReport = MutableLiveData<List<Route>>()
    fun loadRouteNameForEndOfDayReport(saleManId:String){
        launch {
            reportRepo.getRouteNameForEndOfDayReport(saleManId)
                .flatMap {
                    var routeId = 0
                    if (it.count() > 0){
                        for (i in it) {
                            routeId = i.route_id.toInt()
                        }
                    }
                    return@flatMap reportRepo.getSaleManRouteNameList(routeId)
                }
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe{
                    routeNameForEndOfDayReport.postValue(it)
                }

        }
    }

    fun setCurrentDate(): String {
        val currentDate = Calendar.getInstance().time
        val format = SimpleDateFormat("yyyy-MM-dd")
        return format.format(currentDate)
    }

    fun setEndTime(): String {
        val currentDate = Calendar.getInstance().time
        val format = SimpleDateFormat("h:mm a")
        return format.format(currentDate)
    }


    //spinner data
    var customerDataList = MutableLiveData<List<Customer>>()

    fun loadCustomer() {
        launch {
            reportRepo.getAllCustomerData()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {
                    customerDataList.postValue(it)
                }
        }
    }
}
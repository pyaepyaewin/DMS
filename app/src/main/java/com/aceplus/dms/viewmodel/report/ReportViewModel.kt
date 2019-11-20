package com.aceplus.dms.viewmodel.report

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.aceplus.domain.entity.customer.Customer
import com.aceplus.domain.entity.invoice.Invoice
import com.aceplus.domain.entity.preorder.PreOrderProduct
import com.aceplus.domain.entity.product.Product
import com.aceplus.domain.entity.product.ProductCategory
import com.aceplus.domain.entity.product.ProductGroup
import com.aceplus.domain.entity.sale.saletarget.SaleTargetCustomer
import com.aceplus.domain.entity.sale.saletarget.SaleTargetSaleMan
import com.aceplus.domain.repo.report.ReportRepo
import com.aceplus.domain.vo.report.*
import com.aceplus.shared.viewmodel.BaseViewModel
import com.kkk.githubpaging.network.rx.SchedulerProvider
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ReportViewModel(
    private val reportRepo: ReportRepo,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {
    //deliver report
    var reportErrorState = MutableLiveData<String>()
    var deliverReportSuccessState = MutableLiveData<List<DeliverReport>>()
    var deliverDetailReportSuccessState = MutableLiveData<List<DeliverDetailReport>>()
    fun loadDeliverReport() {

        launch {
            this.reportRepo.deliverReport()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe({
                    deliverReportSuccessState.postValue(it)
                    Log.d("Testing", "$it")
                }, {
                    reportErrorState.value = it.localizedMessage
                })
        }

    }

    fun loadDeliverDetailReport(invoiceId: String) {
        launch {
            reportRepo.deliverDetailReport(invoiceId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe({
                    deliverDetailReportSuccessState.postValue(it)
                    Log.d("Testing", "$it")
                }, {
                    reportErrorState.value = it.localizedMessage
                })
        }

    }

    //pre order report
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
                    Log.d("Testing", "$it")
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

    //sale invoice report
    var saleInvoiceReportList = MutableLiveData<List<SaleInvoiceReport>>()
    var saleInvoiceReportSuccessState =
        MutableLiveData<Pair<List<SaleInvoiceReport>, List<Customer>>>()
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

    fun loadSaleInvoiceReport() {
        var customerDataList: List<Customer>
        var saleInvoiceReport = listOf<SaleInvoiceReport>()
        launch {
            reportRepo.saleHistoryReport()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {
                    saleInvoiceReport = it
                }
            reportRepo.getAllCustomerData()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe({
                    customerDataList = it
                    saleInvoiceReportSuccessState.postValue(
                        Pair(
                            saleInvoiceReport,
                            customerDataList
                        )
                    )
                }, {
                    reportErrorState.value = it.localizedMessage
                })
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
    var salesCancelReportSuccessState =
        MutableLiveData<Pair<List<SalesCancelReport>, List<Customer>>>()
    var saleCancelDetailReportSuccessState =
        MutableLiveData<List<SaleCancelInvoiceDetailReport>>()

    fun loadSalesCancelReport() {
        var customerDataList: List<Customer>
        var saleCancelReport = listOf<SalesCancelReport>()
        launch {
            reportRepo.salesCancelReport()
                .doOnNext {
                    saleCancelReport = it
                }
                .flatMap { reportRepo.getAllCustomerData() }

                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe({
                    customerDataList = it
                    salesCancelReportSuccessState.postValue(
                        Pair(
                            saleCancelReport,
                            customerDataList
                        )
                    )
                }, {
                    reportErrorState.value = it.localizedMessage
                })
        }

    }

    fun loadSaleCancelDetailReport(invoiceId: String) {
        launch {
            reportRepo.salesCancelDetailReport(invoiceId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe({
                    saleCancelDetailReportSuccessState.postValue(it)
                    Log.d("Testing", "$it")
                }, {
                    reportErrorState.value = it.localizedMessage
                })
        }

    }

    //sale order history report
    var salesOrderHistoryReportSuccessState =
        MutableLiveData<Pair<List<SalesOrderHistoryFullDataReport>, List<Customer>>>()

    fun loadSalesOrderHistoryReport() {
        var customerDataList = listOf<Customer>()
        var saleOrderHistoryReport = listOf<SalesOrderHistoryFullDataReport>()
        launch {
            reportRepo.salesOrderHistoryReport()
                .flatMap {
                    saleOrderHistoryReport = it
                    return@flatMap reportRepo.getAllCustomerData()
                }
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe({
                    customerDataList = it
                    salesOrderHistoryReportSuccessState.postValue(
                        Pair(
                            saleOrderHistoryReport,
                            customerDataList
                        )
                    )
                }, {
                    reportErrorState.value = it.localizedMessage
                })
        }

    }

    //sale return report
    var salesReturnReportSuccessState = MutableLiveData<List<SalesReturnReport>>()
    var salesReturnDetailReportSuccessState = MutableLiveData<List<SalesReturnDetailReport>>()
    fun loadSalesReturnReport() {

        launch {
            reportRepo.salesReturnReport()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe({
                    salesReturnReportSuccessState.postValue(it)
                    Log.d("Testing", "$it")
                }, {
                    reportErrorState.value = it.localizedMessage
                })
        }

    }

    fun loadSalesReturnDetailReport(invoiceId: String) {
        launch {
            reportRepo.salesReturnDetailReport(invoiceId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe({
                    salesReturnDetailReportSuccessState.postValue(it)
                    Log.d("Testing", "$it")
                }, {
                    reportErrorState.value = it.localizedMessage
                })
        }

    }

    //sale visit history report
    var salesVisitHistoryReportSuccessState = MutableLiveData<List<SalesVisitHistoryReport>>()

    fun loadSalesVisitHistoryReport() {
        launch {
            reportRepo.salesVisitHistoryReport()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe({
                    salesVisitHistoryReportSuccessState.postValue(it)
                    Log.d("Testing", "$it")
                }, {
                    reportErrorState.value = it.localizedMessage
                })
        }

    }

    //unSell reason report
    var unSellReasonReportSuccessState = MutableLiveData<List<UnsellReasonReport>>()

    fun loadUnSellReasonReport() {
        launch {
            reportRepo.unSellReasonReport()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe({
                    unSellReasonReportSuccessState.postValue(it)
                    Log.d("Testing", "$it")
                }, {
                    reportErrorState.value = it.localizedMessage
                })
        }

    }

    //sale target and sale man
    var saleTargetAndSaleManReportSuccessState =
        MutableLiveData<Pair<List<SaleTargetSaleMan>, List<Invoice>>>()
    var saleTargetAndSaleManIdList =
        MutableLiveData<Pair<List<SaleTargetSaleMan>, List<SaleTargetVO>>>()
    var productGroupAndCategoryDataList =
        MutableLiveData<Pair<List<ProductGroup>, List<ProductCategory>>>()

    fun loadSaleTargetAndSaleManReport() {
        var saleTargetAndSaleManReportList = listOf<SaleTargetSaleMan>()
        var invoiceDataList = listOf<Invoice>()
        launch {
            reportRepo.saleTargetSaleManReport()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {
                    saleTargetAndSaleManReportList = it
                    // Log.d("Testing", "${saleTargetAndSaleManReportList.size}")
                }
            reportRepo.getAllInvoiceData()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe({
                    invoiceDataList = it
                    // Log.d("Testing", "${invoiceDataList.size}")

                    saleTargetAndSaleManReportSuccessState.postValue(
                        Pair(
                            saleTargetAndSaleManReportList,
                            invoiceDataList
                        )
                    )
                }, {
                    reportErrorState.value = it.localizedMessage
                })
        }

    }

    fun loadSaleTargetAndSaleIdList(groupId: Int, categoryId: Int) {
        var saleTargetAndSaleManReportList = listOf<SaleTargetSaleMan>()
        var saleTargetDataList = listOf<SaleTargetVO>()
        launch {
            reportRepo.saleTargetSaleManReport()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {
                    saleTargetAndSaleManReportList = it
                }
            reportRepo.saleTargetAmountForSaleMan(groupId, categoryId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe({
                    saleTargetDataList = it

                    saleTargetAndSaleManIdList.postValue(
                        Pair(
                            saleTargetAndSaleManReportList,
                            saleTargetDataList
                        )
                    )
                }, {
                    reportErrorState.value = it.localizedMessage
                })
        }

    }

    fun loadProductGroupAndProductCategoryList() {
        var groupDataList = listOf<ProductGroup>()
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
    //state of customerId = 0
    var saleTargetCustomerSuccessState =
        MutableLiveData<Pair<List<SaleTargetCustomer>, List<Invoice>>>()

    fun loadSaleTargetAndActualForCustomerReport() {
        var saleTargetAndActualForCustomerReportList = listOf<SaleTargetCustomer>()
        var saleTargetDataList = listOf<Invoice>()
        launch {
            this.reportRepo.saleTargetCustomerReport()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {
                    saleTargetAndActualForCustomerReportList = it
                }
            this.reportRepo.getAllInvoiceData()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe({
                    saleTargetDataList = it
                    saleTargetCustomerSuccessState.postValue(
                        Pair(
                            saleTargetAndActualForCustomerReportList,
                            saleTargetDataList
                        )
                    )
                }, {
                    reportErrorState.value = it.localizedMessage
                })
        }

    }

    //state of customerId != 0
    var saleTargetAmountForCustomerList =
        MutableLiveData<Pair<List<SaleTargetCustomer>, List<SaleTargetVO>>>()

    fun loadSaleTargetAndActualAmountsForCustomerList(
        customerId: Int,
        iCustomerId: String,
        groupId: Int,
        categoryId: Int
    ) {
        var saleTargetAndActualForCustomerReportList = listOf<SaleTargetCustomer>()
        var saleTargetDataList = listOf<SaleTargetVO>()
        launch {
            reportRepo.saleTargetCustomerIdList(customerId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {
                    saleTargetAndActualForCustomerReportList = it
                }

            reportRepo.saleTargetAmountForCustomer(
                iCustomerId,
                groupId,
                categoryId
            )

                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe({
                    saleTargetDataList = it
                    saleTargetAmountForCustomerList.postValue(
                        Pair(
                            saleTargetAndActualForCustomerReportList,
                            saleTargetDataList
                        )
                    )
                }, {
                    reportErrorState.value = it.localizedMessage
                })
        }

    }

    var customerAndProductGroupAndProductCategoryList =
        MutableLiveData<Triple<List<Customer>, List<ProductGroup>, List<ProductCategory>>>()

    fun loadCustomerAndProductGroupAndProductCategoryList() {
        var groupDataList = listOf<ProductGroup>()
        var categoryDataList = listOf<ProductCategory>()
        var customerDataList = listOf<Customer>()
        Log.d("Customer Testing", "function started")
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
        MutableLiveData<Pair<List<SaleTargetSaleMan>, List<TargetAndActualSaleForProduct>>>()

    fun loadNameListForSaleTargetProduct() {
        var saleTargetAndActualForProductReportList = listOf<SaleTargetSaleMan>()
        var nameListForSaleTargetProductList = listOf<TargetAndActualSaleForProduct>()
        launch {
            reportRepo.saleTargetSaleManReport()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {
                    saleTargetAndActualForProductReportList = it
                }
            reportRepo.getNameListForSaleTargetProduct()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {
                    nameListForSaleTargetProductList = it
                    saleTargetAndSaleManForProductReportSuccessState.postValue(
                        Pair(
                            saleTargetAndActualForProductReportList,
                            nameListForSaleTargetProductList
                        )
                    )

                }


        }
    }

    //end of day report
    var endOfDayReportData = MutableLiveData<EndOfDayReport>()

    fun loadEndOFDayReport() {
        var userName = ""
        var routeName = ""
        var startTime = ""
        var endTime = ""
        var totalSaleAmount = 0.0
        var totalSaleOrderAmount = 0.0
        var totalSaleExchangeAmount = 0.0
        var totalSaleCashAmount = 0.0
        var totalSaleNetCashAmount = 0.0
        var totalSaleReturnAmount = 0.0
        var totalSales = ""
        var totalSalesOrder = ""
        var totalExchange = ""
        var totalReturn  = ""
        var totalCashReceive = ""
        var netCash =""
        var totalCustomer = ""
        var newCustomer = ""
        var planCustomer = ""
        var totalSalesCount = ""
        var totalOrderCount = ""
        var totalSalesExchangeOnly = ""
        var totalSalesReturnOnly = ""
        var totalCashReceiptCount = ""
        var notVisitedCount = ""
        launch {
            reportRepo.getSaleManNameList()
                .flatMap {
                    for (i in it) {
                        userName = i.user_name!!
                    }

                    return@flatMap reportRepo.getSaleManRouteNameList()
                }
                .flatMap {
                    for (i in it) {
                        routeName = i.route_name!!
                    }
                    return@flatMap reportRepo.getStartTimeAndEndTimeList()
                }
                .flatMap {
                    for (i in it) {
                        startTime = i.start_time!!
                        endTime = i.end_time!!
                    }
                    return@flatMap reportRepo.getAllInvoiceData()
                }
                .flatMap {
                    for (i in it) {
                        totalSaleAmount += i.total_amount!!.toDouble()
                        totalSales = totalSaleAmount.toString()
                        totalSaleExchangeAmount += i.pay_amount!!.toDouble()
                        totalExchange = totalSaleExchangeAmount.toString()
                        totalSalesCount = it.size.toString()
                    }
                    return@flatMap reportRepo.getAllCustomerData()
                }
                .flatMap {
                    totalCustomer = it.size.toString()
                    return@flatMap reportRepo.getDataForNewCustomerList()
                }
                .flatMap {
                    newCustomer = it.size.toString()
                    return@flatMap reportRepo.getTotalSaleOrderList()
                }
                .flatMap {
                    for (i in it) {
                        totalSaleOrderAmount += i.net_amount!!.toDouble()
                        totalSalesOrder = totalSaleOrderAmount.toString()
                        totalOrderCount = it.size.toString()
                    }
                    return@flatMap reportRepo.getTotalSaleExchangeList()
                }
                .flatMap {
                    totalSalesExchangeOnly = it.size.toString()
                    return@flatMap reportRepo.getTotalSaleReturnList()
                }
                .flatMap {
                    for (i in it) {
                        totalSaleReturnAmount += i.pay_amount
                        totalReturn = totalSaleReturnAmount.toString()
                        totalSalesReturnOnly = it.size.toString()
                    }
                    return@flatMap reportRepo.getTotalCashReceiptList()
                }
                .flatMap {
                    for (i in it) {
                        totalSaleCashAmount += i.pay_amount
                        totalCashReceive = totalSaleCashAmount.toString()
                        totalSaleNetCashAmount += i.pay_amount
                        netCash = totalSaleNetCashAmount.toString()
                        totalCashReceiptCount = it.size.toString()
                    }
                    return@flatMap reportRepo.getPlanCustomerList()
                }
                .flatMap {
                    planCustomer = it.size.toString()
                    return@flatMap reportRepo.getDataNotVisitedCountList()
                }
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {
                    notVisitedCount = it.size.toString()
                    val allData = EndOfDayReport(
                        userName,
                        routeName,
                        startTime,
                        endTime,
                        totalSales,
                        totalSalesOrder,
                        totalExchange,
                        totalReturn,
                        totalCashReceive,
                        netCash,
                        totalCustomer,
                        newCustomer,
                        planCustomer,
                        totalSalesCount,
                        totalOrderCount,
                        totalSalesExchangeOnly,
                        totalSalesReturnOnly,
                        totalCashReceiptCount,
                        notVisitedCount
                    )
                    endOfDayReportData.postValue(allData)
                }
        }
    }

    fun setCurrentDate(): String {
        val currentDate = Calendar.getInstance().time
        val format = SimpleDateFormat("yyyy-MM-dd")
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
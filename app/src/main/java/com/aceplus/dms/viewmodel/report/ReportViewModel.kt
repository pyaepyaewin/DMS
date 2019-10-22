package com.aceplus.dms.viewmodel.report

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.aceplus.domain.entity.CompanyInformation
import com.aceplus.domain.entity.GroupCode
import com.aceplus.domain.entity.cash.CashReceive
import com.aceplus.domain.entity.credit.Credit
import com.aceplus.domain.entity.customer.Customer
import com.aceplus.domain.entity.customer.CustomerBalance
import com.aceplus.domain.entity.customer.CustomerCategory
import com.aceplus.domain.entity.customer.CustomerVisitRecordReport
import com.aceplus.domain.entity.invoice.Invoice
import com.aceplus.domain.entity.invoice.InvoicePresent
import com.aceplus.domain.entity.preorder.PreOrder
import com.aceplus.domain.entity.preorder.PreOrderPresent
import com.aceplus.domain.entity.product.Product
import com.aceplus.domain.entity.product.ProductCategory
import com.aceplus.domain.entity.product.ProductGroup
import com.aceplus.domain.entity.route.Route
import com.aceplus.domain.entity.route.RouteScheduleItemV2
import com.aceplus.domain.entity.sale.SaleMan
import com.aceplus.domain.entity.sale.saleexchange.SaleExchange
import com.aceplus.domain.entity.sale.salereturn.SaleReturn
import com.aceplus.domain.entity.sale.saletarget.SaleTargetCustomer
import com.aceplus.domain.entity.sale.saletarget.SaleTargetSaleMan
import com.aceplus.domain.entity.sale.salevisit.SaleVisitRecordUpload
import com.aceplus.domain.repo.report.ReportRepo
import com.aceplus.domain.vo.report.*
import com.aceplus.shared.viewmodel.BaseViewModel
import com.kkk.githubpaging.network.rx.SchedulerProvider
import java.text.SimpleDateFormat
import java.util.*

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
            reportRepo.deliverReport()
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
    var preOrderReportSuccessState = MutableLiveData<List<PreOrderReport>>()
    var preOrderDetailReportSuccessState = MutableLiveData<List<PreOrderDetailReport>>()
    fun loadPreOrderReport() {
        launch {
            reportRepo.preOrderReport()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe({
                    preOrderReportSuccessState.postValue(it)
                    Log.d("Testing", "$it")
                }, {
                    reportErrorState.value = it.localizedMessage
                })
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
    var saleInvoiceReportSuccessState = MutableLiveData<List<SaleInvoiceReport>>()
    var saleInvoiceDetailReportSuccessState = MutableLiveData<List<SaleInvoiceDetailReport>>()
    fun loadSaleInvoiceReport() {
        launch {
            reportRepo.saleInvoiceReport()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe({
                    saleInvoiceReportSuccessState.postValue(it)
                    Log.d("Testing", "$it")
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
                    Log.d("Testing", "$it")
                }, {
                    reportErrorState.value = it.localizedMessage
                })
        }

    }

    //sale cancel report
    var salesCancelReportSuccessState = MutableLiveData<List<SalesCancelReport>>()
    var saleCancelDetailReportSuccessState = MutableLiveData<List<SaleCancelInvoiceDetailReport>>()
    fun loadSalesCancelReport() {
        launch {
            reportRepo.salesCancelReport()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe({
                    salesCancelReportSuccessState.postValue(it)
                    Log.d("Testing", "$it")
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
    var salesOrderHistoryReportSuccessState = MutableLiveData<List<SalesOrderHistoryReport>>()

    fun loadSalesOrderHistoryReport() {
        launch {
            reportRepo.salesOrderHistoryReport()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe({
                    salesOrderHistoryReportSuccessState.postValue(it)
                    Log.d("Testing", "$it")
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

    fun loadSaleTargetAndSaleManReport() {
        var saleTargetAndSaleManReportList = listOf<SaleTargetSaleMan>()
        var invoiceDataList = listOf<Invoice>()
        launch {
            reportRepo.saleTargetSaleManReport()
                .doOnNext {
                    saleTargetAndSaleManReportList = it
                    // Log.d("Testing", "${saleTargetAndSaleManReportList.size}")
                }
                .flatMap { reportRepo.getAllInvoiceData() }
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe({
                    invoiceDataList = it
                    // Log.d("Testing", "${invoiceDataList.size}")
                }, {
                    reportErrorState.value = it.localizedMessage
                })
        }
        saleTargetAndSaleManReportSuccessState.postValue(
            Pair(
                saleTargetAndSaleManReportList,
                invoiceDataList
            )
        )

    }

    //sale target and actual for customer
    var saleTargetCustomerSuccessState =
        MutableLiveData<Pair<List<SaleTargetCustomer>, List<Invoice>>>()

    fun loadSaleTargetAndActualForCustomerReport() {
        var saleTargetAndActualForCustomerReportList = listOf<SaleTargetCustomer>()
        var invoiceDataList = listOf<Invoice>()
        launch {
            reportRepo.saleTargetCustomerReport()
                .doOnNext {
                    saleTargetAndActualForCustomerReportList = it
                }
                .flatMap { reportRepo.getAllInvoiceData() }
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe({
                    invoiceDataList = it
                }, {
                    reportErrorState.value = it.localizedMessage
                })
        }
        saleTargetCustomerSuccessState.postValue(
            Pair(
                saleTargetAndActualForCustomerReportList,
                invoiceDataList
            )
        )


    }

    //sale target and actual sale for product
    var loadProductNameDataList = MutableLiveData<List<Product>>()
    var loadGroupCodeNameDataList = MutableLiveData<List<GroupCode>>()
    var loadProductCategoryNameDataList = MutableLiveData<List<ProductCategory>>()
    fun loadProductNameList(stockId:Int) {
        launch {
            reportRepo.saleTargetProductReport(stockId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {
                    loadProductNameDataList.postValue(it)
                }
        }
    }
    fun loadGroupCodeNameList(groupNo:Int) {
        launch {
            reportRepo.getGroupCodeListForSaleTargetProduct(groupNo)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {
                    loadGroupCodeNameDataList.postValue(it)
                }
        }
    }

    fun loadProductCategoryNameList(categoryId:String) {
        launch {
            reportRepo.getProductCategoryListForSaleTargetProduct(categoryId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {
                    loadProductCategoryNameDataList.postValue(it)
                }
        }
    }

    //end of day report
    var saleManDataList = MutableLiveData<List<SaleMan>>()
    fun loadSaleManList() {
        launch {
            reportRepo.getSaleManNameList()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {
                    saleManDataList.postValue(it)
                }
        }
    }
    var saleManRouteNameDataList = MutableLiveData<List<Route>>()
    fun loadSaleManRouteNameDataList() {
        launch {
            reportRepo.getSaleManRouteNameList()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {
                    saleManRouteNameDataList.postValue(it)
                }
        }
    }
    var startTimeAndEndTimeList = MutableLiveData<List<CompanyInformation>>()
    fun loadStartTimeAndEndTimeList() {
        launch {
            reportRepo.getStartTimeAndEndTimeList()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {
                    startTimeAndEndTimeList.postValue(it)
                }
        }
    }
    var totalSaleOrderList = MutableLiveData<List<PreOrder>>()
    fun loadTotalSaleOrderList() {
        launch {
            reportRepo.getTotalSaleOrderList()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {
                    totalSaleOrderList.postValue(it)
                }
        }
    }
    var totalSaleExchangeList = MutableLiveData<List<SaleExchange>>()
    fun loadTotalSaleExchangeList() {
        launch {
            reportRepo.getTotalSaleExchangeList()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {
                    totalSaleExchangeList.postValue(it)
                }
        }
    }
    var totalSaleReturnList = MutableLiveData<List<SaleReturn>>()
    fun loadTotalSaleReturnList() {
        launch {
            reportRepo.getTotalSaleReturnList()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {
                    totalSaleReturnList.postValue(it)
                }
        }
    }
    var totalCashReceiptList = MutableLiveData<List<Credit>>()
    fun loadTotalCashReceiptList() {
        launch {
            reportRepo.getTotalCashReceiptList()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {
                    totalCashReceiptList.postValue(it)
                }
        }
    }
    var planCustomerList = MutableLiveData<List<RouteScheduleItemV2>>()
    fun loadPlanCustomerList() {
        launch {
            reportRepo.getPlanCustomerList()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {
                    planCustomerList.postValue(it)
                }
        }
    }
    var dataForNewCustomerList = MutableLiveData<List<Customer>>()
    fun loadDataForNewCustomerList() {
        launch {
            reportRepo.getDataForNewCustomerList()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {
                    dataForNewCustomerList.postValue(it)
                }
        }
    }
    var dataNotVisitedCountList = MutableLiveData<List<SaleVisitRecordUpload>>()
    fun loadDataNotVisitedCountList() {
        launch {
            reportRepo.getDataNotVisitedCountList()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {
                    dataNotVisitedCountList.postValue(it)
                }
        }
    }
    fun setCurrentDate(): String{
        val currentDate = Calendar.getInstance().time
        val format = SimpleDateFormat("yyyy-MM-dd")
        return format.format(currentDate)
    }
    //spinner data
    var customerDataList = MutableLiveData<List<Customer>>()
    var groupDataList = MutableLiveData<List<ProductGroup>>()
    var categoryDataList = MutableLiveData<List<ProductCategory>>()
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

    fun loadProductGroup() {
        launch {
            reportRepo.getAllGroupData()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {
                    groupDataList.postValue(it)
                }
        }
    }

    fun loadProductCategory() {
        launch {
            reportRepo.getAllCategoryData()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {
                    categoryDataList.postValue(it)
                }
        }
    }


}
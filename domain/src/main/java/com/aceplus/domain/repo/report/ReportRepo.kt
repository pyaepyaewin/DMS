package com.aceplus.domain.repo.report

import com.aceplus.domain.entity.CompanyInformation
import com.aceplus.domain.entity.credit.Credit
import com.aceplus.domain.entity.customer.Customer
import com.aceplus.domain.entity.customer.DidCustomerFeedback
import com.aceplus.domain.entity.delivery.Delivery
import com.aceplus.domain.entity.delivery.DeliveryItemUpload
import com.aceplus.domain.entity.delivery.DeliveryUpload
import com.aceplus.domain.entity.invoice.Invoice
import com.aceplus.domain.entity.invoice.InvoiceProduct
import com.aceplus.domain.entity.preorder.PreOrder
import com.aceplus.domain.entity.preorder.PreOrderProduct
import com.aceplus.domain.entity.product.ProductCategory
import com.aceplus.domain.entity.product.ProductGroup
import com.aceplus.domain.entity.route.Route
import com.aceplus.domain.entity.route.RouteScheduleItemV2
import com.aceplus.domain.entity.route.RouteScheduleV2
import com.aceplus.domain.entity.sale.SaleMan
import com.aceplus.domain.entity.sale.saleexchange.SaleExchange
import com.aceplus.domain.entity.sale.salereturn.SaleReturn
import com.aceplus.domain.entity.sale.salereturn.SaleReturnDetail
import com.aceplus.domain.entity.sale.saletarget.SaleTargetCustomer
import com.aceplus.domain.entity.sale.saletarget.SaleTargetSaleMan
import com.aceplus.domain.entity.sale.salevisit.SaleVisitRecordUpload
import com.aceplus.domain.model.routeSchedule_v2.RouteScheduleItem_v2
import com.aceplus.domain.model.routeSchedule_v2.RouteSchedule_v2
import com.aceplus.domain.vo.report.*
import io.reactivex.Observable
import java.sql.Date
import java.util.*
import kotlin.collections.ArrayList

interface ReportRepo {
    //deliver report
    fun inCompleteDeliverReport(): Observable<List<IncompleteDeliverReport>>
    fun getDeliveryItemUploadList(invoiceNo:List<String>):Observable<List<DeliveryItemUpload>>
    fun getTotalAmountForDeliveryReport(list: List<String>): Observable<List<TotalAmountForDeliveryReport>>
    fun deliverDetailReport(invoiceId: String): Observable<List<DeliverDetailReport>>

    //preOrder report
    fun preOrderReport(): Observable<List<PreOrderReport>>
    fun preOrderQtyReport(invoiceIdList: List<String>): Observable<List<PreOrderProduct>>

    fun preOrderDetailReport(invoiceId: String): Observable<List<PreOrderDetailReport>>

    //product balance report
    fun productBalanceReport(): Observable<List<com.aceplus.domain.entity.product.Product>>

    //sale invoice report and sale exchange tab2
    fun saleInvoiceReport(): Observable<List<SaleInvoiceReport>>
    fun saleExchangeTab2Report(): Observable<List<SaleInvoiceReport>>

    //sale history report
    fun saleHistoryReport(): Observable<List<SaleInvoiceReport>>
    fun saleHistoryReportForDate(fromDate: String,toDate:String ): Observable<List<SaleInvoiceReport>>

    fun saleInvoiceDetailReport(invoiceId: String): Observable<List<SaleInvoiceDetailReport>>
    fun saleInvoiceDetlailPrint(invoiceId:String):Observable<Invoice>

    //spinner data
    fun getAllCustomerData(): Observable<List<Customer>>
    fun getAllGroupData(): Observable<List<ProductGroup>>
    fun getAllCategoryData(): Observable<List<ProductCategory>>

    //sale cancel report
    fun salesCancelReport(): Observable<List<SalesCancelReport>>
    fun saleCancelReportForDate(fromDate: String, toDate: String):  Observable<List<SalesCancelReport>>

    fun salesCancelDetailReport(invoiceId: String): Observable<List<SaleCancelInvoiceDetailReport>>

    //sale order history report
    fun salesOrderHistoryReport(): Observable<List<SalesOrderHistoryFullDataReport>>
    fun saleHistoryDateFilterList(fromDate: String, toDate: String): Observable<List<SalesOrderHistoryFullDataReport>>

    //sale return report and exchange tab1
    fun salesReturnQtyReport(): Observable<List<SalesReturnQtyReport>>
    fun salesExchangeTab1Report(): Observable<List<SalesReturnQtyReport>>
    fun salesReturnReport(idList:List<String>):Observable<List<SaleReturnDetail>>
    fun salesReturnDetailReport(invoiceId: String): Observable<List<SalesReturnDetailReport>>

    //sale visit history report
    fun invoiceCheckFromAndToCustomer(fromCusNo: Int, toCusNo: Int, newDate: String):Observable<List<Invoice>>
    fun preOrderCheckFromAndToCustomer(fromCusNo: Int, toCusNo: Int, newDate: String):Observable<List<PreOrder>>
    fun deliveryUploadCheckFromAndToCustomer(fromCusNo: Int, toCusNo: Int, newDate: String):Observable<List<DeliveryUpload>>
    fun saleReturnCheckFromAndToCustomer(fromCusNo: Int, toCusNo: Int, newDate: String):Observable<List<SaleReturn>>
    fun didCustomerFeedbackCheckFromAndToCustomer(fromCusNo: Int, toCusNo: Int, newDate: String):Observable<List<DidCustomerFeedback>>


    //unSell reason report
    fun unSellReasonReport(): Observable<List<UnsellReasonReport>>

    //sale target sale man
    fun saleTargetSaleManReport(): Observable<List<SaleTargetSaleMan>>
    fun saleTargetAmountForSaleMan(groupId:Int,categoryId:Int):Observable<List<SaleTargetVO>>


    fun getAllInvoiceData(): Observable<List<Invoice>>

    //sale target and actual sale for customer
    fun saleTargetCustomerReport(): Observable<List<SaleTargetCustomer>>
    fun saleTargetAmountForCustomer(iCustomerId:String,groupId:Int,categoryId:Int):Observable<List<SaleTargetVO>>
    fun saleTargetCustomerIdList(customerId:Int): Observable<List<SaleTargetCustomer>>

    //target and actual sale for product
    fun getNameListForSaleTargetProduct(): Observable<List<TargetAndActualSaleForProduct>>


    //......end of day report..........//
    //New..............
    fun getRouteNameForEndOfDayReport(saleManId:String):Observable<List<RouteScheduleV2>>
    fun getSaleManRouteNameList(routeId:Int): Observable<List<Route>>
    fun getTotalSaleList(now:String):Observable<List<Delivery>>
    fun getInvoiceListForEndOfDay(now: String): Observable<List<Invoice>>
    fun getPreOrderListForEndOfDay(now: String): Observable<List<PreOrder>>
    fun getSaleReturnListForEndOfDay(now: String): Observable<List<SaleReturn>>
    fun getSaleExchangeListForEndOfDay(now: String): Observable<List<Invoice>>
    fun getCashReceiveListForEndOfDay(now: String):Observable<List<Credit>>
    fun getSaleCountForEndOfDay(now: String): Observable<List<Invoice>>
    fun getDataForNewCustomerList():Observable<List<Customer>>
    fun getPlanCustomerList(): Observable<List<RouteScheduleItemV2>>
    fun getDataNotVisitedCountList():Observable<List<SaleVisitRecordUpload>>
    fun getStartTime(now:String):Observable<List<Invoice>>
    //Up To...............
}
package com.aceplus.domain.repo.report

import com.aceplus.domain.entity.CompanyInformation
import com.aceplus.domain.entity.GroupCode
import com.aceplus.domain.entity.cash.CashReceive
import com.aceplus.domain.entity.credit.Credit
import com.aceplus.domain.entity.customer.Customer
import com.aceplus.domain.entity.customer.CustomerBalance
import com.aceplus.domain.entity.invoice.Invoice
import com.aceplus.domain.entity.invoice.InvoicePresent
import com.aceplus.domain.entity.preorder.PreOrder
import com.aceplus.domain.entity.preorder.PreOrderPresent
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
import com.aceplus.domain.vo.report.*
import io.reactivex.Observable

interface ReportRepo {
    //deliver report
    fun deliverReport(): Observable<List<DeliverReport>>

    fun deliverDetailReport(invoiceId: String): Observable<List<DeliverDetailReport>>

    //preOrder report
    fun preOrderReport(): Observable<List<PreOrderReport>>

    fun preOrderDetailReport(invoiceId: String): Observable<List<PreOrderDetailReport>>

    //product balance report
    fun productBalanceReport(): Observable<List<com.aceplus.domain.entity.product.Product>>

    //sale invoice report
    fun saleInvoiceReport(): Observable<List<SaleInvoiceReport>>

    fun saleInvoiceDetailReport(invoiceId: String): Observable<List<SaleInvoiceDetailReport>>
    //spinner data
    fun getAllCustomerData(): Observable<List<Customer>>

    fun getAllGroupData(): Observable<List<ProductGroup>>
    fun getAllCategoryData(): Observable<List<ProductCategory>>

    //sale cancel report
    fun salesCancelReport(): Observable<List<SalesCancelReport>>

    fun salesCancelDetailReport(invoiceId: String): Observable<List<SaleCancelInvoiceDetailReport>>

    //sale order history report
    fun salesOrderHistoryReport(): Observable<List<SalesOrderHistoryReport>>

    //sale return report
    fun salesReturnReport(): Observable<List<SalesReturnReport>>

    fun salesReturnDetailReport(invoiceId: String): Observable<List<SalesReturnDetailReport>>

    //sale visit history report
    fun salesVisitHistoryReport(): Observable<List<SalesVisitHistoryReport>>

    //unSell reason report
    fun unSellReasonReport(): Observable<List<UnsellReasonReport>>

    //sale target sale man
    fun saleTargetSaleManReport(): Observable<List<SaleTargetSaleMan>>

    fun getAllInvoiceData(): Observable<List<Invoice>>

    //sale target and customer
    fun saleTargetCustomerReport(): Observable<List<SaleTargetCustomer>>

    //target and actual sale for product
    fun saleTargetProductReport(stockId: Int): Observable<List<com.aceplus.domain.entity.product.Product>>

    fun getGroupCodeListForSaleTargetProduct(groupNo: Int): Observable<List<GroupCode>>
    fun getProductCategoryListForSaleTargetProduct(categoryId: String): Observable<List<ProductCategory>>

    //end of day report
    fun getSaleManNameList(): Observable<List<SaleMan>>

    fun getSaleManRouteNameList(): Observable<List<Route>>
    fun getStartTimeAndEndTimeList(): Observable<List<CompanyInformation>>
    fun getTotalSaleOrderList(): Observable<List<PreOrder>>
    fun getTotalSaleExchangeList(): Observable<List<SaleExchange>>
    fun getTotalSaleReturnList(): Observable<List<SaleReturn>>
    fun getTotalCashReceiptList(): Observable<List<Credit>>
    fun getPlanCustomerList(): Observable<List<RouteScheduleItemV2>>
    fun getDataForNewCustomerList():Observable<List<Customer>>
    fun getDataNotVisitedCountList():Observable<List<SaleVisitRecordUpload>>


}
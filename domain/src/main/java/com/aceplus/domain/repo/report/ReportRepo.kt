package com.aceplus.domain.repo.report

import com.aceplus.domain.entity.customer.Customer
import com.aceplus.domain.entity.product.ProductCategory
import com.aceplus.domain.entity.product.ProductGroup
import com.aceplus.domain.vo.report.*
import io.reactivex.Observable

interface ReportRepo {
    //deliver report
    fun deliverReport(): Observable<List<DeliverReport>>
    fun deliverDetailReport(invoiceId:String): Observable<List<DeliverDetailReport>>

    //preOrder report
    fun preOrderReport():Observable<List<PreOrderReport>>
    fun preOrderDetailReport(invoiceId:String):Observable<List<PreOrderDetailReport>>

    //product balance report
    fun productBalanceReport():Observable<List<com.aceplus.domain.entity.product.Product>>

    //sale invoice report
    fun saleInvoiceReport(): Observable<List<SaleInvoiceReport>>
    fun saleInvoiceDetailReport(invoiceId:String): Observable<List<SaleInvoiceDetailReport>>
    //spinner data
    fun getAllCustomerData(): Observable<List<Customer>>
    fun getAllGroupData():Observable<List<ProductGroup>>
    fun getAllCategoryData():Observable<List<ProductCategory>>

    //sale cancel report
    fun salesCancelReport(): Observable<List<SalesCancelReport>>
    fun salesCancelDetailReport(invoiceId: String): Observable<List<SaleInvoiceDetailReport>>

    //sale order history report
    fun salesOrderHistoryReport():Observable<List<SalesOrderHistoryReport>>

    //sale return report
    fun salesReturnReport(): Observable<List<SalesReturnReport>>
    fun salesReturnDetailReport(invoiceId:String): Observable<List<SalesReturnDetailReport>>

    //sale visit history report
    fun salesVisitHistoryReport():Observable<List<SalesVisitHistoryReport>>

    //unSell reason report
    fun unSellReasonReport(): Observable<List<UnsellReasonReport>>
}
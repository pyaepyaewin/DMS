package com.aceplus.domain.repo.report

import com.aceplus.domain.entity.customer.Customer
import com.aceplus.domain.model.report.SaleInvoiceDetailReport
import com.aceplus.domain.model.report.SaleInvoiceReport
import com.aceplus.domain.model.sale.Saleinvoicedetail
import io.reactivex.Observable

interface SaleInvoiceReportRepo {
    fun saleInvoiceReport(): Observable<List<SaleInvoiceReport>>
    fun saleInvoiceDetailReport(invoiceId:String): Observable<List<SaleInvoiceDetailReport>>
    fun getAllCustomerData(): Observable<List<Customer>>
}
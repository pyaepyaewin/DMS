package com.aceplus.data.repoimpl.report
import com.aceplus.data.database.MyDatabase
import com.aceplus.domain.entity.customer.Customer
import com.aceplus.domain.model.report.SaleInvoiceDetailReport
import com.aceplus.domain.model.report.SaleInvoiceReport
import com.aceplus.domain.repo.report.SaleInvoiceReportRepo
import io.reactivex.Observable

class SaleInvoiceReportRepoImpl(private val db:MyDatabase): SaleInvoiceReportRepo {

    override fun saleInvoiceReport(): Observable<List<SaleInvoiceReport>> {
        return Observable.just(db.invoiceDao().getSaleInvoiceReport())
    }

    override fun saleInvoiceDetailReport(invoiceId:String): Observable<List<SaleInvoiceDetailReport>> {
        return Observable.just(db.invoiceProductDao().getSaleInvoiceDetailReport(invoiceId))

    }

    override fun getAllCustomerData(): Observable<List<Customer>> {
        return Observable.just(db.customerDao().allData)
    }
}
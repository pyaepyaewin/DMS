package com.aceplus.data.repoimpl.report

import com.aceplus.data.database.MyDatabase
import com.aceplus.domain.entity.customer.Customer
import com.aceplus.domain.entity.invoice.Invoice
import com.aceplus.domain.entity.product.Product
import com.aceplus.domain.entity.product.ProductCategory
import com.aceplus.domain.entity.product.ProductGroup
import com.aceplus.domain.vo.report.*
import com.aceplus.domain.repo.report.ReportRepo
import io.reactivex.Observable

class ReportRepoImpl(private val db: MyDatabase) : ReportRepo {
//    //testing invoice
//    override fun getAllInvoiceData(): Observable<List<Invoice>> {
//        return Observable.just(db.invoiceDao().allData)
//    }

    //deliver report
    override fun deliverReport(): Observable<List<DeliverReport>> {
        return Observable.just(db.deliveryDao().getDeliverReport())
    }

    override fun deliverDetailReport(invoiceId: String): Observable<List<DeliverDetailReport>> {
        return Observable.just(db.deliveryDao().getDeliverDetailReport(invoiceId))
    }

    //preOrder report
    override fun preOrderReport(): Observable<List<PreOrderReport>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun preOrderDetailReport(invoiceId: String): Observable<List<PreOrderDetailReport>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    //product balance report
    override fun productBalanceReport(): Observable<List<Product>> {
        return Observable.just(db.productDao().allData)
    }

    //sale invoice report
    override fun saleInvoiceReport(): Observable<List<SaleInvoiceReport>> {
        return Observable.just(db.invoiceDao().getSaleInvoiceReport())
    }

    override fun saleInvoiceDetailReport(invoiceId: String): Observable<List<SaleInvoiceDetailReport>> {
        return Observable.just(db.invoiceProductDao().getSaleInvoiceDetailReport(invoiceId))
    }

    //spinner data
    override fun getAllCustomerData(): Observable<List<Customer>> {
        return Observable.just(db.customerDao().allData)
    }
    override fun getAllGroupData(): Observable<List<ProductGroup>> {
        return Observable.just(db.productGroupDao().allData)
    }

    override fun getAllCategoryData(): Observable<List<ProductCategory>> {
        return Observable.just(db.productCategoryDao().allData)
    }

    //sale cancel report
    override fun salesCancelReport(): Observable<List<SalesCancelReport>> {
        return Observable.just(db.invoiceCancelDao().getSalesCancelReport())
    }

    override fun salesCancelDetailReport(invoiceId: String): Observable<List<SaleInvoiceDetailReport>> {
        return Observable.just(db.invoiceCancelProductDao().getSaleCancelDetailReport(invoiceId))
    }

    //sale order history report
    override fun salesOrderHistoryReport(): Observable<List<SalesOrderHistoryReport>> {
        return Observable.just(db.preOrderDao().getSalesOrderHistoryReport())
    }

    //sale return report
    override fun salesReturnReport(): Observable<List<SalesReturnReport>> {
        return Observable.just(db.saleReturnDao().getSalesReturnReport())
    }

    override fun salesReturnDetailReport(invoiceId: String): Observable<List<SalesReturnDetailReport>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    //sale visit history report
    override fun salesVisitHistoryReport(): Observable<List<SalesVisitHistoryReport>> {
        return Observable.just(db.customerVisitRecordReportDao().getSalesVisitHistoryReport())
    }

    //unSell reason report
    override fun unSellReasonReport(): Observable<List<UnsellReasonReport>> {
        return Observable.just(db.customerFeedbackDao().getUnSellReasonReport())
    }

}
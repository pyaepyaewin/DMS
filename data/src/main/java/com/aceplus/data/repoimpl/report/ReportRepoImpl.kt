package com.aceplus.data.repoimpl.report

import com.aceplus.data.database.MyDatabase
import com.aceplus.domain.entity.CompanyInformation
import com.aceplus.domain.entity.credit.Credit
import com.aceplus.domain.entity.customer.Customer
import com.aceplus.domain.entity.invoice.Invoice
import com.aceplus.domain.entity.preorder.PreOrder
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
import io.reactivex.Observable

class ReportRepoImpl(private val db: MyDatabase) : ReportRepo {

    //deliver report
    override fun deliverReport(): Observable<List<DeliverReport>> {
        return Observable.just(db.deliveryDao().getDeliverReport())
    }

    override fun deliverDetailReport(invoiceId: String): Observable<List<DeliverDetailReport>> {
        return Observable.just(db.deliveryDao().getDeliverDetailReport(invoiceId))
    }

    //preOrder report
    override fun preOrderReport(): Observable<List<PreOrderReport>> {
        return Observable.just(db.preOrderDao().getPreOrderReport())
    }

    override fun preOrderDetailReport(invoiceId: String): Observable<List<PreOrderDetailReport>> {
        return Observable.just(db.preOrderProductDao().getPreOrderDetailReport(invoiceId))
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

    override fun salesCancelDetailReport(invoiceId: String): Observable<List<SaleCancelInvoiceDetailReport>> {
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

    //sale target and sale man report
    override fun saleTargetSaleManReport(): Observable<List<SaleTargetSaleMan>> {
        return Observable.just(db.saleTargetSaleManDao().allData)
    }

    override fun getAllInvoiceData(): Observable<List<Invoice>> {
        return Observable.just(db.invoiceDao().allData)
    }

    //sale target and actual sale for customer
    override fun saleTargetCustomerReport(): Observable<List<SaleTargetCustomer>> {
        return Observable.just(db.saleTargetCustomerDao().allData)
    }

    override fun saleTargetCustomerIdList(customerId: Int): Observable<List<SaleTargetCustomer>> {
        return Observable.just(db.saleTargetCustomerDao().dataById(customerId))
    }

    override fun saleTargetAmountForCustomer(
        customerId: Int,
        groupId: Int,
        categoryId: Int
    ): Observable<List<SaleTargetVO>> {
        return Observable.just(
            db.saleTargetCustomerDao().actualSaleData(
                customerId,
                groupId,
                categoryId
            )
        )
    }

    //sale target and actual sale for product

    override fun getNameListForSaleTargetProduct(): Observable<List<TargetAndActualSaleForProduct>> {
        return Observable.just(db.saleTargetSaleManDao().allDataWithName)
    }

    //end of day report
    override fun getSaleManNameList(): Observable<List<SaleMan>> {
        return Observable.just(db.saleManDao().allData)
    }

    override fun getSaleManRouteNameList(): Observable<List<Route>> {
        return Observable.just(db.routeDao().allData)
    }

    override fun getStartTimeAndEndTimeList(): Observable<List<CompanyInformation>> {
        return Observable.just(db.companyInformationDao().allData)
    }

    override fun getTotalSaleOrderList(): Observable<List<PreOrder>> {
        return Observable.just(db.preOrderDao().allData)
    }

    override fun getTotalSaleExchangeList(): Observable<List<SaleExchange>> {
        return Observable.just(db.saleExchangeDao().allData)
    }

    override fun getTotalSaleReturnList(): Observable<List<SaleReturn>> {
        return Observable.just(db.saleReturnDao().allData)
    }

    override fun getTotalCashReceiptList(): Observable<List<Credit>> {
        return Observable.just(db.creditDao().allData)
    }

    override fun getPlanCustomerList(): Observable<List<RouteScheduleItemV2>> {
        return Observable.just(db.routeScheduleItemV2Dao().allData)
    }

    override fun getDataForNewCustomerList(): Observable<List<Customer>> {
        return Observable.just(db.customerDao().customerList)
    }

    override fun getDataNotVisitedCountList(): Observable<List<SaleVisitRecordUpload>> {
        return Observable.just(db.saleVisitRecordUploadDao().saleVisitUploadList)
    }


}
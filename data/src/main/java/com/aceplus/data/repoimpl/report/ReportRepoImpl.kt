package com.aceplus.data.repoimpl.report

import com.aceplus.data.database.MyDatabase
import com.aceplus.domain.entity.CompanyInformation
import com.aceplus.domain.entity.credit.Credit
import com.aceplus.domain.entity.customer.Customer
import com.aceplus.domain.entity.delivery.DeliveryItemUpload
import com.aceplus.domain.entity.invoice.Invoice
import com.aceplus.domain.entity.preorder.PreOrder
import com.aceplus.domain.entity.preorder.PreOrderProduct
import com.aceplus.domain.entity.product.Product
import com.aceplus.domain.entity.product.ProductCategory
import com.aceplus.domain.entity.product.ProductGroup
import com.aceplus.domain.entity.route.Route
import com.aceplus.domain.entity.route.RouteScheduleItemV2
import com.aceplus.domain.entity.sale.SaleMan
import com.aceplus.domain.entity.sale.saleexchange.SaleExchange
import com.aceplus.domain.entity.sale.salereturn.SaleReturn
import com.aceplus.domain.entity.sale.salereturn.SaleReturnDetail
import com.aceplus.domain.entity.sale.saletarget.SaleTargetCustomer
import com.aceplus.domain.entity.sale.saletarget.SaleTargetSaleMan
import com.aceplus.domain.entity.sale.salevisit.SaleVisitRecordUpload
import com.aceplus.domain.repo.report.ReportRepo
import com.aceplus.domain.vo.report.*
import io.reactivex.Observable

class ReportRepoImpl(private val db: MyDatabase) : ReportRepo {
    //deliver report
    override fun inCompleteDeliverReport(): Observable<List<IncompleteDeliverReport>> {
        return Observable.just(db.deliveryUploadDao().getIncompleteDeliverReport())
    }

    override fun getDeliveryItemUploadList(invoiceNo: List<String>): Observable<List<DeliveryItemUpload>> {
        return Observable.just(db.deliveryItemUpload().getDeliveryItemList(invoiceNo))
    }

    override fun getTotalAmountForDeliveryReport(list: List<String>): Observable<List<TotalAmountForDeliveryReport>> {
        return Observable.just(db.deliveryItemUpload().getQtyAndAmount(list))
    }

    override fun deliverDetailReport(invoiceId: String): Observable<List<DeliverDetailReport>> {
        return Observable.just(db.deliveryItemUpload().getDeliverDetailReport(invoiceId))
    }

    //preOrder report
    override fun preOrderReport(): Observable<List<PreOrderReport>> {
        return Observable.just(db.preOrderDao().getPreOrderReport())
    }
    override fun preOrderQtyReport(invoiceIdList: List<String>): Observable<List<PreOrderProduct>> {
        return Observable.just(db.preOrderProductDao().allQtyDataById(invoiceIdList))
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
        return Observable.just(db.invoicePresentDao().getSaleInvoiceReport())
    }

    override fun saleExchangeTab2Report(): Observable<List<SaleInvoiceReport>> {
        return Observable.just(db.invoicePresentDao().getSaleExchangeTab2Report())
    }

    //sale history report
    override fun saleHistoryReport(): Observable<List<SaleInvoiceReport>> {
        return Observable.just(db.invoiceDao().getSaleHistoryReport())
    }

    override fun saleHistoryReportForDate(
        fromDate: String,
        toDate: String
    ): Observable<List<SaleInvoiceReport>> {
        return Observable.just(db.invoiceDao().getSaleHistoryReportForDate(fromDate, toDate))
    }

    override fun saleInvoiceDetailReport(invoiceId: String): Observable<List<SaleInvoiceDetailReport>> {
        return Observable.just(db.invoiceProductDao().getSaleInvoiceDetailReport(invoiceId))
    }

    override fun saleInvoiceDetlailPrint(invoiceId: String): Observable<Invoice> {
        return Observable.just(db.invoiceDao().getSaleHistoryReportForPrint(invoiceId))
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
//    override fun saleCancelReportForDate(fromDate: String, toDate: String): Observable<List<SalesCancelReport>> {
//       return Observable.just(db.invoiceCancelDao().getSaleCancelReportForDate(fromDate,toDate))
//    }

    //sale order history report
    override fun salesOrderHistoryReport(): Observable<List<SalesOrderHistoryFullDataReport>> {
        return Observable.just(db.preOrderDao().getSalesOrderHistoryReport())
    }

    //sale return report
    override fun salesReturnQtyReport(): Observable<List<SalesReturnQtyReport>> {
        return Observable.just(db.saleReturnDao().getSalesReturnQtyReport())
    }

    override fun salesExchangeTab1Report(): Observable<List<SalesReturnQtyReport>> {
       return Observable.just(db.saleReturnDao().getSalesExchangeTab1())
    }


    override fun salesReturnReport(idList:List<String>): Observable<List<SaleReturnDetail>> {
        return Observable.just(db.saleReturnDetailDao().getSalesReturnReport(idList))
    }

    override fun salesReturnDetailReport(invoiceId: String): Observable<List<SalesReturnDetailReport>> {
       return Observable.just(db.saleReturnDetailDao().getSalesReturnDetailList(invoiceId))
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

    override fun saleTargetAmountForSaleMan(
        groupId: Int,
        categoryId: Int
    ): Observable<List<SaleTargetVO>> {
        return Observable.just(
            db.saleTargetSaleManDao().actualSaleDataForSaleMan(
                groupId,
                categoryId
            )
        )
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
        iCustomerId: String,
        groupId: Int,
        categoryId: Int
    ): Observable<List<SaleTargetVO>> {
        return Observable.just(
            db.saleTargetCustomerDao().actualSaleData(
                iCustomerId,
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
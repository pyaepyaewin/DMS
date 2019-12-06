package com.aceplus.data.repoimpl.report

import com.aceplus.data.database.MyDatabase
import com.aceplus.domain.entity.CompanyInformation
import com.aceplus.domain.entity.GroupCode
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
import com.aceplus.domain.entity.product.Product
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

    override fun saleHistoryReportForDate(fromDate: String, toDate: String): Observable<List<SaleInvoiceReport>> {
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

    override fun getAllGroupData(): Observable<List<GroupCode>> {
        return Observable.just(db.groupCodeDao().allData)
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
    override fun saleCancelReportForDate(fromDate: String, toDate: String): Observable<List<SalesCancelReport>> {
       return Observable.just(db.invoiceCancelDao().getSaleCancelReportForDate(fromDate,toDate))
    }

    //sale order history report
    override fun salesOrderHistoryReport(): Observable<List<SalesOrderHistoryFullDataReport>> {
        return Observable.just(db.preOrderDao().getSalesOrderHistoryReport())
    }
    override fun saleHistoryDateFilterList(fromDate: String, toDate: String): Observable<List<SalesOrderHistoryFullDataReport>> {
        return Observable.just(db.preOrderDao().getSalesOrderHistoryDateFilterList(fromDate,toDate))
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
    override fun invoiceCheckFromAndToCustomer(fromCusNo: Int, toCusNo: Int, newDate: String): Observable<List<Invoice>> {
        return Observable.just(db.invoiceDao().getSaleVisitFromAndToCustomer(fromCusNo,toCusNo,newDate))
    }
    override fun preOrderCheckFromAndToCustomer(fromCusNo: Int, toCusNo: Int, newDate: String): Observable<List<PreOrder>> {
        return Observable.just(db.preOrderDao().getSaleVisitForPreOrder(fromCusNo,toCusNo,newDate))
    }

    override fun deliveryUploadCheckFromAndToCustomer(fromCusNo: Int, toCusNo: Int, newDate: String): Observable<List<DeliveryUpload>> {
        return Observable.just(db.deliveryUploadDao().getSaleVisitForDeliveryUpload(fromCusNo,toCusNo,newDate))
    }

    override fun saleReturnCheckFromAndToCustomer(fromCusNo: Int, toCusNo: Int, newDate: String): Observable<List<SaleReturn>> {
        return Observable.just(db.saleReturnDao().getSaleVisitForSaleReturn(fromCusNo,toCusNo,newDate))
    }

    override fun didCustomerFeedbackCheckFromAndToCustomer(fromCusNo: Int, toCusNo: Int, newDate: String): Observable<List<DidCustomerFeedback>> {
        return Observable.just(db.didCustomerFeedbackDao().getSaleVisitForDidCustomerFeedback(fromCusNo,toCusNo,newDate))
    }

    //unSell reason report
    override fun unSellReasonReport(): Observable<List<UnsellReasonReport>> {
        return Observable.just(db.customerFeedbackDao().getUnSellReasonReport())
    }

    //sale target and sale man report
    override fun saleTargetSaleManReport(): Observable<List<SaleTargetSaleMan>> {
        return Observable.just(db.saleTargetSaleManDao().allData)
    }

    override fun getCategoryListFromInvoiceProduct(categoryId:String) : Observable<List<TargetAndSaleForSaleMan>>{
        return Observable.just(db.invoiceProductDao().getCategoryListFromInvoiceProduct(categoryId))
    }

    override fun getGroupListFromInvoiceProduct(groupId:String) : Observable<List<TargetAndSaleForSaleMan>>{
        return Observable.just(db.invoiceProductDao().getGroupListFromInvoiceProduct(groupId))
    }

    override fun getAllInvoiceData(): Observable<List<Invoice>> {
        return Observable.just(db.invoiceDao().allData)
    }

    override fun getTargetSaleDB(customerId:Int) : Observable<List<SaleTargetSaleMan>>{
        return Observable.just(db.saleTargetSaleManDao().getTargetSaleDB(customerId))
    }

    //sale target and actual sale for customer
    override fun getTargetSaleDBForCustomer(customerIdFromSpinner:Int) : Observable<List<SaleTargetCustomer>>{
        return Observable.just(db.saleTargetCustomerDao().getTargetSaleDBForCustomer(customerIdFromSpinner))
    }

    override fun getCustomerSaleTargetAndSaleIdList(customerId:String) :Observable<List<TargetAndSaleForSaleMan>>{
        return Observable.just(db.invoiceProductDao().getCustomerSaleTargetAndSaleIdList(customerId))
    }

    //sale target and actual sale for product
    override  fun getActualSale1ForSaleTargetProduct():Observable<List<Product>>{
        return Observable.just(db.productDao().getActualSale1ForSaleTargetProduct)
    }
    override  fun getActualSale2ForSaleTargetProduct():Observable<List<Product>>{
        return Observable.just(db.productDao().getActualSale2ForSaleTargetProduct)
    }

    override  fun getActualSale3ForSaleTargetProduct():Observable<List<Product>>{
        return Observable.just(db.productDao().getActualSale3ForSaleTargetProduct)
    }

    override fun getInvoiceProductList(id: String):Observable<List<InvoiceProduct>> {
       return Observable.just(db.invoiceProductDao().getInvoiceProductList(id))
    }

    override fun getGroupIdFromProduct(productId:Int) : Observable<List<Product>>{
      return Observable.fromArray(db.productDao().getGroupIdFromProduct(productId))
    }

    override fun getProductNameFromProduct(stockId:Int) : Observable<Product>{
        return Observable.just(db.productDao().getProductNameFromProduct(stockId))
    }

    override  fun getGroupCodeNameFromGroupCode(groupId:Int) : Observable<GroupCode>{
        return Observable.just(db.groupCodeDao().getGroupCodeNameFromGroupCode(groupId))
    }

    override fun getCategoryNameFromProductCategory(categoryId:Int) : Observable<ProductCategory>{
        return Observable.just(db.productCategoryDao().getCategoryNameFromProductCategory(categoryId))
    }

    //end of day report
    //.......New............//
    override fun getRouteNameForEndOfDayReport(saleManId: String): Observable<List<RouteScheduleV2>> {
        return Observable.just(db.routeScheduleV2Dao().getRouteName(saleManId))
    }

    override fun getSaleManRouteNameList(routeId:Int): Observable<List<Route>> {
        return Observable.just(db.routeDao().getRouteList(routeId))
    }

    override fun getTotalSaleList(now: String): Observable<List<Delivery>> {
        return Observable.just(db.deliveryDao().getTotalSaleListForEndOdDay(now))
    }

    override fun getInvoiceListForEndOfDay(now: String): Observable<List<Invoice>> {
        return Observable.just(db.invoiceDao().getInvoiceListForEndOfDay(now))
    }

    override fun getPreOrderListForEndOfDay(now: String): Observable<List<PreOrder>> {
        return Observable.just(db.preOrderDao().getPreOrderListForEndOfDay(now))
    }

    override fun getSaleReturnListForEndOfDay(now: String): Observable<List<SaleReturn>> {
        return Observable.just(db.saleReturnDao().getSaleReturnListForEndOfDay(now))
    }

    override fun getSaleExchangeListForEndOfDay(now: String): Observable<List<Invoice>> {
        return Observable.just(db.invoiceDao().getSaleExchangeListForEndOfDay(now))
    }

    override fun getCashReceiveListForEndOfDay(now: String):Observable<List<Credit>>{
        return Observable.just(db.creditDao().getCashReceiveListForEndOfDay(now))
    }

    override fun getSaleCountForEndOfDay(now: String): Observable<List<Invoice>>{
        return Observable.just(db.invoiceDao().getSaleCountForEndOfDay(now))
    }

    override fun getDataForNewCustomerList(): Observable<List<Customer>> {
        return Observable.just(db.customerDao().customerList)
    }

    override fun getPlanCustomerList(): Observable<List<RouteScheduleItemV2>> {
        return Observable.just(db.routeScheduleItemV2Dao().allData)
    }

    override fun getDataNotVisitedCountList(): Observable<List<SaleVisitRecordUpload>> {
        return Observable.just(db.saleVisitRecordUploadDao().saleVisitUploadList)
    }

    override fun getStartTime(now: String): Observable<List<Invoice>> {
        return Observable.just(db.invoiceDao().getStartTime(now))
    }

}
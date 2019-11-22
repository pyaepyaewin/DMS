package com.aceplus.data.repoimpl.deliveryrepoimpl

import com.aceplus.data.database.MyDatabase
import com.aceplus.domain.entity.CompanyInformation
import com.aceplus.domain.entity.delivery.*
import com.aceplus.domain.entity.invoice.Invoice
import com.aceplus.domain.entity.invoice.InvoiceProduct
import com.aceplus.domain.entity.route.RouteScheduleV2
import com.aceplus.domain.entity.sale.SaleMan
import com.aceplus.domain.model.Product
import com.aceplus.domain.model.customer.Customer
import com.aceplus.domain.model.delivery.Deliver
import com.aceplus.domain.model.delivery.DeliverItem
import com.aceplus.domain.repo.deliveryrepo.DeliveryRepo
import com.aceplus.domain.vo.customer.DeliveryVO
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class DeliveryRepoImpl(private val db: MyDatabase) : DeliveryRepo {
    override fun getDeliveryName(invoiceId: String): Observable<Invoice> {
        return Observable.just(db.invoiceDao().getSaleHistoryReportForPrint(invoiceId = invoiceId))
    }

    override fun getSaleManName(saleManId: Int): Observable<List<SaleMan>> {
        return Observable.just(db.saleManDao().allData)
    }

    override fun saveDeliveryUpload(cvDeliveryUpload: DeliveryUpload) {
        Observable.fromCallable { db.deliveryUploadDao().insert(cvDeliveryUpload) }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe()
    }

    override fun getTaxTypeList(): Observable<List<CompanyInformation>> {
        return Observable.just(db.companyInformationDao().allData)
    }

    override fun saveInvoiceData(invoice: Invoice) {
        Observable.fromCallable { db.invoiceDao().insert(invoice) }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe()
    }

    override fun getRouteId(invoiceId: String):Observable<RouteScheduleV2> {
        return Observable.just(db.routeScheduleV2Dao().dataBySaleManId(invoiceId))
    }

    override fun updateCheckDeliveryItem(stockId: String) {
        db.deliveryItemDao().updateCheckDeliveryItem(stockId)
    }

    override fun updateRelatedDeliveryPresent(stockId: String) {
        db.deliveryPresentDao().updateDeliveryPresentQty(stockId)
    }

    override fun updateRelatedDeliveryItem(quantity: Int, stockId: String) {
       // db.deliveryItemDao().updateDeliveryItemQty(quantity,stockId)
    }

    override fun updateRelatedProduct(quantity: Int, id: Int) {
        db.productDao().updateProductDeliveryQty(quantity,id)
    }

    override fun saveDeliveryItemUpload(cvDeliveryUploadItem: DeliveryItemUpload) {
        Observable.fromCallable { db.deliveryItemUpload().insertDeliveryItemUpload(cvDeliveryUploadItem) }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe()
    }

    override fun saveDeliveryDataItem(cvInvoiceProduct: InvoiceProduct) {
        Observable.fromCallable { db.invoiceProductDao().insertDeliveryData(cvInvoiceProduct) }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe()
    }

    //Testing..............
    override fun allData(): Observable<List<Delivery>> {
        return Observable.just(db.deliveryDao().allData)
    }

    override fun deliveryDataList(): Observable<List<DeliveryVO>> {
        return Observable.just(db.deliveryDao().getDeliveryData())
    }

    override fun deliveryItemDataList(deliveryId:Int): Observable<List<DeliveryItem>> {
        return Observable.just(db.deliveryItemDao().deliveryItemData(deliveryId))
    }

    override fun deliveryPresentDataList(deliveryId:Int): Observable<List<DeliveryPresent>> {
        return Observable.just(db.deliveryPresentDao().getDeliveryPresentDataList(deliveryId))
    }

    override fun deliveryProductList(stockIdList: List<String>): Observable<List<com.aceplus.domain.entity.product.Product>> {
        return Observable.just(db.productDao().deliveryProductDataList(stockIdList))
    }

    override fun deliveryCustomerList(customerId: Int): Observable<com.aceplus.domain.entity.customer.Customer> {
        return Observable.just(db.customerDao().deliveryCustomerDataList(customerId))
    }



}
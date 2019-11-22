package com.aceplus.domain.repo.deliveryrepo

import com.aceplus.domain.entity.CompanyInformation
import com.aceplus.domain.entity.delivery.*
import com.aceplus.domain.entity.invoice.Invoice
import com.aceplus.domain.entity.invoice.InvoiceProduct
import com.aceplus.domain.entity.product.Product
import com.aceplus.domain.entity.route.RouteScheduleV2
import com.aceplus.domain.entity.sale.SaleMan
import com.aceplus.domain.vo.customer.DeliveryVO
import io.reactivex.Observable

interface DeliveryRepo {
    //Testing
    fun allData():Observable<List<Delivery>>
    fun deliveryDataList(): Observable<List<DeliveryVO>>
    fun deliveryItemDataList(deliveryId:Int):Observable<List<DeliveryItem>>
    fun deliveryPresentDataList(deliveryId:Int): Observable<List<DeliveryPresent>>
    fun deliveryProductList(stockIdList:List<String>):Observable<List<com.aceplus.domain.entity.product.Product>>
    fun deliveryCustomerList(customerId:Int):Observable<com.aceplus.domain.entity.customer.Customer>
    fun saveDeliveryDataItem(cvInvoiceProduct: InvoiceProduct)
    fun saveDeliveryItemUpload(cvDeliveryUploadItem: DeliveryItemUpload)
    fun updateRelatedProduct(quantity: Int, id: Int)
    fun updateRelatedDeliveryItem(quantity: Int, stockId: String)
    fun updateRelatedDeliveryPresent(stockId: String)
    fun updateCheckDeliveryItem(stockId: String)
    fun getRouteId(invoiceId: String):Observable<RouteScheduleV2>
    fun saveInvoiceData(invoice: Invoice)
    fun getTaxTypeList():Observable<List<CompanyInformation>>
    fun saveDeliveryUpload(cvDeliveryUpload: DeliveryUpload)
    fun getSaleManName(saleManId: Int): Observable<List<SaleMan>>
    fun getDeliveryName(invoiceId: String): Observable<Invoice>
}
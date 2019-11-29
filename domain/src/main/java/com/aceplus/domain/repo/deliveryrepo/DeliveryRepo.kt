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
import java.io.DataOutput

interface DeliveryRepo {
    //Testing
    fun allData():Observable<List<Delivery>>
    fun deliveryDataList(): Observable<List<DeliveryVO>>
    fun deliveryItemDataList(deliveryId:String):Observable<List<DeliveryItem>>
    fun deliveryPresentDataList(deliveryId:String): Observable<List<DeliveryPresent>>
    fun deliveryProductList(stockIdList:List<String>):Observable<List<Product>>
    fun deliveryCustomerList(customerId:Int):Observable<com.aceplus.domain.entity.customer.Customer>
    fun saveDeliveryDataItem(cvInvoiceProduct: InvoiceProduct)
    fun saveDeliveryItemUpload(cvDeliveryUploadItem: DeliveryItemUpload)
    fun updateRelatedProduct(quantity: Int, id: Int)
    fun updateRelatedDeliveryItem(quantity: Double, stockId: String)
    fun updateRelatedDeliveryPresent(stockId: String)
    fun updateCheckDeliveryItem(stockId: String)
    fun getRouteId(invoiceId: String):Observable<RouteScheduleV2>
    fun saveInvoiceData(invoice: Invoice)
    fun getTaxTypeList():Observable<List<CompanyInformation>>
    fun saveDeliveryUpload(cvDeliveryUpload: DeliveryUpload)
    fun getSaleManName(saleManId: Int): Observable<List<SaleMan>>
    fun getDeliveryName(invoiceId: String): Observable<Invoice>
    fun getDeliveryItemObject(productId: String) :Observable<DeliveryItem>
   }
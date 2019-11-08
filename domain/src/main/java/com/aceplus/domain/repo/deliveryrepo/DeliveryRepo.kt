package com.aceplus.domain.repo.deliveryrepo

import com.aceplus.domain.entity.delivery.Delivery
import com.aceplus.domain.entity.delivery.DeliveryItem
import com.aceplus.domain.entity.delivery.DeliveryPresent
import com.aceplus.domain.model.Product
import com.aceplus.domain.model.customer.Customer
import com.aceplus.domain.model.delivery.Deliver
import com.aceplus.domain.model.delivery.DeliverItem
import com.aceplus.domain.vo.customer.DeliveryVO
import io.reactivex.Observable

interface DeliveryRepo {
    fun deliveryDataList(): Observable<List<DeliveryVO>>
    fun deliveryItemDataList(deliveryId:Int):Observable<List<DeliveryItem>>
    fun deliveryPresentDataList(deliveryId:Int): Observable<List<DeliveryPresent>>
    fun deliveryProductList(stockId:String):Observable<List<com.aceplus.domain.entity.product.Product>>
    fun deliveryCustomerList(customerId:Int):Observable<com.aceplus.domain.entity.customer.Customer>
}
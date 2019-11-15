package com.aceplus.data.repoimpl.deliveryrepoimpl

import com.aceplus.data.database.MyDatabase
import com.aceplus.domain.entity.delivery.Delivery
import com.aceplus.domain.entity.delivery.DeliveryItem
import com.aceplus.domain.entity.delivery.DeliveryPresent
import com.aceplus.domain.model.Product
import com.aceplus.domain.model.customer.Customer
import com.aceplus.domain.model.delivery.Deliver
import com.aceplus.domain.model.delivery.DeliverItem
import com.aceplus.domain.repo.deliveryrepo.DeliveryRepo
import com.aceplus.domain.vo.customer.DeliveryVO
import io.reactivex.Observable

class DeliveryRepoImpl(private val db: MyDatabase) : DeliveryRepo {
    //Testing
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

    override fun deliveryProductList(stockId: String): Observable<List<com.aceplus.domain.entity.product.Product>> {
        return Observable.just(db.productDao().deliveryProductDataList(stockId))
    }

    override fun deliveryCustomerList(customerId: Int): Observable<com.aceplus.domain.entity.customer.Customer> {
        return Observable.just(db.customerDao().deliveryCustomerDataList(customerId))
    }



}
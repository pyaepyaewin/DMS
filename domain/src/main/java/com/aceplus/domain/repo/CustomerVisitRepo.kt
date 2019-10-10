package com.aceplus.domain.repo

import com.aceplus.domain.entity.classdiscount.ClassDiscountByPrice
import com.aceplus.domain.entity.classdiscount.ClassDiscountByPriceItem
import com.aceplus.domain.entity.customer.Customer
import com.aceplus.domain.entity.customer.CustomerFeedback
import com.aceplus.domain.entity.customer.DidCustomerFeedback
import com.aceplus.domain.entity.predefine.Township
import com.aceplus.domain.entity.product.Product
import com.aceplus.domain.entity.promotion.PromotionDate
import com.aceplus.domain.entity.promotion.PromotionPrice
import com.aceplus.domain.entity.sale.SaleMan
import com.aceplus.domain.model.roomdb.StringObject
import com.aceplus.shared.utils.GPSTracker
import io.reactivex.Observable
import io.reactivex.internal.operators.observable.ObservableLift
import java.util.*

interface CustomerVisitRepo {

    fun getLocationCode(): Int
    fun getSaleManData(): SaleMan
    fun getRouteScheduleIDV2(): Int
    fun getLastCountForInvoiceNumber(mode: String): Int

    fun getAllCustomerData(): Observable<List<Customer>>
    fun getAllDidFeedback(): Observable<List<String>>
    fun getAllDefaultFeedback(): Observable<List<CustomerFeedback>>

    fun getAllProductData(): Observable<List<Product>>
    fun getProductByID(productID: Int): Observable<List<Product>>

    fun saveDataForTempSaleManRoute(selectedCustomer: Customer, currentDate: String)
    fun saveCustomerFeedback(didCustomerFeedbackEntity: DidCustomerFeedback)
    fun saveSaleVisitRecord(selectedCustomer: Customer, gpsTracker: GPSTracker)

    fun updateDepartureTimeForSaleManRoute(saleManId: String, customerId: String, currentDate: String)

    fun getClassDiscountByPrice(currentDate: String): Observable<List<ClassDiscountByPrice>>
    fun getClassDiscountByPriceItem(classDiscountId: Int): Observable<List<ClassDiscountByPriceItem>>
    fun getCurrentDatePromotion(currentDate: String): Observable<List<PromotionDate>>

    fun getPromotionPriceByID(promotionPlanId: String, buy_qty: Int, stockID: String): Observable<List<PromotionPrice>>
    fun getAllPromoPrice(): Observable<List<PromotionPrice>> // Testing

}
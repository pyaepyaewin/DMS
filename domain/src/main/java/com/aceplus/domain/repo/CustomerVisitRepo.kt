package com.aceplus.domain.repo

import com.aceplus.domain.vo.SoldProductInfo
import com.aceplus.domain.entity.classdiscount.ClassDiscountByPrice
import com.aceplus.domain.entity.classdiscount.ClassDiscountByPriceItem
import com.aceplus.domain.entity.customer.Customer
import com.aceplus.domain.entity.customer.CustomerFeedback
import com.aceplus.domain.entity.customer.DidCustomerFeedback
import com.aceplus.domain.entity.invoice.InvoiceProduct
import com.aceplus.domain.entity.product.Product
import com.aceplus.domain.entity.promotion.PromotionDate
import com.aceplus.domain.entity.promotion.PromotionGift
import com.aceplus.domain.entity.promotion.PromotionPrice
import com.aceplus.domain.entity.sale.SaleMan
import com.aceplus.shared.utils.GPSTracker
import io.reactivex.Observable

interface CustomerVisitRepo {

    fun getLocationCode(): Int
    fun getSaleManData(): SaleMan
    fun getRouteScheduleIDV2(): Int
    fun getLastCountForInvoiceNumber(mode: String): Int

    fun getAllCustomerData(): Observable<List<Customer>>
    fun updateCustomerData(customer: Customer)
    fun getAllDidFeedback(): Observable<List<String>>
    fun getAllDefaultFeedback(): Observable<List<CustomerFeedback>>

    fun getAllProductData(): Observable<List<Product>>
    fun getProductByID(productID: Int): Observable<List<Product>>
    fun updateProductRemainingQty(soldProductInfo: SoldProductInfo)

    fun saveDataForTempSaleManRoute(selectedCustomer: Customer, currentDate: String)
    fun saveCustomerFeedback(didCustomerFeedbackEntity: DidCustomerFeedback)
    fun saveSaleVisitRecord(selectedCustomer: Customer, gpsTracker: GPSTracker)

    fun updateDepartureTimeForSaleManRoute(saleManId: String, customerId: String, currentDate: String)

    fun getClassDiscountByPrice(currentDate: String): Observable<List<ClassDiscountByPrice>>
    fun getClassDiscountByPriceOnClassID(currentClassId: String): Observable<List<ClassDiscountByPrice>>
    fun getClassDiscountByPriceItem(classDiscountId: Int): Observable<List<ClassDiscountByPriceItem>>
    fun getClassDiscountByPriceItemCountOnClassID(classID: String): Observable<Int>
    fun getCurrentDatePromotion(currentDate: String): Observable<List<PromotionDate>>

    fun getPromotionPriceByID(promotionPlanId: String, buy_qty: Int, stockID: String): Observable<List<PromotionPrice>>
    fun getAllPromoPrice(): Observable<List<PromotionPrice>> // For Testing

    fun getPromotionGiftByPlanID(promotionPlanId: String): Observable<List<PromotionGift>>
    fun getPromotionToBuyProduct(promotionPlanId: String, soldProductInfo: SoldProductInfo): Observable<List<PromotionGift>>

    fun getInvoiceCountByID(invoiceId: String): Observable<Int>

    fun insertInvoiceProduct(invoiceProduct: InvoiceProduct)

}
package com.aceplus.domain.repo

import com.aceplus.domain.entity.CompanyInformation
import com.aceplus.domain.entity.Location
import com.aceplus.domain.vo.SoldProductInfo
import com.aceplus.domain.entity.classdiscount.ClassDiscountByPrice
import com.aceplus.domain.entity.classdiscount.ClassDiscountByPriceItem
import com.aceplus.domain.entity.customer.Customer
import com.aceplus.domain.entity.customer.CustomerFeedback
import com.aceplus.domain.entity.customer.DidCustomerFeedback
import com.aceplus.domain.entity.invoice.Invoice
import com.aceplus.domain.entity.invoice.InvoiceProduct
import com.aceplus.domain.entity.product.Product
import com.aceplus.domain.entity.promotion.PromotionDate
import com.aceplus.domain.entity.promotion.PromotionGift
import com.aceplus.domain.entity.promotion.PromotionPrice
import com.aceplus.domain.entity.route.RouteScheduleV2
import com.aceplus.domain.entity.sale.SaleMan
import com.aceplus.domain.entity.volumediscount.VolumeDiscount
import com.aceplus.domain.entity.volumediscount.VolumeDiscountFilter
import com.aceplus.domain.entity.volumediscount.VolumeDiscountFilterItem
import com.aceplus.domain.model.routeSchedule_v2.RouteSchedule_v2
import com.aceplus.shared.utils.GPSTracker
import io.reactivex.Observable

interface CustomerVisitRepo {

    fun getLocationCode(): Int
    fun getSaleManData(): SaleMan
    fun getSaleManName(saleManId: String): Observable<List<String?>>
    fun getRouteID(saleManId: String): Observable<List<String>>
    fun getRouteScheduleByID(saleManId: String): Observable<RouteScheduleV2>
    fun getRouteNameByID(routeID: Int): Observable<String?>
    fun getRouteScheduleIDV2(): Int
    fun getLastCountForInvoiceNumber(mode: String): Int

    fun getAllCustomerData(): Observable<List<Customer>>
    fun getCustomerByID(customerID: Int): Observable<Customer>
    fun getCustomerTownshipName(customerID: Int): Observable<String>
    fun updateCustomerData(customer: Customer)
    fun getAllDidFeedback(): Observable<List<String>>
    fun getAllDefaultFeedback(): Observable<List<CustomerFeedback>>

    fun getAllProductData(): Observable<List<Product>>
    fun getProductByID(productID: Int): Observable<List<Product>>
    fun updateProductRemainingQty(soldProductInfo: SoldProductInfo)

    fun saveDataForTempSaleManRoute(selectedCustomer: Customer, currentDate: String)
    fun saveCustomerFeedback(didCustomerFeedbackEntity: DidCustomerFeedback)
    fun saveSaleVisitRecord(selectedCustomer: Customer, gpsTracker: GPSTracker)
    fun updateSaleVisitRecord(customerId: Int, visitFlag: String, saleFlag: String)

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
    fun insertNewInvoice(invoice: Invoice)
    fun getAllInvoice(): Observable<List<Invoice>>

    fun insertInvoiceProduct(invoiceProduct: InvoiceProduct)
    fun getAllInvoiceProduct(): Observable<List<InvoiceProduct>>
    fun insertAllInvoiceProduct(invoiceProductList: ArrayList<InvoiceProduct>)

    fun getAllLocation(): Observable<List<Location>>

    fun getCompanyInfo(): Observable<List<CompanyInformation>>

    fun getVolumeDiscountFilterByDate(currentDate: String): Observable<List<VolumeDiscountFilter>>
    fun getVolumeDiscountFilterItem(volDisFilterId: Int): Observable<List<VolumeDiscountFilterItem>>
    fun getDiscountPercentFromVolumeDiscountFilterItem(volDisFilterId: Int, buyAmt: Double): Observable<List<VolumeDiscountFilterItem>>

    fun getVolumeDiscountByDate(currentDate: String): Observable<List<VolumeDiscount>>

}
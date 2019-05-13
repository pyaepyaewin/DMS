package com.aceplus.domain.repo

import com.aceplus.domain.entity.sale.SaleMan
import com.aceplus.domain.model.customer.Customer
import com.aceplus.domain.model.forApi.DataForDiscountCategoryQuantity
import com.aceplus.domain.model.forApi.DiscountCategoryQuantityResponse
import com.aceplus.domain.model.forApi.classdiscount.ClassDiscountDataForShow
import com.aceplus.domain.model.forApi.classdiscount.ClassDiscountForShowResponse
import com.aceplus.domain.model.forApi.classdiscount.ClassDiscountPriceForApi
import com.aceplus.domain.model.forApi.classdiscount.ClassDiscountResponse
import com.aceplus.domain.model.forApi.company.CompanyInformationResponse
import com.aceplus.domain.model.forApi.credit.CreditResponse
import com.aceplus.domain.model.forApi.customer.CustomerForApi
import com.aceplus.domain.model.forApi.customer.CustomerResponse
import com.aceplus.domain.model.forApi.customer.CustomerVisitResponse
import com.aceplus.domain.model.forApi.delivery.DeliveryResponse
import com.aceplus.domain.model.forApi.incentive.IncentiveResponse
import com.aceplus.domain.model.forApi.other.GeneralData
import com.aceplus.domain.model.forApi.other.GeneralResponse
import com.aceplus.domain.model.forApi.posm.PosmShopTypeForApi
import com.aceplus.domain.model.forApi.posm.PosmShopTypeResponse
import com.aceplus.domain.model.forApi.preorder.PreOrderHistoryResponse
import com.aceplus.domain.model.forApi.product.ProductForApi
import com.aceplus.domain.model.forApi.product.ProductResponse
import com.aceplus.domain.model.forApi.promotion.PromotionForApi
import com.aceplus.domain.model.forApi.promotion.PromotionResponse
import com.aceplus.domain.model.forApi.sale.salehistory.SaleHistoryResponse
import com.aceplus.domain.model.forApi.sale.saletarget.SaleTargetResponse
import com.aceplus.domain.model.forApi.volumediscount.DataForVolumeDiscount
import com.aceplus.domain.model.forApi.volumediscount.VolumeDiscountResponse
import io.reactivex.Observable

interface SyncRepo {
    fun saveStartTime(time: String)

    fun getSaleManData(): SaleMan
    fun getRouteScheduleID(): Int
    fun getCustomerIdList(): List<Int>

    fun downloadCustomer(paramData: String): Observable<CustomerResponse>
    fun downloadProduct(paramData: String): Observable<ProductResponse>
    fun downloadClassDiscount(paramData: String): Observable<ClassDiscountResponse>
    fun downloadClassDiscountForShow(paramData: String): Observable<ClassDiscountForShowResponse>
    fun downloadPromotion(paramData: String): Observable<PromotionResponse>
    fun downloadVolumeDiscount(paramData: String): Observable<VolumeDiscountResponse>
    fun downloadDiscountCategoryQuantity(paramData: String): Observable<DiscountCategoryQuantityResponse>
    fun downloadGeneral(paramData: String): Observable<GeneralResponse>
    fun downloadPosmAndShopType(paramData: String): Observable<PosmShopTypeResponse>
    fun downloadDelivery(paramData: String): Observable<DeliveryResponse>
    fun downloadCredit(paramData: String): Observable<CreditResponse>
    fun downloadCustomerVisit(paramData: String): Observable<CustomerVisitResponse>
    fun downloadCompanyInformation(paramData: String): Observable<CompanyInformationResponse>
    fun downloadSaleTarget(paramData: String): Observable<SaleTargetResponse>
    fun downloadSaleHistory(paramData: String): Observable<SaleHistoryResponse>
    fun downloadIncentive(paramData: String): Observable<IncentiveResponse>
    fun downloadPreOrderHistory(paramData: String): Observable<PreOrderHistoryResponse>

    fun saveCustomerData(customerList: List<CustomerForApi>)
    fun saveProductData(productApiList: List<ProductForApi>)
    fun saveClassDiscountData(classDiscountList: List<ClassDiscountPriceForApi>)
    fun saveClassDiscountForShowData(classDiscountForShowList: List<ClassDiscountDataForShow>)
    fun savePromotionData(promotionList: List<PromotionForApi>)
    fun saveVolumeDiscountData(volumeDiscountList: List<DataForVolumeDiscount>)
    fun saveDiscountCategoryQuantityData(discountCategoryQuantityList: List<DataForDiscountCategoryQuantity>)
    fun saveGeneralData(generalDataList: List<GeneralData>)
    fun savePosmAndShopTypeData(posmAndShopTypeList: List<PosmShopTypeForApi>)
}
package com.aceplus.data.remote

import com.aceplus.domain.model.forApi.DiscountCategoryQuantityResponse
import com.aceplus.domain.model.forApi.classdiscount.ClassDiscountForShowResponse
import com.aceplus.domain.model.forApi.classdiscount.ClassDiscountResponse
import com.aceplus.domain.model.forApi.company.CompanyInformationResponse
import com.aceplus.domain.model.forApi.credit.CreditResponse
import com.aceplus.domain.model.forApi.customer.CustomerResponse
import com.aceplus.domain.model.forApi.customer.CustomerVisitResponse
import com.aceplus.domain.model.forApi.delivery.DeliveryResponse
import com.aceplus.domain.model.forApi.incentive.IncentiveResponse
import com.aceplus.domain.model.forApi.login.LoginResponse
import com.aceplus.domain.model.forApi.other.GeneralResponse
import com.aceplus.domain.model.forApi.posm.PosmShopTypeResponse
import com.aceplus.domain.model.forApi.preorder.PreOrderHistoryResponse
import com.aceplus.domain.model.forApi.product.ProductResponse
import com.aceplus.domain.model.forApi.promotion.PromotionResponse
import com.aceplus.domain.model.forApi.sale.salehistory.SaleHistoryResponse
import com.aceplus.domain.model.forApi.sale.saletarget.SaleTargetResponse
import com.aceplus.domain.model.forApi.volumediscount.VolumeDiscountResponse
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface DownloadApiService {

    @FormUrlEncoded
    @POST("login")
    fun login(@Field("param_data") paramData: String): Observable<LoginResponse>

    @FormUrlEncoded
    @POST("customer")
    fun getCustomer(@Field("param_data") paramData: String): Observable<CustomerResponse>

    @FormUrlEncoded
    @POST("saleitem")
    fun getProduct(@Field("param_data") paramData: String): Observable<ProductResponse>

    @FormUrlEncoded
    @POST("promotion")
    fun getPromotion(@Field("param_data") paramData: String): Observable<PromotionResponse>

    @FormUrlEncoded
    @POST("volumediscount")
    fun getVolumeDiscount(@Field("param_data") paramData: String): Observable<VolumeDiscountResponse>

    @FormUrlEncoded
    @POST("categoryQuantity")
    fun getDiscountCategoryQuantity(@Field("param_data") paramData: String): Observable<DiscountCategoryQuantityResponse>


    @FormUrlEncoded
    @POST("general")
    fun getGeneral(@Field("param_data") paramData: String): Observable<GeneralResponse>

    @FormUrlEncoded
    @POST("posm_shopType")
    fun getPosmAndShopType(@Field("param_data") paramData: String): Observable<PosmShopTypeResponse>

    @FormUrlEncoded
    @POST("delivery")
    fun getDeliveryFromApi(@Field("param_data") paramData: String): Observable<DeliveryResponse>

    /*@FormUrlEncoded
    @POST("standardExternalCheck")
    Call<DownloadMarketing> getMarketingFromApi(@Field("param_data") String paramData);
*/
    @FormUrlEncoded
    @POST("creditCollection")
    fun getCreditFromApi(@Field("param_data") paramData: String): Observable<CreditResponse>

    @FormUrlEncoded
    @POST("customerVisit")
    fun getCustomerVisitFromApi(@Field("param_data") paramData: String): Observable<CustomerVisitResponse>

    @FormUrlEncoded
    @POST("saleTarget")
    fun getSaleTargetFromApi(@Field("param_data") paramData: String): Observable<SaleTargetResponse>

    @FormUrlEncoded
    @POST("companyInfo")
    fun getCompanyInformationFromApi(@Field("param_data") paramData: String): Observable<CompanyInformationResponse>

    @FormUrlEncoded
    @POST("saleHistory")
    fun getSaleHistoryFromApi(@Field("param_data") paramData: String): Observable<SaleHistoryResponse>

    /*@FormUrlEncoded
    @POST("reissue")
    Call<ProductResponse> getReissueProductFromApi(@Field("param_data") String paramData);*/

    @FormUrlEncoded
    @POST("incentive")
    fun getIncentiveFromApi(@Field("param_data") paramData: String): Observable<IncentiveResponse>

    @FormUrlEncoded
    @POST("saleOrderHistory")
    fun getPreOrderHistoryFromApi(@Field("param_data") paramData: String): Observable<PreOrderHistoryResponse>

    @FormUrlEncoded
    @POST("classDiscount")
    fun getClassDiscount(@Field("param_data") paramData: String): Observable<ClassDiscountResponse>

    @FormUrlEncoded
    @POST("showClassDiscount")
    fun getClassDiscountForShow(@Field("param_data") paramData: String): Observable<ClassDiscountForShowResponse>
}
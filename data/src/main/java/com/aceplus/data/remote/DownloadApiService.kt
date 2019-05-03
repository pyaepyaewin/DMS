//package com.aceplus.data.remote
//
//import com.aceplus.domain.model.LoginResponse
//import retrofit2.Call
//import retrofit2.http.FormUrlEncoded
//import retrofit2.http.POST
//
//interface DownloadApiService {
//    @FormUrlEncoded
//    @POST("login")
//    abstract fun login(@Field("param_data") paramData: String): Call<LoginResponse>
//
//    @FormUrlEncoded
//    @POST("customer")
//    abstract fun getCustomer(@Field("param_data") paramData: String): Call<CustomerResponse>
//
//    @FormUrlEncoded
//    @POST("saleitem")
//    abstract fun getProduct(@Field("param_data") paramData: String): Call<ProductResponse>
//
//    @FormUrlEncoded
//    @POST("promotion")
//    abstract fun getPromotion(@Field("param_data") paramData: String): Call<PromotionResponse>
//
//    @FormUrlEncoded
//    @POST("volumediscount")
//    abstract fun getVolumeDiscount(@Field("param_data") paramData: String): Call<VolumeDiscountResponse>
//
//    @FormUrlEncoded
//    @POST("categoryQuantity")
//    abstract fun getDiscountCategoryQuantity(@Field("param_data") paramData: String): Call<DiscountCategoryQuantityResponse>
//
//
//    @FormUrlEncoded
//    @POST("general")
//    abstract fun getGeneral(@Field("param_data") paramData: String): Call<GeneralResponse>
//
//    @FormUrlEncoded
//    @POST("posm_shopType")
//    abstract fun getPosmAndShopType(@Field("param_data") paramData: String): Call<PosmShopTypeResponse>
//
//    @FormUrlEncoded
//    @POST("delivery")
//    abstract fun getDeliveryFromApi(@Field("param_data") paramData: String): Call<DeliveryResponse>
//
//    /*@FormUrlEncoded
//    @POST("standardExternalCheck")
//    Call<DownloadMarketing> getMarketingFromApi(@Field("param_data") String paramData);
//*/
//    @FormUrlEncoded
//    @POST("creditCollection")
//    abstract fun getCreditFromApi(@Field("param_data") paramData: String): Call<CreditResponse>
//
//    @FormUrlEncoded
//    @POST("customerVisit")
//    abstract fun getCustomerVisitFromApi(@Field("param_data") paramData: String): Call<CustomerVisitResponse>
//
//    @FormUrlEncoded
//    @POST("saleTarget")
//    abstract fun getSaleTargetFromApi(@Field("param_data") paramData: String): Call<SaleTargetResponse>
//
//    @FormUrlEncoded
//    @POST("companyInfo")
//    abstract fun getCompanyInformationFromApi(@Field("param_data") paramData: String): Call<CompanyInformationResponse>
//
//    @FormUrlEncoded
//    @POST("saleHistory")
//    abstract fun getSaleHistoryFromApi(@Field("param_data") paramData: String): Call<SaleHistoryResponse>
//
//    /*@FormUrlEncoded
//    @POST("reissue")
//    Call<ProductResponse> getReissueProductFromApi(@Field("param_data") String paramData);*/
//
//    @FormUrlEncoded
//    @POST("incentive")
//    abstract fun getIncentiveFromApi(@Field("param_data") paramData: String): Call<IncentiveResponse>
//
//    @FormUrlEncoded
//    @POST("saleOrderHistory")
//    abstract fun getPreOrderHistoryFromApi(@Field("param_data") paramData: String): Call<PreOrderHistoryResponse>
//
//    @FormUrlEncoded
//    @POST("classDiscount")
//    abstract fun getClassDiscount(@Field("param_data") paramData: String): Call<ClassDiscountResponse>
//
//    @FormUrlEncoded
//    @POST("showClassDiscount")
//    abstract fun getClassDiscountForShow(@Field("param_data") paramData: String): Call<ClassDiscountForShowResponse>
//}
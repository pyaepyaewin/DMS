package com.aceplus.data.remote

import com.aceplus.domain.model.forApi.InvoiceResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UploadApiService {

    @FormUrlEncoded
    @POST("upload/tsale")
    abstract fun getSaleInvoice(@Field("param_data") paramData: String): Call<InvoiceResponse>

    @FormUrlEncoded
    @POST("upload/tsale_cancel")
    abstract fun getSaleCancelInvoice(@Field("param_data") paramData: String): Call<InvoiceResponse>

    @FormUrlEncoded
    @POST("upload/issue_request")
    abstract fun getUploadVanIssue(@Field("param_data") paramData: String): Call<InvoiceResponse>

    @FormUrlEncoded
    @POST("upload/customer")
    abstract fun getCustomer(@Field("param_data") paramData: String): Call<InvoiceResponse>

    @FormUrlEncoded
    @POST("upload/existing_customer")
    abstract fun getExistingCustomer(@Field("param_data") paramData: String): Call<InvoiceResponse>

    @FormUrlEncoded
    @POST("upload/preorder")
    abstract fun uploadPreOrderData(@Field("param_data") paramData: String): Call<InvoiceResponse>

    @FormUrlEncoded
    @POST("Sale/GetTSaleOrderInfoCollection/0")
    abstract fun uploadRealTimePreOrderData(@Field("param_data") paramData: String): Call<InvoiceResponse>

    @FormUrlEncoded
    @POST("upload/tsalereturn")
    abstract fun uploadSaleReturn(@Field("param_data") paramData: String): Call<InvoiceResponse>

    @FormUrlEncoded
    @POST("upload/posmByCustomer")
    abstract fun uploadPosm(@Field("param_data") paramData: String): Call<InvoiceResponse>

    @FormUrlEncoded
    @POST("upload/delivery")
    abstract fun uploadDelivery(@Field("param_data") paramData: String): Call<InvoiceResponse>

    @FormUrlEncoded
    @POST("upload/tCashReceive")
    abstract fun uploadCashReceive(@Field("param_data") paramData: String): Call<InvoiceResponse>

    @FormUrlEncoded
    @POST("upload/visibility")
    abstract fun uploadDisplayAssessment(@Field("param_data") paramData: String): Call<InvoiceResponse>

    @FormUrlEncoded
    @POST("upload/sizeAndStock")
    abstract fun uploadcompetitorsizeinstore(@Field("param_data") paramData: String): Call<InvoiceResponse>

    @FormUrlEncoded
    @POST("upload/customerVisit")
    abstract fun uploadCustomerVisit(@Field("param_data") paramData: String): Call<InvoiceResponse>

    @FormUrlEncoded
    @POST("upload/eRoute")
    abstract fun uploadSaleManRoute(@Field("param_data") paramData: String): Call<InvoiceResponse>

    @FormUrlEncoded
    @POST("deviceIssue/status")
    abstract fun confirmDownloadSuccess(@Field("param_data") paramData: String): Call<InvoiceResponse>

    @FormUrlEncoded
    @POST("upload/unsellReason")
    abstract fun uploadUnsellReason(@Field("param_data") paramData: String): Call<InvoiceResponse>

    @FormUrlEncoded
    @POST("upload/incentive")
    abstract fun uploadIncentivePaid(@Field("param_data") paramData: String): Call<InvoiceResponse>

    @FormUrlEncoded
    @POST("upload/competitor")
    abstract fun uploadCompetitorActivities(@Field("param_data") paramData: String): Call<InvoiceResponse>
}
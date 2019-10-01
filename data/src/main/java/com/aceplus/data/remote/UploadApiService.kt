package com.aceplus.data.remote

import com.aceplus.domain.model.forApi.invoice.InvoiceResponse
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UploadApiService {

    @FormUrlEncoded
    @POST("upload/tsale")
    fun uploadSaleInvoice(@Field("param_data") paramData: String): Observable<InvoiceResponse>

    @FormUrlEncoded
    @POST("upload/tsale_cancel")
    fun uploadSaleCancel(@Field("param_data") paramData: String): Observable<InvoiceResponse>

    @FormUrlEncoded
    @POST("upload/issue_request")
    fun uploadVanIssue(@Field("param_data") paramData: String): Observable<InvoiceResponse>

    @FormUrlEncoded
    @POST("upload/customer")
    fun uploadCustomer(@Field("param_data") paramData: String): Observable<InvoiceResponse>

    @FormUrlEncoded
    @POST("upload/existing_customer")
    fun uploadExistingCustomer(@Field("param_data") paramData: String): Observable<InvoiceResponse>

    @FormUrlEncoded
    @POST("upload/preorder")
    fun uploadPreOrderData(@Field("param_data") paramData: String): Observable<InvoiceResponse>

    @FormUrlEncoded
    @POST("upload/indirectsales")
    fun uploadIndirectPreOrderData(@Field("param_data") paramData: String): Observable<InvoiceResponse>

    @FormUrlEncoded
    @POST("Sale/GetTSaleOrderInfoCollection/0")
    fun uploadRealTimePreOrderData(@Field("param_data") paramData: String): Observable<InvoiceResponse>

    @FormUrlEncoded
    @POST("upload/tsalereturn")
    fun uploadSaleReturn(@Field("param_data") paramData: String): Observable<InvoiceResponse>

    @FormUrlEncoded
    @POST("upload/posmByCustomer")
    fun uploadPosmByCustomer(@Field("param_data") paramData: String): Observable<InvoiceResponse>

    @FormUrlEncoded
    @POST("upload/delivery")
    fun uploadDelivery(@Field("param_data") paramData: String): Observable<InvoiceResponse>

    @FormUrlEncoded
    @POST("upload/tCashReceive")
    fun uploadCashReceive(@Field("param_data") paramData: String): Observable<InvoiceResponse>

    @FormUrlEncoded
    @POST("upload/visibility")
    fun uploadDisplayAssessment(@Field("param_data") paramData: String): Observable<InvoiceResponse>

    @FormUrlEncoded
    @POST("upload/sizeAndStock")
    fun uploadCompetitorSizeInStore(@Field("param_data") paramData: String): Observable<InvoiceResponse>

    @FormUrlEncoded
    @POST("upload/customerVisit")
    fun uploadCustomerVisit(@Field("param_data") paramData: String): Observable<InvoiceResponse>

    @FormUrlEncoded
    @POST("upload/eRoute")
    fun uploadSaleManRoute(@Field("param_data") paramData: String): Observable<InvoiceResponse>

    @FormUrlEncoded
    @POST("deviceIssue/status")
    fun uploadConfirmDownloadSuccess(@Field("param_data") paramData: String): Observable<InvoiceResponse>

    @FormUrlEncoded
    @POST("upload/unsellReason")
    fun uploadUnsellReason(@Field("param_data") paramData: String): Observable<InvoiceResponse>

    @FormUrlEncoded
    @POST("upload/incentive")
    fun uploadIncentive(@Field("param_data") paramData: String): Observable<InvoiceResponse>

    @FormUrlEncoded
    @POST("upload/competitor")
    fun uploadCompetitor(@Field("param_data") paramData: String): Observable<InvoiceResponse>
}
package com.aceplus.data.remote

import com.aceplus.domain.model.forApi.invoice.InvoiceResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RealTimeUploadApiService {

    @FormUrlEncoded
    @POST("upload/preorder")
    fun uploadPreOrderData(@Field("param_data") paramData: String): Call<InvoiceResponse>

}
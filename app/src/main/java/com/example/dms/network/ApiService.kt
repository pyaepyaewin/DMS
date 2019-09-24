package com.example.dms.network

import com.example.dms.network.request.customerRequest
import com.example.dms.network.response.CustomerListResponse
import com.example.dms.network.response.Sale.SaleListResponse
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

@FormUrlEncoded
@POST("customer")
fun loadCustomerData(@Field("param_data") param: String): Observable<CustomerListResponse>

 @FormUrlEncoded
 @POST("saleitem")
 fun loadSaleData(@Field("param_data")param: String):Observable<SaleListResponse>


}
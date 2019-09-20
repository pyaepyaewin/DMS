package com.example.dms.network

import retrofit2.http.POST

interface ApiService {
@POST("customer")
fun loadCustomerList()
}
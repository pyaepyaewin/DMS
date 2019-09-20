package com.example.dms.network.network_response.customer

data class CustomerResponse(
    val aceplusStatusCode: Int,
    val aceplusStatusMessage: String,
    val data: List<Data>,
    val user_id: String
)
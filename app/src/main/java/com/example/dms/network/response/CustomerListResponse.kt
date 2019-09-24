package com.example.dms.network.response

data class CustomerListResponse(
    val aceplusStatusCode: Int,
    val aceplusStatusMessage: String,
    val data: List<Data>,
    val user_id: String
)
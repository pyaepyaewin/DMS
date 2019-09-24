package com.example.dms.network.response.Sale

data class SaleListResponse(
    val aceplusStatusCode: Int,
    val aceplusStatusMessage: String,
    val data: List<Data>,
    val user_id: String
)
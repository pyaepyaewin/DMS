package com.example.dms.network.response

data class CustomerListResponse(
    val aceplusStatusCode: String?=null,
    val aceplusStatusMessage: String?=null,
    val data: List<Data>,
    var user_id: String?=null
)
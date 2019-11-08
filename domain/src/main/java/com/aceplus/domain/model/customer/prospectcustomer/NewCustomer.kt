package com.aceplus.domain.model.customer.prospectcustomer

class NewCustomer(
    var id: Int = 0,
    var customerId: String,
    val customerName: String,
    val contactPerson: String,
    val phone: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    var streetId: Int ,
    val townshipNumber: Int,
    var township: String,
    val districtId: Int,
    val stateDivisionId: Int,
    val shopTypeId: Int,
    var createdUserId: String,
    var createdDate: String,
    var flag: String

)
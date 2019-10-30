package com.aceplus.domain.model.customer.prospectcustomer

class NewCustomer(
    val customerName: String,
    val contact_person: String,
    val phone: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val township_number: Int,
    val district_id: Int,
    val state_division_id: Int,
    val shopTypeId: Int
)
package com.aceplus.domain.model.creditcollectiondataclass

data class CreditCollectionDataClass(
    val id:String,
    val customer_name:String,
    val balance:Double?,
    val pay_amount:Double?,
    val amount:Double
) {
}
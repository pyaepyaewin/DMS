package com.aceplus.domain.model.creditcollectiondataclass

data class CreditCollectionCheckoutDataClass(
    val invoice_no: String,
    val invoice_date: String,
    val amount: String,
    val pay_amount:String
) {
}
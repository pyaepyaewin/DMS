package com.aceplus.domain.model.creditcollectiondataclass

import java.io.Serializable

data class CreditCollectionCheckoutDataClass(
    val id:String,
    val invoice_no: String,
    val invoice_date: String,
    val amount: Double,
    var pay_amount:Double,
    val sale_man_id:String
):Serializable {
}
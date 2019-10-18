package com.aceplus.domain.model.promotionDataClass

class ClassDiscountForShowPriceDataClass(
    val discount_type: String,
    val name: Int,
    val from_quantity: String,
    val to_quantity: String,
    val from_amount: String,
    val to_amount: String,
    val discount_percent: String?
) {
}
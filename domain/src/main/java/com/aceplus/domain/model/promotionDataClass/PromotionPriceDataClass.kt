package com.aceplus.domain.model.promotionDataClass

data class PromotionPriceDataClass(
    var product_name: String,
    var from_quantity: String,
    var to_quantity: String,
    var promotion_price: String?
) {
}
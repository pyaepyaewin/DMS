package com.aceplus.domain.model.promotionDataClass

data class CategoryDiscountDataClass(
     var category_name: String,
     var from_quantity: String,
     var to_quantity: String,
     var discount_percent: String
    ) {
}
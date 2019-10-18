package com.aceplus.domain.VO

import com.aceplus.domain.entity.promotion.Promotion

data class CalculatedSoldProduct(
    val soldProductInfo: SoldProductInfo,
    val promotionList: ArrayList<Promotion>,
    val position: Int
)
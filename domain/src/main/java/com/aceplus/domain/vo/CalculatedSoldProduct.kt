package com.aceplus.domain.vo

import com.aceplus.domain.entity.promotion.Promotion

class CalculatedSoldProduct(
    var soldProductInfo: SoldProductInfo,
    var promotionList: ArrayList<Promotion>,
    var position: Int
)
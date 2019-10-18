package com.aceplus.domain.VO

import com.aceplus.domain.entity.promotion.Promotion

class CalculatedSoldProduct(
    var soldProductInfo: SoldProductInfo,
    var promotionList: ArrayList<Promotion>,
    var position: Int
)
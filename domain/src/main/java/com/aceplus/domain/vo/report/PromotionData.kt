package com.aceplus.domain.vo.report

import android.arch.persistence.room.ColumnInfo

class PromotionData (
    @ColumnInfo(name = "stock_id")
    var stockId: Int = 0,
    @ColumnInfo(name = "quantity")
    var quantity: Double = 0.0,
    @ColumnInfo(name = "currency_id")
    var currencyId: Int = 0,
    @ColumnInfo(name = "product_name")
    var productName: String? = null,
    @ColumnInfo(name = "id")
    var productId: Int = 0,
    @ColumnInfo(name = "selling_price")
    var sellingPrice:  String? = null
)
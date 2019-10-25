package com.aceplus.domain.vo.report

import android.arch.persistence.room.ColumnInfo

class SaleTargetVO (
    @ColumnInfo(name = "sale_quantity")
    var saleQuantity:String? = "",
    @ColumnInfo(name = "total_amount")
    var totalAmount:Double? = 0.0,
    @ColumnInfo(name = "product_id")
    var productId:String? = ""
)
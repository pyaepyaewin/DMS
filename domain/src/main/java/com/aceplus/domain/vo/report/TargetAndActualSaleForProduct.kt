package com.aceplus.domain.vo.report

import android.arch.persistence.room.ColumnInfo

class TargetAndActualSaleForProduct(

    @ColumnInfo(name = "product_id")
    var productId: String? = null,

    @ColumnInfo(name = "total_amount")
    var totalAmount :Double = 0.0,

    @ColumnInfo(name = "sale_quantity")
    var saleQuantity :String? = null
)
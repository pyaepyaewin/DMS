package com.aceplus.domain.vo.report

import android.arch.persistence.room.ColumnInfo

class TargetAndSaleForSaleMan (
    @ColumnInfo(name = "total_amount")
    val totalAmount: Double,
    @ColumnInfo(name = "sale_quantity")
    val saleQuantity: String,
    @ColumnInfo(name = "product_id")
    val productId : String
)
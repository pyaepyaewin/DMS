package com.aceplus.domain.vo.report

import android.arch.persistence.room.ColumnInfo

class PreOrderDetailReport(
    @ColumnInfo(name = "product_name")
    val productName:String,
    @ColumnInfo(name = "order_quantity")
    val orderQuantity:String,
    @ColumnInfo(name = "total_amount")
    val totalAmount:String
)
package com.aceplus.domain.vo.report

import android.arch.persistence.room.ColumnInfo

class DeliverDetailReport(
    @ColumnInfo(name = "product_name")
    val productName:String,
    @ColumnInfo(name = "total_quantity")
    val totalQuantity:Double
)
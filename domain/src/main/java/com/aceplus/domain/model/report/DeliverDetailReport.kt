package com.aceplus.domain.model.report

import android.arch.persistence.room.ColumnInfo

class DeliverDetailReport(
    @ColumnInfo(name = "product_name")
    val product_name:String,
    @ColumnInfo(name = "total_quantity")
    val total_quantity:Double
)
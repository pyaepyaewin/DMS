package com.aceplus.domain.model.report

import android.arch.persistence.room.ColumnInfo

class PreOrderDetailReport(
    @ColumnInfo(name = "product_name")
    val product_name:String,
    @ColumnInfo(name = "total_quantity")
    val total_quantity:Double,
    @ColumnInfo(name = "total_amount")
    val total_amount:String
)
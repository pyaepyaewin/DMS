package com.aceplus.domain.vo.report

import android.arch.persistence.room.ColumnInfo

class TotalAmountForDeliveryReport (
    @ColumnInfo(name = "quantity")
    val quantity: String,

    @ColumnInfo(name = "selling_price")
    val sellingPrice: String
)
package com.aceplus.domain.vo.report

import android.arch.persistence.room.ColumnInfo

class SalesReturnDetailReport(
    @ColumnInfo(name = "product_name")
    var customerName:String,
    @ColumnInfo(name = "total_quantity")
    var totalQuantity:Double
)
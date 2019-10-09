package com.aceplus.domain.model.report

import android.arch.persistence.room.ColumnInfo

class SalesReturnDetailReport(
    @ColumnInfo(name = "product_name")
    var customer_name:String,
    @ColumnInfo(name = "total_quantity")
    var total_quantity:Double
)
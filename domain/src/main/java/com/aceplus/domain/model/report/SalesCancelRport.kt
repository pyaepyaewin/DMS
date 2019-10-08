package com.aceplus.domain.model.report

import android.arch.persistence.room.ColumnInfo

class SalesCancelReport (
    @ColumnInfo(name = "invoice_id")
    var invoice_id:Int,

    @ColumnInfo(name = "customer_name")
    var customer_name:String,

    @ColumnInfo(name = "total_amount")
    var total_amount:Double,

    @ColumnInfo(name = "total_discount_amount")
    var total_discount_amount:Double
)

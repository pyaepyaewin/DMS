package com.aceplus.domain.vo.report

import android.arch.persistence.room.ColumnInfo

class SalesCancelReport (
    @ColumnInfo(name = "invoice_id")
    var invoiceId:Int,

    @ColumnInfo(name = "customer_name")
    var customerName:String,

    @ColumnInfo(name = "total_amount")
    var totalAmount:Double,

    @ColumnInfo(name = "total_discount_amount")
    var totalDiscountAmount:Double
)

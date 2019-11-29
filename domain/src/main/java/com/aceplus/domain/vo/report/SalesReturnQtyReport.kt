package com.aceplus.domain.vo.report

import android.arch.persistence.room.ColumnInfo

class SalesReturnQtyReport (
    @ColumnInfo(name = "sale_return_id")
    val saleReturnId: String,

    @ColumnInfo(name = "customer_name")
    val customerName: String,

    @ColumnInfo(name = "address")
    val address: String,

    @ColumnInfo(name = "return_date")
    val returnDate: String,

    @ColumnInfo(name = "pay_amount")
    val totalAmount: Double
)
package com.aceplus.domain.model.report

import android.arch.persistence.room.ColumnInfo

class SalesReturnReport(
    @ColumnInfo(name = "invoice_id")
    val invoice_id: String,

    @ColumnInfo(name = "customer_name")
    val customer_name: String,

    @ColumnInfo(name = "address")
    val address: String,

    @ColumnInfo(name = "return_date")
    val return_date: String,

    @ColumnInfo(name = "total_quantity")
    val total_quantity: Double,

    @ColumnInfo(name = "total_amount")
    val total_amount: String
)
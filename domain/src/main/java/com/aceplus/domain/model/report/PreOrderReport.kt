package com.aceplus.domain.model.report

import android.arch.persistence.room.ColumnInfo

class PreOrderReport (
    @ColumnInfo(name = "invoice_id")
    val invoice_id: String,

    @ColumnInfo(name = "customer_name")
    val customer_name: String,

    @ColumnInfo(name = "total_quantity")
    val total_quantity: Double,

    @ColumnInfo(name = "prepaid_amount")
    val prepaid_amount: String,

    @ColumnInfo(name = "total_amount")
    val total_amount: String
)
package com.aceplus.domain.vo.report

import android.arch.persistence.room.ColumnInfo

class PreOrderReport (
    @ColumnInfo(name = "invoice_id")
    val invoiceId: String,

    @ColumnInfo(name = "customer_name")
    val customerName: String,

    @ColumnInfo(name = "total_quantity")
    val totalQuantity: Double,

    @ColumnInfo(name = "prepaid_amount")
    val prepaidAmount: String,

    @ColumnInfo(name = "total_amount")
    val totalAmount: String
)
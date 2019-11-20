package com.aceplus.domain.vo.report

import android.arch.persistence.room.ColumnInfo

class PreOrderQtyReport (
    @ColumnInfo(name = "invoice_id")
    val invoiceId: String,

    @ColumnInfo(name = "customer_name")
    val customerName: String,

    @ColumnInfo(name = "total_quantity")
    val totalQuantity: Double,

    @ColumnInfo(name = "advance_payment_amount")
    val prepaidAmount: String,

    @ColumnInfo(name = "net_amount")
    val totalAmount: String
)
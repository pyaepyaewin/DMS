package com.aceplus.domain.vo.report

import android.arch.persistence.room.ColumnInfo

class IncompleteDeliverReport (
    @ColumnInfo(name = "invoice_no")
    val invoiceId: String,

    @ColumnInfo(name = "customer_name")
    val customerName: String,

    @ColumnInfo(name = "address")
    val address: String
)
package com.aceplus.domain.vo.report

import android.arch.persistence.room.ColumnInfo

class SalesOrderHistoryReport (
    @ColumnInfo(name = "invoice_id")
    var invoiceId:String,

    @ColumnInfo(name = "customer_name")
    var customerName:String,

    @ColumnInfo(name = "address")
    var address:String,

    @ColumnInfo(name = "total_amount")
    var totalAmount:String,

    @ColumnInfo(name = "discount")
    var discount: String,

    @ColumnInfo(name = "advance_payment_amount")
    var advancePaymentAmount: String,

    @ColumnInfo(name = "net_amount")
    var netAmount: String


)
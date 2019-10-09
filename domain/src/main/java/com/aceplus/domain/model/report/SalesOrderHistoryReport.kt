package com.aceplus.domain.model.report

import android.arch.persistence.room.ColumnInfo

class SalesOrderHistoryReport (
    @ColumnInfo(name = "invoice_id")
    var invoice_id:String,

    @ColumnInfo(name = "customer_name")
    var customer_name:String,

    @ColumnInfo(name = "address")
    var address:String,

    @ColumnInfo(name = "total_amount")
    var total_amount:String,

    @ColumnInfo(name = "discount")
    var discount: String,

    @ColumnInfo(name = "advance_payment_amount")
    var advance_payment_amount: String,

    @ColumnInfo(name = "net_amount")
    var net_amount: String


)
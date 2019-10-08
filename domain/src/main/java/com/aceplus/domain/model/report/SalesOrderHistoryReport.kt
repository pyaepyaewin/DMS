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
    var total_amount:String

)
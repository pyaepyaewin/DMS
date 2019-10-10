package com.aceplus.domain.vo.report

import android.arch.persistence.room.ColumnInfo

class SalesVisitHistoryReport(
    @ColumnInfo(name = "customer_name")
    val customerName: String,

    @ColumnInfo(name = "address")
    val address: String,
    @ColumnInfo(name = "status")
    val status: String,

    @ColumnInfo(name = "sale_status")
    val task: String

)
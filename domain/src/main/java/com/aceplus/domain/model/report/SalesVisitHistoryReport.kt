package com.aceplus.domain.model.report

import android.arch.persistence.room.ColumnInfo

class SalesVisitHistoryReport(
    @ColumnInfo(name = "customer_name")
    val customer_name: String,

    @ColumnInfo(name = "address")
    val address: String,
    @ColumnInfo(name = "status")
    val status: String,

    @ColumnInfo(name = "task")
    val task: String

)
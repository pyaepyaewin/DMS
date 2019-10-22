package com.aceplus.domain.vo.report

import android.arch.persistence.room.ColumnInfo

class EndOfDayReport(
    @ColumnInfo(name = "user_name")
    val userName: String,
    @ColumnInfo(name = "route_name")
    val routeName: String,
    @ColumnInfo(name = "start_time")
    val startTime: String,
    @ColumnInfo(name = "end_time")
    val endTime: String,
    @ColumnInfo(name = "total_amount")
    val totalSaleAmount: String,
    @ColumnInfo(name = "net_amount")
    val totalSaleOrderAmount: String,
    @ColumnInfo(name = "amount")
    val totalReturnAmount: String,
    @ColumnInfo(name = "amount")
    val totalCashReceiptAmount: String


)
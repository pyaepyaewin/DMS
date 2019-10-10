package com.aceplus.domain.vo.report

import android.arch.persistence.room.ColumnInfo

class UnsellReasonReport (
    @ColumnInfo(name = "customer_name")
    var customerName:String,
    @ColumnInfo(name = "description")
    var description:String,
    @ColumnInfo(name = "remark")
    var remark: String

)
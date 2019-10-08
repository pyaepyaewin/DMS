package com.aceplus.domain.model.report

import android.arch.persistence.room.ColumnInfo

class UnsellReasonReport (
    @ColumnInfo(name = "customer_name")
    var customer_name:String,
    @ColumnInfo(name = "description")
    var description:String,
    @ColumnInfo(name = "remark")
    var remark: String

)
package com.aceplus.domain.entity.customer

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "customer_visit_record_report")
class CustomerVisitRecordReport {

    @ColumnInfo(name = "customer_name")
    @SerializedName("customer_name")
    @Expose
    var customer_name: String? = null

    @ColumnInfo(name = "address")
    @SerializedName("address")
    @Expose
    var address: String? = null

    @ColumnInfo(name = "current_time")
    @SerializedName("current_time")
    @Expose
    var current_time: String? = null

}
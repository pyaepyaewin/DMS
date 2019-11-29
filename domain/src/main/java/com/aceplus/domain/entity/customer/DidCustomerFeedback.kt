package com.aceplus.domain.entity.customer

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "did_customer_feedback")
class DidCustomerFeedback {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    var id: Int = 0

    @ColumnInfo(name = "sale_man_id")
    @SerializedName("sale_man_id")
    @Expose
    var sale_man_id: Int = 0

    @ColumnInfo(name = "dev_id")
    @SerializedName("dev_id")
    @Expose
    var dev_id: String? = ""

    @ColumnInfo(name = "invoice_no")
    @SerializedName("invoice_no")
    @Expose
    var invoice_no: String = ""

    @ColumnInfo(name = "invoice_date")
    @SerializedName("invoice_date")
    @Expose
    var invoice_date: String? = null

    @ColumnInfo(name = "customer_no")
    @SerializedName("customer_no")
    @Expose
    var customer_no: Int = 0

    @ColumnInfo(name = "location_no")
    @SerializedName("location_no")
    @Expose
    var location_no: Int = 0

    @ColumnInfo(name = "feedback_no")
    @SerializedName("feedback_no")
    @Expose
    var feedback_no: Int = 0

    @ColumnInfo(name = "feedback_date")
    @SerializedName("feedback_date")
    @Expose
    var feedback_date: String? = null

    @ColumnInfo(name = "serial_no")
    @SerializedName("serial_no")
    @Expose
    var serial_no: String? = null

    @ColumnInfo(name = "description")
    @SerializedName("description")
    @Expose
    var description: String? = null

    @ColumnInfo(name = "remark")
    @SerializedName("remark")
    @Expose
    var remark: String? = null

    @ColumnInfo(name = "route_id")
    @SerializedName("route_id")
    @Expose
    var route_id: Int = 0
}
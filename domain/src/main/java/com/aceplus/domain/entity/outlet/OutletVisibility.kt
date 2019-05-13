package com.aceplus.domain.entity.outlet

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "outlet_visibility")
class OutletVisibility {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    var id: Int = 0

    @ColumnInfo(name = "invoice_no")
    @SerializedName("invoice_no")
    @Expose
    var invoice_no: String? = null

    @ColumnInfo(name = "invoice_date")
    @SerializedName("invoice_date")
    @Expose
    var invoice_date: String? = null

    @ColumnInfo(name = "customer_id")
    @SerializedName("customer_id")
    @Expose
    var customer_id: Int = 0

    @ColumnInfo(name = "sale_man_id")
    @SerializedName("sale_man_id")
    @Expose
    var sale_man_id: Int = 0

    @ColumnInfo(name = "image")
    @SerializedName("image")
    @Expose
    var image: String? = null

    @ColumnInfo(name = "image_name")
    @SerializedName("image_name")
    @Expose
    var image_name: String? = null

    @ColumnInfo(name = "image_no")
    @SerializedName("image_no")
    @Expose
    var image_no: String? = null

    @ColumnInfo(name = "customer_no")
    @SerializedName("customer_no")
    @Expose
    var customer_no: String? = null

    @ColumnInfo(name = "date_and_time")
    @SerializedName("date_and_time")
    @Expose
    var date_and_time: String? = null

    @ColumnInfo(name = "remark")
    @SerializedName("remark")
    @Expose
    var remark: String? = null

    @ColumnInfo(name = "delete_flag")
    @SerializedName("delete_flag")
    @Expose
    var delete_flag: String? = null

}
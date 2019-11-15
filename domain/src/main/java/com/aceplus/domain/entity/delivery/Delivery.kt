package com.aceplus.domain.entity.delivery

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "delivery")
class Delivery {

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
    var customer_id: String? = null

    @ColumnInfo(name = "amount")
    @SerializedName("amount")
    @Expose
    var amount: String? = null

    @ColumnInfo(name = "paid_amount")
    @SerializedName("paid_amount")
    @Expose
    var paid_amount: String? = null

    @ColumnInfo(name = "discount")
    @SerializedName("discount")
    @Expose
    var discount: Double? = null

    @ColumnInfo(name = "discount_percent")
    @SerializedName("discount_percent")
    @Expose
    var discount_percent: Double? = null

    @ColumnInfo(name = "expire_date")
    @SerializedName("expire_date")
    @Expose
    var expire_date: String? = null

    @ColumnInfo(name = "sale_man_id")
    @SerializedName("sale_man_id")
    @Expose
    var sale_man_id:String? = null

    @ColumnInfo(name = "remark")
    @SerializedName("remark")
    @Expose
    var remark: String? = null
}
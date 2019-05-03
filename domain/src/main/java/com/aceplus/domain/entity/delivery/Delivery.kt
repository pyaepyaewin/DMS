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
    var invoice_no: Int = 0

    @ColumnInfo(name = "invoice_date")
    @SerializedName("invoice_date")
    @Expose
    var invoice_date: String? = null

    @ColumnInfo(name = "customer_id")
    @SerializedName("customer_id")
    @Expose
    var customer_id: Int = 0

    @ColumnInfo(name = "amount")
    @SerializedName("amount")
    @Expose
    var amount: Double = 0.0

    @ColumnInfo(name = "paid_amount")
    @SerializedName("paid_amount")
    @Expose
    var paid_amount: Double = 0.0

    @ColumnInfo(name = "expire_date")
    @SerializedName("expire_date")
    @Expose
    var expire_date: String? = null

    @ColumnInfo(name = "sale_man_id")
    @SerializedName("sale_man_id")
    @Expose
    var sale_man_id: Int = 0
}
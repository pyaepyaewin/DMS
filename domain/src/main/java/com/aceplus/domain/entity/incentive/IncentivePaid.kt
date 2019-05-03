package com.aceplus.domain.entity.incentive

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "incentive_paid")
class IncentivePaid {

    @ColumnInfo(name = "invoice_no")
    @SerializedName("invoice_no")
    @Expose
    var class_discount_id: Int = 0

    @ColumnInfo(name = "invoice_date")
    @SerializedName("invoice_date")
    @Expose
    var invoice_date: String? = null

    @ColumnInfo(name = "customer_id")
    @SerializedName("customer_id")
    @Expose
    var customer_id: Int = 0

    @ColumnInfo(name = "stock_id")
    @SerializedName("stock_id")
    @Expose
    var stock_id: Int = 0

    @ColumnInfo(name = "quantity")
    @SerializedName("quantity")
    @Expose
    var quantity: Int = 0

    @ColumnInfo(name = "paid_quantity")
    @SerializedName("paid_quantity")
    @Expose
    var paid_quantity: Int = 0

    @ColumnInfo(name = "sale_man_id")
    @SerializedName("sale_man_id")
    @Expose
    var sale_man_id: Int = 0

    @ColumnInfo(name = "delete_flag")
    @SerializedName("delete_flag")
    @Expose
    var delete_flag: String? = null
}
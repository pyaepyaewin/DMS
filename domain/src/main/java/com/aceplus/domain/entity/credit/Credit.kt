package com.aceplus.domain.entity.credit

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "credit")
class Credit {

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

    @ColumnInfo(name = "amount")
    @SerializedName("amount")
    @Expose
    var amount: Double = 0.0

    @ColumnInfo(name = "pay_amount")
    @SerializedName("pay_amount")
    @Expose
    var pay_amount: Double = 0.0

    @ColumnInfo(name = "first_pay_amount")
    @SerializedName("first_pay_amount")
    @Expose
    var first_pay_amount: Double = 0.0

    @ColumnInfo(name = "extra_amount")
    @SerializedName("extra_amount")
    @Expose
    var extra_amount: Double = 0.0

    @ColumnInfo(name = "refund")
    @SerializedName("refund")
    @Expose
    var refund: Double = 0.0

    @ColumnInfo(name = "sale_status")
    @SerializedName("sale_status")
    @Expose
    var sale_status: String? = null

    @ColumnInfo(name = "invoice_status")
    @SerializedName("invoice_status")
    @Expose
    var invoice_status: String? = null

    @ColumnInfo(name = "sale_man_id")
    @SerializedName("sale_man_id")
    @Expose
    var sale_man_id: String? = null
}
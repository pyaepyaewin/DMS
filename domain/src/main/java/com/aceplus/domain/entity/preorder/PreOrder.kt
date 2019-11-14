package com.aceplus.domain.entity.preorder

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "pre_order")
class PreOrder {

    @PrimaryKey
    @ColumnInfo(name = "invoice_id")
    @SerializedName("invoice_id")
    @Expose
    var invoice_id: String = "0"

    @ColumnInfo(name = "customer_id")
    @SerializedName("customer_id")
    @Expose
    var customer_id: String? = null

    @ColumnInfo(name = "customer_name")
    @SerializedName("customer_name")
    @Expose
    @Ignore var customer_name: String? = null

    @ColumnInfo(name = "sale_man_id")
    @SerializedName("sale_man_id")
    @Expose
    var sale_man_id: String? = null

    @ColumnInfo(name = "sale_man_name")
    @SerializedName("sale_man_name")
    @Expose
    @Ignore var sale_man_name: String? = null

    @ColumnInfo(name = "dev_id")
    @SerializedName("dev_id")
    @Expose
    var dev_id: String? = null

    @ColumnInfo(name = "pre_order_date")
    @SerializedName("pre_order_date")
    @Expose
    var pre_order_date: String? = null

    @ColumnInfo(name = "expected_delivery_date")
    @SerializedName("expected_delivery_date")
    @Expose
    var expected_delivery_date: String? = null

    @ColumnInfo(name = "advance_payment_amount")
    @SerializedName("advance_payment_amount")
    @Expose
    var advance_payment_amount: String? = null

    @ColumnInfo(name = "net_amount")
    @SerializedName("net_amount")
    @Expose
    var net_amount: String? = null

    @ColumnInfo(name = "location_id")
    @SerializedName("location_id")
    @Expose
    var location_id: String? = null

    @ColumnInfo(name = "discount")
    @SerializedName("discount")
    @Expose
    var discount: String? = null

    @ColumnInfo(name = "discount_percent")
    @SerializedName("discount_percent")
    @Expose
    var discount_percent: String? = null

    @ColumnInfo(name = "volume_discount")
    @SerializedName("volume_discount")
    @Expose
    var volume_discount: Double = 0.0

    @ColumnInfo(name = "volume_discount_percent")
    @SerializedName("volume_discount_percent")
    @Expose
    var volume_discount_percent: Double = 0.0

    @ColumnInfo(name = "tax_amount")
    @SerializedName("tax_amount")
    @Expose
    var tax_amount: String? = null

    @ColumnInfo(name = "bank_name")
    @SerializedName("bank_name")
    @Expose
    var bank_name: String? = null

    @ColumnInfo(name = "bank_account_no")
    @SerializedName("bank_account_no")
    @Expose
    var bank_account_no: String? = null

    @ColumnInfo(name = "remark")
    @SerializedName("remark")
    @Expose
    var remark: String? = null

    @ColumnInfo(name = "delete_flag")
    @SerializedName("delete_flag")
    @Expose
    var delete_flag: Int? = 0

    @ColumnInfo(name = "sale_flag")
    @SerializedName("sale_flag")
    @Expose
    var sale_flag: Int? = 0
}
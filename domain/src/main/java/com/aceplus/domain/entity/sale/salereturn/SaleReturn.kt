package com.aceplus.domain.entity.sale.salereturn

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "sale_return")
class SaleReturn {

    @PrimaryKey
    @ColumnInfo(name = "sale_return_id")
    @SerializedName("sale_return_id")
    @Expose
    var sale_return_id: Int = 0

    @ColumnInfo(name = "customer_id")
    @SerializedName("customer_id")
    @Expose
    var customer_id: Int = 0

    @ColumnInfo(name = "location_id")
    @SerializedName("location_id")
    @Expose
    var location_id: Int = 0

    @ColumnInfo(name = "amount")
    @SerializedName("amount")
    @Expose
    var amount: Double = 0.0

    @ColumnInfo(name = "pay_amount")
    @SerializedName("pay_amount")
    @Expose
    var pay_amount: Double = 0.0

    @ColumnInfo(name = "pc_address")
    @SerializedName("pc_address")
    @Expose
    var pc_address: String? = null

    @ColumnInfo(name = "return_date")
    @SerializedName("return_date")
    @Expose
    var return_date: String? = null

    @ColumnInfo(name = "rate")
    @SerializedName("rate")
    @Expose
    var rate: String? = null

    @ColumnInfo(name = "currency_id")
    @SerializedName("currency_id")
    @Expose
    var currency_id: Int = 0

    @ColumnInfo(name = "invoice_status")
    @SerializedName("invoice_status")
    @Expose
    var invoice_status: String? = null

    @ColumnInfo(name = "sale_man_id")
    @SerializedName("sale_man_id")
    @Expose
    var sale_man_id: Int = 0

    @ColumnInfo(name = "sale_id")
    @SerializedName("sale_id")
    @Expose
    var sale_id: Int = 0

    @ColumnInfo(name = "discount")
    @SerializedName("discount")
    @Expose
    var discount: Double = 0.0

    @ColumnInfo(name = "tax")
    @SerializedName("tax")
    @Expose
    var tax: Double = 0.0

    @ColumnInfo(name = "tax_percent")
    @SerializedName("tax_percent")
    @Expose
    var tax_percent: Double = 0.0

    @ColumnInfo(name = "delete_flag")
    @SerializedName("delete_flag")
    @Expose
    var delete_flag: String? = null
}
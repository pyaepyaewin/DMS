package com.aceplus.domain.entity.invoice

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "invoice_product")
class InvoiceProduct {

    @PrimaryKey
    @ColumnInfo(name = "invoice_product_id")
    @SerializedName("invoice_product_id")
    @Expose
    var invoice_product_id: Int = 0

    @ColumnInfo(name = "product_id")
    @SerializedName("product_id")
    @Expose
    var product_id: Int = 0

    @ColumnInfo(name = "sale_quantity")
    @SerializedName("sale_quantity")
    @Expose
    var sale_quantity: Double = 0.0

    @ColumnInfo(name = "discount_amount")
    @SerializedName("discount_amount")
    @Expose
    var discount_amount: Double = 0.0

    @ColumnInfo(name = "total_amount")
    @SerializedName("total_amount")
    @Expose
    var total_amount: Double = 0.0

    @ColumnInfo(name = "discount_percent")
    @SerializedName("discount_percent")
    @Expose
    var discount_percent: Double = 0.0

    @ColumnInfo(name = "extra_discount")
    @SerializedName("extra_discount")
    @Expose
    var extra_discount: Double = 0.0

    @ColumnInfo(name = "serial_id")
    @SerializedName("serial_id")
    @Expose
    var serial_id: Int = 0

    @ColumnInfo(name = "s_price")
    @SerializedName("s_price")
    @Expose
    var s_price: Double = 0.0

    @ColumnInfo(name = "p_price")
    @SerializedName("p_price")
    @Expose
    var p_price: Double = 0.0

    @ColumnInfo(name = "promotion_plan_id")
    @SerializedName("promotion_plan_id")
    @Expose
    var promotion_plan_id: Int = 0

    @ColumnInfo(name = "promotion_price")
    @SerializedName("promotion_price")
    @Expose
    var promotion_price: Double = 0.0

    @ColumnInfo(name = "volume_discount_percent")
    @SerializedName("volume_discount_percent")
    @Expose
    var volume_discount_percent: Double = 0.0

    @ColumnInfo(name = "item_discount_percent")
    @SerializedName("item_discount_percent")
    @Expose
    var item_discount_percent: Double = 0.0

    @ColumnInfo(name = "item_discount_amount")
    @SerializedName("item_discount_amount")
    @Expose
    var item_discount_amount: Double = 0.0

    @ColumnInfo(name = "exclude")
    @SerializedName("exclude")
    @Expose
    var exclude: String? = null
}
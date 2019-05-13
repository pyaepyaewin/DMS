package com.aceplus.domain.entity.preorder

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "pre_order_product")
class PreOrderProduct {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    var id: Int = 0

    @ColumnInfo(name = "sale_order_id")
    @SerializedName("sale_order_id")
    @Expose
    var sale_order_id: Int = 0

    @ColumnInfo(name = "product_id")
    @SerializedName("product_id")
    @Expose
    var product_id: Int = 0

    @ColumnInfo(name = "order_quantity")
    @SerializedName("order_quantity")
    @Expose
    var order_quantity: String? = null

    @ColumnInfo(name = "price")
    @SerializedName("price")
    @Expose
    var price: String? = null

    @ColumnInfo(name = "total_amount")
    @SerializedName("total_amount")
    @Expose
    var total_amount: String? = null

    @ColumnInfo(name = "promotion_price")
    @SerializedName("promotion_price")
    @Expose
    var promotion_price: String? = null

    @ColumnInfo(name = "promotion_plan_id")
    @SerializedName("promotion_plan_id")
    @Expose
    var promotion_plan_id: Int = 0

    @ColumnInfo(name = "volume_discount")
    @SerializedName("volume_discount")
    @Expose
    var volume_discount: String? = null

    @ColumnInfo(name = "volume_discount_percent")
    @SerializedName("volume_discount_percent")
    @Expose
    var volume_discount_percent: String? = null

    @ColumnInfo(name = "item_discount_percent")
    @SerializedName("item_discount_percent")
    @Expose
    var item_discount_percent: String? = null

    @ColumnInfo(name = "item_discount_amount")
    @SerializedName("item_discount_amount")
    @Expose
    var item_discount_amount: String? = null

    @ColumnInfo(name = "exclude")
    @SerializedName("exclude")
    @Expose
    var exclude: String? = null

    @ColumnInfo(name = "delete_flag")
    @SerializedName("delete_flag")
    @Expose
    var delete_flag: String? = null
}
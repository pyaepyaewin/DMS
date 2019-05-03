package com.aceplus.domain.entity.product

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "product")
class Product {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    var id: Int = 0

    @ColumnInfo(name = "product_id")
    @SerializedName("product_id")
    @Expose
    var product_id: Int = 0

    @ColumnInfo(name = "product_name")
    @SerializedName("product_name")
    @Expose
    var product_name: String? = null

    @ColumnInfo(name = "category_id")
    @SerializedName("category_id")
    @Expose
    var category_id: Int = 0

    @ColumnInfo(name = "group_id")
    @SerializedName("group_id")
    @Expose
    var group_id: Int = 0

    @ColumnInfo(name = "total_quantity")
    @SerializedName("total_quantity")
    @Expose
    var total_quantity: Int = 0

    @ColumnInfo(name = "remaining_quantity")
    @SerializedName("remaining_quantity")
    @Expose
    var remaining_quantity: Int = 0

    @ColumnInfo(name = "selling_price")
    @SerializedName("selling_price")
    @Expose
    var selling_price: Double = 0.0

    @ColumnInfo(name = "purchase_price")
    @SerializedName("purchase_price")
    @Expose
    var purchase_price: Double = 0.0

    @ColumnInfo(name = "discount_type")
    @SerializedName("discount_type")
    @Expose
    var discount_type: String? = null

    @ColumnInfo(name = "um")
    @SerializedName("um")
    @Expose
    var um: String? = null

    @ColumnInfo(name = "sold_quantity")
    @SerializedName("sold_quantity")
    @Expose
    var sold_quantity: Int = 0

    @ColumnInfo(name = "order_quantity")
    @SerializedName("order_quantity")
    @Expose
    var order_quantity: Int = 0

    @ColumnInfo(name = "exchange_quantity")
    @SerializedName("exchange_quantity")
    @Expose
    var exchange_quantity: Int = 0

    @ColumnInfo(name = "return_quantity")
    @SerializedName("return_quantity")
    @Expose
    var return_quantity: Int = 0

    @ColumnInfo(name = "delivery_quantity")
    @SerializedName("delivery_quantity")
    @Expose
    var delivery_quantity: Int = 0

    @ColumnInfo(name = "present_quantity")
    @SerializedName("present_quantity")
    @Expose
    var present_quantity: Int = 0

    @ColumnInfo(name = "device_issue_status")
    @SerializedName("device_issue_status")
    @Expose
    var device_issue_status: String? = null

    @ColumnInfo(name = "class_id")
    @SerializedName("class_id")
    @Expose
    var class_id: String? = null
}
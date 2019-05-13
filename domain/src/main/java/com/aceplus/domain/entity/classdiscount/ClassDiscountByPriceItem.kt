package com.aceplus.domain.entity.classdiscount

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "class_discount_by_price_item")
class ClassDiscountByPriceItem {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    var id: Int = 0

    @ColumnInfo(name = "class_discount_id")
    @SerializedName("class_discount_id")
    @Expose
    var class_discount_id: String? = null

    @ColumnInfo(name = "class_id")
    @SerializedName("class_id")
    @Expose
    var class_id: String? = null

    @ColumnInfo(name = "from_quantity")
    @SerializedName("from_quantity")
    @Expose
    var from_quantity: String? = null

    @ColumnInfo(name = "to_quantity")
    @SerializedName("to_quantity")
    @Expose
    var to_quantity: String? = null

    @ColumnInfo(name = "from_amount")
    @SerializedName("from_amount")
    @Expose
    var from_amount: String? = null

    @ColumnInfo(name = "to_amount")
    @SerializedName("to_amount")
    @Expose
    var to_amount: String? = null

    @ColumnInfo(name = "discount_percent")
    @SerializedName("discount_percent")
    @Expose
    var discount_percent: String? = null

    @ColumnInfo(name = "active")
    @SerializedName("active")
    @Expose
    var active: String? = null

    @ColumnInfo(name = "created_date")
    @SerializedName("created_date")
    @Expose
    var created_date: String? = null

    @ColumnInfo(name = "created_user_id")
    @SerializedName("created_user_id")
    @Expose
    var created_user_id: String? = null

    @ColumnInfo(name = "updated_date")
    @SerializedName("updated_date")
    @Expose
    var updated_date: String? = null

    @ColumnInfo(name = "updated_user_id")
    @SerializedName("updated_user_id")
    @Expose
    var updated_user_id: String? = null

    @ColumnInfo(name = "timestamp")
    @SerializedName("timestamp")
    @Expose
    var timestamp: String? = null
}
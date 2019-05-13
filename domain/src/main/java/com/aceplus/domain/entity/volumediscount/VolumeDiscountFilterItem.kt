package com.aceplus.domain.entity.volumediscount

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "volume_discount_filter_item")
class VolumeDiscountFilterItem {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    var id: Int = 0

    @ColumnInfo(name = "volume_discount_id")
    @SerializedName("volume_discount_id")
    @Expose
    var volume_discount_id: String? = null

    @ColumnInfo(name = "category_id")
    @SerializedName("category_id")
    @Expose
    var category_id: String? = null

    @ColumnInfo(name = "group_code_id")
    @SerializedName("group_code_id")
    @Expose
    var group_code_id: String? = null

    @ColumnInfo(name = "from_sale_amount")
    @SerializedName("from_sale_amount")
    @Expose
    var from_sale_amount: String? = null

    @ColumnInfo(name = "to_sale_amount")
    @SerializedName("to_sale_amount")
    @Expose
    var to_sale_amount: String? = null

    @ColumnInfo(name = "discount_percent")
    @SerializedName("discount_percent")
    @Expose
    var discount_percent: String? = null

    @ColumnInfo(name = "discount_amount")
    @SerializedName("discount_amount")
    @Expose
    var discount_amount: Double = 0.0

    @ColumnInfo(name = "discount_price")
    @SerializedName("discount_price")
    @Expose
    var discount_price: Double = 0.0

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
    var created_user_id: Int = 0

    @ColumnInfo(name = "updated_date")
    @SerializedName("updated_date")
    @Expose
    var updated_date: String? = null

    @ColumnInfo(name = "updated_user_id")
    @SerializedName("updated_user_id")
    @Expose
    var updated_user_id: Int = 0

    @ColumnInfo(name = "timestamp")
    @SerializedName("timestamp")
    @Expose
    var timestamp: String? = null
}
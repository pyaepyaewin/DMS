package com.aceplus.domain.entity.promotion

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "promotion_gift_item")
class PromotionGiftItem {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    var id: Int = 0

    @ColumnInfo(name = "promotion_plan_id")
    @SerializedName("promotion_plan_id")
    @Expose
    var promotion_plan_id: Int = 0

    @ColumnInfo(name = "stock_id")
    @SerializedName("stock_id")
    @Expose
    var stock_id: Int = 0

    @ColumnInfo(name = "quantity")
    @SerializedName("quantity")
    @Expose
    var quantity: Double = 0.0

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
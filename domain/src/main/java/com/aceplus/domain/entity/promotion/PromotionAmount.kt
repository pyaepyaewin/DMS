package com.aceplus.domain.entity.promotion

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "promotion_amount")
class PromotionAmount {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    var id: Int = 0

    @ColumnInfo(name = "promotion_id")
    @SerializedName("promotion_id")
    @Expose
    var promotion_id: Int = 0

    @ColumnInfo(name = "product_id")
    @SerializedName("product_id")
    @Expose
    var product_id: Int = 0

    @ColumnInfo(name = "from_date")
    @SerializedName("from_date")
    @Expose
    var from_date: String? = null

    @ColumnInfo(name = "to_date")
    @SerializedName("to_date")
    @Expose
    var to_date: String? = null

    @ColumnInfo(name = "from_amount")
    @SerializedName("from_amount")
    @Expose
    var from_amount: Double = 0.0

    @ColumnInfo(name = "to_amount")
    @SerializedName("to_amount")
    @Expose
    var to_amount: Double = 0.0

    @ColumnInfo(name = "quantity")
    @SerializedName("quantity")
    @Expose
    var quantity: Int = 0

    @ColumnInfo(name = "discount_amount")
    @SerializedName("discount_amount")
    @Expose
    var discount_amount: Double = 0.0
}
package com.aceplus.domain.entity.promotion

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "promotion")
class Promotion {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    var id: Int = 0

    @ColumnInfo(name = "name")
    @SerializedName("name")
    @Expose
    var name: String? = null

    @ColumnInfo(name = "buy_product_id")
    @SerializedName("buy_product_id")
    @Expose
    var buy_product_id: Int = 0

    @ColumnInfo(name = "buy_quantity")
    @SerializedName("buy_quantity")
    @Expose
    var buy_quantity: Double = 0.0

    @ColumnInfo(name = "buy_amount")
    @SerializedName("buy_amount")
    @Expose
    var buy_amount: Double = 0.0

    @ColumnInfo(name = "product_or_gift")
    @SerializedName("product_or_gift")
    @Expose
    var product_or_gift: String? = null

    @ColumnInfo(name = "promotion_product_id")
    @SerializedName("promotion_product_id")
    @Expose
    var promotion_product_id: Int = 0

    @ColumnInfo(name = "promotion_quantity")
    @SerializedName("promotion_quantity")
    @Expose
    var promotion_quantity: Int = 0

}
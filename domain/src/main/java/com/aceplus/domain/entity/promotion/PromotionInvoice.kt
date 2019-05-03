package com.aceplus.domain.entity.promotion

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "promotion_invoice")
class PromotionInvoice {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    var id: Int = 0

    @ColumnInfo(name = "promotion_product_id")
    @SerializedName("promotion_product_id")
    @Expose
    var promotion_product_id: Int = 0

    @ColumnInfo(name = "promotion_quantity")
    @SerializedName("promotion_quantity")
    @Expose
    var promotion_quantity: Double = 0.0

}
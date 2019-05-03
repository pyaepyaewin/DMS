package com.aceplus.domain.entity.delivery

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "delivery_item_upload")
class DeliveryItemUpload {

    @ColumnInfo(name = "delivery_id")
    @SerializedName("delivery_id")
    @Expose
    var delivery_id: Int = 0

    @ColumnInfo(name = "stock_id")
    @SerializedName("stock_id")
    @Expose
    var stock_id: Int = 0

    @ColumnInfo(name = "quantity")
    @SerializedName("quantity")
    @Expose
    var quantity: String? = null

    @ColumnInfo(name = "foc")
    @SerializedName("foc")
    @Expose
    var foc: String? = null

}
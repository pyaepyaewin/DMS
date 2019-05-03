package com.aceplus.domain.entity.delivery

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "delivery_present")
class DeliveryPresent {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    var id: Int = 0

    @ColumnInfo(name = "sale_order_id")
    @SerializedName("sale_order_id")
    @Expose
    var sale_order_id: Int = 0

    @ColumnInfo(name = "stock_id")
    @SerializedName("stock_id")
    @Expose
    var stock_id: Int = 0

    @ColumnInfo(name = "quantity")
    @SerializedName("quantity")
    @Expose
    var quantity: Double = 0.0

    @ColumnInfo(name = "delivery_flag")
    @SerializedName("delivery_flag")
    @Expose
    var delivery_flag: String? = null

}
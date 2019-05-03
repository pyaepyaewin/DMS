package com.aceplus.domain.entity.delivery

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "delivery_item")
class DeliveryItem {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    var id: Int = 0

    @ColumnInfo(name = "delivery_id")
    @SerializedName("delivery_id")
    @Expose
    var delivery_id: Int = 0

    @ColumnInfo(name = "stock_id")
    @SerializedName("stock_id")
    @Expose
    var stock_id: Int = 0

    @ColumnInfo(name = "order_quantity")
    @SerializedName("order_quantity")
    @Expose
    var order_quantity: Double = 0.0

    @ColumnInfo(name = "received_quantity")
    @SerializedName("received_quantity")
    @Expose
    var received_quantity: Double = 0.0

    @ColumnInfo(name = "s_price")
    @SerializedName("s_price")
    @Expose
    var s_price: Double = 0.0

    @ColumnInfo(name = "foc_status")
    @SerializedName("foc_status")
    @Expose
    var foc_status: String? = null

    @ColumnInfo(name = "delivery_flag")
    @SerializedName("delivery_flag")
    @Expose
    var delivery_flag: String? = null
}
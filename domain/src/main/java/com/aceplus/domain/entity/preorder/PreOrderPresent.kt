package com.aceplus.domain.entity.preorder

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "pre_order_present")
class PreOrderPresent {

    @ColumnInfo(name = "pre_order_id")
    @SerializedName("pre_order_id")
    @Expose
    var pre_order_id: Int = 0

    @ColumnInfo(name = "stock_id")
    @SerializedName("stock_id")
    @Expose
    var stock_id: Int = 0

    @ColumnInfo(name = "quantity")
    @SerializedName("quantity")
    @Expose
    var quantity: Double = 0.0

    @ColumnInfo(name = "pc_address")
    @SerializedName("pc_address")
    @Expose
    var pc_address: String? = null

    @ColumnInfo(name = "location_id")
    @SerializedName("location_id")
    @Expose
    var location_id: Int = 0

    @ColumnInfo(name = "price")
    @SerializedName("price")
    @Expose
    var price: Double = 0.0

    @ColumnInfo(name = "delete_flag")
    @SerializedName("delete_flag")
    @Expose
    var delete_flag: String? = null

    @ColumnInfo(name = "status")
    @SerializedName("status")
    @Expose
    var status: String? = null
}
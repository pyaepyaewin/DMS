package com.aceplus.domain.entity.outlet

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "outlet_stock_availability_detail")
class OutletStockAvailabilityDetail {

    @PrimaryKey
    @ColumnInfo(name = "outlet_stock_availability_id")
    @SerializedName("outlet_stock_availability_id")
    @Expose
    var outlet_stock_availability_id: Int = 0

    @ColumnInfo(name = "product_id")
    @SerializedName("product_id")
    @Expose
    var product_id: Int = 0

    @ColumnInfo(name = "quantity")
    @SerializedName("quantity")
    @Expose
    var quantity: Double = 0.0

}
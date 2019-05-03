package com.aceplus.domain.entity.incentive

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "incentive_item")
class IncentiveItem {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    var id: Int = 0

    @ColumnInfo(name = "display_program_id")
    @SerializedName("display_program_id")
    @Expose
    var display_program_id: Int = 0

    @ColumnInfo(name = "incentive_id")
    @SerializedName("incentive_id")
    @Expose
    var incentive_id: Int = 0

    @ColumnInfo(name = "stock_id")
    @SerializedName("stock_id")
    @Expose
    var stock_id: Int = 0

    @ColumnInfo(name = "quantity")
    @SerializedName("quantity")
    @Expose
    var quantity: Double = 0.0

    @ColumnInfo(name = "price")
    @SerializedName("price")
    @Expose
    var price: Double = 0.0

}
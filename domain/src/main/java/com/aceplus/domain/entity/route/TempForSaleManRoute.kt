package com.aceplus.domain.entity.route

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "temp_for_sale_man_route")
class TempForSaleManRoute {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    var id: Int = 0

    @ColumnInfo(name = "route_id")
    @SerializedName("route_id")
    @Expose
    var route_id: Int = 0

    @ColumnInfo(name = "sale_man_id")
    @SerializedName("sale_man_id")
    @Expose
    var sale_man_id: Int = 0

    @ColumnInfo(name = "customer_id")
    @SerializedName("customer_id")
    @Expose
    var customer_id: Int = 0

    @ColumnInfo(name = "latitude")
    @SerializedName("latitude")
    @Expose
    var latitude: String? = null

    @ColumnInfo(name = "longitude")
    @SerializedName("longitude")
    @Expose
    var longitude: String? = null

    @ColumnInfo(name = "arrival_time")
    @SerializedName("arrival_time")
    @Expose
    var arrival_time: String? = null

    @ColumnInfo(name = "added_time")
    @SerializedName("added_time")
    @Expose
    var added_time: String? = null

    @ColumnInfo(name = "departure_time")
    @SerializedName("departure_time")
    @Expose
    var departure_time: String? = null
}
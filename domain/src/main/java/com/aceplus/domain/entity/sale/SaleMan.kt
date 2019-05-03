package com.aceplus.domain.entity.sale

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "sale_man")
class SaleMan {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    var id: Int = 0

    @ColumnInfo(name = "user_id")
    @SerializedName("user_id")
    @Expose
    var user_id: Int = 0

    @ColumnInfo(name = "user_name")
    @SerializedName("user_name")
    @Expose
    var user_name: String? = null

    @ColumnInfo(name = "password")
    @SerializedName("password")
    @Expose
    var password: String? = null

    @ColumnInfo(name = "location_code")
    @SerializedName("location_code")
    @Expose
    var location_code: String? = null

    @ColumnInfo(name = "latitude")
    @SerializedName("latitude")
    @Expose
    var latitude: String? = null

    @ColumnInfo(name = "longitude")
    @SerializedName("longitude")
    @Expose
    var longitude: String? = null

}
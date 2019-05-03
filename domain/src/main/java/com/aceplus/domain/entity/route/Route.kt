package com.aceplus.domain.entity.route

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "route")
class Route {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    var id: Int = 0

    @ColumnInfo(name = "route_no")
    @SerializedName("route_no")
    @Expose
    var route_no: String? = null

    @ColumnInfo(name = "route_name")
    @SerializedName("route_name")
    @Expose
    var route_name: String? = null

    @ColumnInfo(name = "active")
    @SerializedName("active")
    @Expose
    var active: String? = null

    @ColumnInfo(name = "created_date")
    @SerializedName("created_date")
    @Expose
    var created_date: String? = null

    @ColumnInfo(name = "created_user_id")
    @SerializedName("created_user_id")
    @Expose
    var created_user_id: Int = 0

    @ColumnInfo(name = "updated_date")
    @SerializedName("updated_date")
    @Expose
    var updated_date: String? = null

    @ColumnInfo(name = "updated_user_id")
    @SerializedName("updated_user_id")
    @Expose
    var updated_user_id: Int = 0

    @ColumnInfo(name = "timestamp")
    @SerializedName("timestamp")
    @Expose
    var timestamp: String? = null
}
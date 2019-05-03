package com.aceplus.domain.entity.route

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "route_schedule")
class RouteSchedule {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    var id: Int = 0

    @ColumnInfo(name = "schedule_no")
    @SerializedName("schedule_no")
    @Expose
    var schedule_no: Int = 0

    @ColumnInfo(name = "date")
    @SerializedName("date")
    @Expose
    var date: String? = null

    @ColumnInfo(name = "from_date")
    @SerializedName("from_date")
    @Expose
    var from_date: String? = null

    @ColumnInfo(name = "to_date")
    @SerializedName("to_date")
    @Expose
    var to_date: String? = null

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
    var class_created_user_idid: Int = 0

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
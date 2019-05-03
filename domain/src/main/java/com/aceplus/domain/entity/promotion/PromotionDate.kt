package com.aceplus.domain.entity.promotion

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "promotion_date")
class PromotionDate {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    var id: Int = 0

    @ColumnInfo(name = "promotion_plan_id")
    @SerializedName("promotion_plan_id")
    @Expose
    var promotion_plan_id: Int = 0

    @ColumnInfo(name = "date")
    @SerializedName("date")
    @Expose
    var date: String? = null

    @ColumnInfo(name = "promotion_date")
    @SerializedName("promotion_date")
    @Expose
    var promotion_date: String? = null

    @ColumnInfo(name = "s_hour")
    @SerializedName("s_hour")
    @Expose
    var s_hour: String? = null

    @ColumnInfo(name = "s_minute")
    @SerializedName("s_minute")
    @Expose
    var s_minute: String? = null

    @ColumnInfo(name = "s_shift")
    @SerializedName("s_shift")
    @Expose
    var s_shift: String? = null

    @ColumnInfo(name = "e_hour")
    @SerializedName("e_hour")
    @Expose
    var e_hour: String? = null

    @ColumnInfo(name = "e_minute")
    @SerializedName("e_minute")
    @Expose
    var e_minute: String? = null

    @ColumnInfo(name = "e_shift")
    @SerializedName("e_shift")
    @Expose
    var e_shift: String? = null

    @ColumnInfo(name = "process_status")
    @SerializedName("process_status")
    @Expose
    var process_status: String? = null

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
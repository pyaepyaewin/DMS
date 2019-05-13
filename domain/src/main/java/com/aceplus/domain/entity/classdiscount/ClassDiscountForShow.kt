package com.aceplus.domain.entity.classdiscount

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "class_discount_for_show")
class ClassDiscountForShow {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    var id: Int = 0

    @ColumnInfo(name = "class_discount_no")
    @SerializedName("class_discount_no")
    @Expose
    var class_discount_no: String? = null

    @ColumnInfo(name = "date")
    @SerializedName("date")
    @Expose
    var date: String? = null

    @ColumnInfo(name = "start_date")
    @SerializedName("start_date")
    @Expose
    var start_date: String? = null

    @ColumnInfo(name = "end_date")
    @SerializedName("end_date")
    @Expose
    var end_date: String? = null

    @ColumnInfo(name = "sunday")
    @SerializedName("sunday")
    @Expose
    var sunday: String? = null

    @ColumnInfo(name = "monday")
    @SerializedName("monday")
    @Expose
    var monday: String? = null

    @ColumnInfo(name = "tuesday")
    @SerializedName("tuesday")
    @Expose
    var tuesday: String? = null

    @ColumnInfo(name = "wednesday")
    @SerializedName("wednesday")
    @Expose
    var wednesday: String? = null

    @ColumnInfo(name = "thursday")
    @SerializedName("thursday")
    @Expose
    var thursday: String? = null

    @ColumnInfo(name = "friday")
    @SerializedName("friday")
    @Expose
    var friday: String? = null

    @ColumnInfo(name = "saturday")
    @SerializedName("saturday")
    @Expose
    var saturday: String? = null

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

    @ColumnInfo(name = "discount_type")
    @SerializedName("discount_type")
    @Expose
    var discount_type: String? = null

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
    var created_user_id: String? = null

    @ColumnInfo(name = "updated_date")
    @SerializedName("updated_date")
    @Expose
    var updated_date: String? = null

    @ColumnInfo(name = "updated_user_id")
    @SerializedName("updated_user_id")
    @Expose
    var updated_user_id: String? = null

    @ColumnInfo(name = "timestamp")
    @SerializedName("timestamp")
    @Expose
    var timestamp: String? = null
}
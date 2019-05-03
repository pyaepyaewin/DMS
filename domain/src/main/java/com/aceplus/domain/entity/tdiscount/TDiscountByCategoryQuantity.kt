package com.aceplus.domain.entity.tdiscount

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "t_discount_by_category_quantity")
class TDiscountByCategoryQuantity {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    var id: Int = 0

    @ColumnInfo(name = "promotion_plan_no")
    @SerializedName("promotion_plan_no")
    @Expose
    var promotion_plan_no: Int = 0

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

    @ColumnInfo(name = "promotion_type")
    @SerializedName("promotion_type")
    @Expose
    var promotion_type: String? = null

    @ColumnInfo(name = "description")
    @SerializedName("description")
    @Expose
    var description: String? = null

    @ColumnInfo(name = "category_id")
    @SerializedName("category_id")
    @Expose
    var category_id: Int = 0

    @ColumnInfo(name = "currency_id")
    @SerializedName("currency_id")
    @Expose
    var currency_id: Int = 0

    @ColumnInfo(name = "um_id")
    @SerializedName("um_id")
    @Expose
    var classum_id_id: Int = 0

    @ColumnInfo(name = "active")
    @SerializedName("active")
    @Expose
    var active: String? = null

    @ColumnInfo(name = "approved_date")
    @SerializedName("approved_date")
    @Expose
    var approved_date: String? = null

    @ColumnInfo(name = "approved_user_id")
    @SerializedName("approved_user_id")
    @Expose
    var approved_user_id: Int = 0

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
package com.aceplus.domain.entity.competitor

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "competitor")
class Competitor {

    @ColumnInfo(name = "competitor_id")
    @SerializedName("competitor_id")
    @Expose
    var competitor_id: Int = 0

    @ColumnInfo(name = "competitor_name")
    @SerializedName("competitor_name")
    @Expose
    var competitor_name: String? = null

    @ColumnInfo(name = "customer_id")
    @SerializedName("customer_id")
    @Expose
    var customer_id: Int = 0

    @ColumnInfo(name = "note")
    @SerializedName("note")
    @Expose
    var note: String? = null

    @ColumnInfo(name = "image_list")
    @SerializedName("image_list")
    @Expose
    var image_list: List<String>? = ArrayList()
}
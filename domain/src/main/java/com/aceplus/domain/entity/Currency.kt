package com.aceplus.domain.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "currency")
class Currency {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    var id: Int = 0

    @ColumnInfo(name = "currency")
    @SerializedName("currency")
    @Expose
    var currency: String? = null

    @ColumnInfo(name = "description")
    @SerializedName("description")
    @Expose
    var description: String? = null

    @ColumnInfo(name = "coupon_status")
    @SerializedName("coupon_status")
    @Expose
    var coupon_status: String? = null

}
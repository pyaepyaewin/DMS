package com.aceplus.domain.entity.sale

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "sale_channel")
class SaleChannel {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    var id: Int = 0

    @ColumnInfo(name = "sale_channel_no")
    @SerializedName("sale_channel_no")
    @Expose
    var sale_channel_no: Int = 0

    @ColumnInfo(name = "sale_channel_name")
    @SerializedName("sale_channel_name")
    @Expose
    var sale_channel_name: String? = null


}
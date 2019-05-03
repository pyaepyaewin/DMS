package com.aceplus.domain.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "shop_type")
class ShopType {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    var id: Int = 0

    @ColumnInfo(name = "shop_type_no")
    @SerializedName("shop_type_no")
    @Expose
    var shop_type_no: Int = 0

    @ColumnInfo(name = "shop_type_name")
    @SerializedName("shop_type_name")
    @Expose
    var shop_type_name: String? = null


}
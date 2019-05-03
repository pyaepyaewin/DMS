package com.aceplus.domain.entity.sizeinstoreshare

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "size_in_store_share_detail")
class SizeInStoreShareDetail {

    @ColumnInfo(name = "size_in_store_share_id")
    @SerializedName("size_in_store_share_id")
    @Expose
    var size_in_store_share_id: Int = 0

    @ColumnInfo(name = "product_id")
    @SerializedName("product_id")
    @Expose
    var product_id: Int = 0

    @ColumnInfo(name = "size_in_store_share_amount")
    @SerializedName("size_in_store_share_amount")
    @Expose
    var size_in_store_share_amount: Double = 0.0

}
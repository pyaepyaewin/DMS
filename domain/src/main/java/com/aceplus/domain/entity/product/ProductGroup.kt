package com.aceplus.domain.entity.product

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "product_group")
class ProductGroup {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    var id: Int = 0

    @ColumnInfo(name = "group_id")
    @SerializedName("group_id")
    @Expose
    var group_id: Int = 0

    @ColumnInfo(name = "group_name")
    @SerializedName("group_name")
    @Expose
    var group_name: String? = null

}
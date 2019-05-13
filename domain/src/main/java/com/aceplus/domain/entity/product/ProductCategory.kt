package com.aceplus.domain.entity.product

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "product_category")
class ProductCategory {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    var id: Int = 0

    @ColumnInfo(name = "category_id")
    @SerializedName("category_id")
    @Expose
    var category_id: String? = null

    @ColumnInfo(name = "category_name")
    @SerializedName("category_name")
    @Expose
    var category_name: String? = null

    @ColumnInfo(name = "category_no")
    @SerializedName("category_no")
    @Expose
    var category_no: String? = null


}
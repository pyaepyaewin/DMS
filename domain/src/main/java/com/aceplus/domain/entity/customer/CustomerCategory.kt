package com.aceplus.domain.entity.customer

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "customer_category")
class CustomerCategory {

    @ColumnInfo(name = "customer_category_id")
    @SerializedName("customer_category_id")
    @Expose
    var customer_category_id: Int = 0

    @ColumnInfo(name = "customer_category_name")
    @SerializedName("customer_category_name")
    @Expose
    var customer_category_name: String? = null
}
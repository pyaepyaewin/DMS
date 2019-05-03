package com.aceplus.domain.entity.outlet

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "outlet_external_check")
class OutletExternalCheck {

    @PrimaryKey
    @ColumnInfo(name = "outlet_external_check_id")
    @SerializedName("outlet_external_check_id")
    @Expose
    var outlet_external_check_id: Int = 0

    @ColumnInfo(name = "customer_id")
    @SerializedName("customer_id")
    @Expose
    var customer_id: Int = 0

    @ColumnInfo(name = "date")
    @SerializedName("date")
    @Expose
    var date: String? = null

}
package com.aceplus.domain.entity.customer

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "customer_balance")
class CustomerBalance {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    var id: Int = 0

    @ColumnInfo(name = "customer_id")
    @SerializedName("customer_id")
    @Expose
    var customer_id: Int = 0

    @ColumnInfo(name = "currency_id")
    @SerializedName("currency_id")
    @Expose
    var currency_id: Int = 0

    @ColumnInfo(name = "opening_balance")
    @SerializedName("opening_balance")
    @Expose
    var opening_balance: Double = 0.0

    @ColumnInfo(name = "balance")
    @SerializedName("balance")
    @Expose
    var balance: Double = 0.0

}
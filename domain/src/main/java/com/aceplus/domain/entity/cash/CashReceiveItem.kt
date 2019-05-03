package com.aceplus.domain.entity.cash

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "cash_receive_item")
class CashReceiveItem {

    @ColumnInfo(name = "receive_no")
    @SerializedName("receive_no")
    @Expose
    var receive_no: String? = null

    @ColumnInfo(name = "sale_id")
    @SerializedName("sale_id")
    @Expose
    var sale_id: String? = null
}
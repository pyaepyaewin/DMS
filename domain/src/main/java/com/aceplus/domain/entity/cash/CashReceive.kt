package com.aceplus.domain.entity.cash

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "cash_receive")
class CashReceive {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    var id: Int = 0

    @ColumnInfo(name = "receive_no")
    @SerializedName("receive_no")
    @Expose
    var receive_no: String? = null

    @ColumnInfo(name = "receive_date")
    @SerializedName("receive_date")
    @Expose
    var receive_date: String? = null

    @ColumnInfo(name = "customer_id")
    @SerializedName("customer_id")
    @Expose
    var customer_id: String? = null

    @ColumnInfo(name = "amount")
    @SerializedName("amount")
    @Expose
    var amount: String? = null

    @ColumnInfo(name = "currency_id")
    @SerializedName("currency_id")
    @Expose
    var currency_id: String? = null

    @ColumnInfo(name = "status")
    @SerializedName("status")
    @Expose
    var status: String? = null

    @ColumnInfo(name = "location_id")
    @SerializedName("location_id")
    @Expose
    var location_id: String? = null

    @ColumnInfo(name = "payment_type")
    @SerializedName("payment_type")
    @Expose
    var payment_type: String? = null

    @ColumnInfo(name = "cash_receive_type")
    @SerializedName("cash_receive_type")
    @Expose
    var cash_receive_type: String? = null

    @ColumnInfo(name = "sale_id")
    @SerializedName("sale_id")
    @Expose
    var sale_id: String? = null

    @ColumnInfo(name = "sale_man_id")
    @SerializedName("sale_man_id")
    @Expose
    var sale_man_id: String? = null

}

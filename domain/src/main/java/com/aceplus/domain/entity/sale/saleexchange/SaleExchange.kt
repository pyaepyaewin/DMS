package com.aceplus.domain.entity.sale.saleexchange

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "sale_exchange")
class SaleExchange {

    @ColumnInfo(name = "sale_exchange_id")
    @SerializedName("sale_exchange_id")
    @Expose
    var sale_exchange_id: Int = 0

    @ColumnInfo(name = "sender_sale_man_code")
    @SerializedName("sender_sale_man_code")
    @Expose
    var sender_sale_man_code: String? = null

    @ColumnInfo(name = "receiver_sale_man_code")
    @SerializedName("receiver_sale_man_code")
    @Expose
    var receiver_sale_man_code: String? = null

    @ColumnInfo(name = "send_or_receive")
    @SerializedName("send_or_receive")
    @Expose
    var send_or_receive: String? = null

    @ColumnInfo(name = "date")
    @SerializedName("date")
    @Expose
    var date: String? = null

}
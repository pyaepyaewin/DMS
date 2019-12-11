package com.aceplus.domain.entity.invoice

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "invoice_present")
class InvoicePresent {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    var id: Int = 0

    @ColumnInfo(name = "tsale_id")
    @SerializedName("tsale_id")
    @Expose
    var tsale_id: String? = null

    @ColumnInfo(name = "stock_id")
    @SerializedName("stock_id")
    @Expose
    var stock_id: Int = 0

    @ColumnInfo(name = "quantity")
    @SerializedName("quantity")
    @Expose
    var quantity: Double = 0.0

    @ColumnInfo(name = "pc_address")
    @SerializedName("pc_address")
    @Expose
    var pc_address: String? = null

    @ColumnInfo(name = "location_id")
    @SerializedName("location_id")
    @Expose
    var location_id: Int = 0

    @ColumnInfo(name = "price")
    @SerializedName("price")
    @Expose
    var price: Double = 0.0

    @ColumnInfo(name = "rate")
    @SerializedName("rate")
    @Expose
    var rate: String? = null

    @ColumnInfo(name = "currency_id")
    @SerializedName("currency_id")
    @Expose
    var currency_id: Int = 0

    @ColumnInfo(name = "p_price")
    @SerializedName("p_price")
    @Expose
    var p_price: Double = 0.0
}
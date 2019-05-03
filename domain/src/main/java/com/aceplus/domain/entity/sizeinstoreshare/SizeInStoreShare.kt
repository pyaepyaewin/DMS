package com.aceplus.domain.entity.sizeinstoreshare

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "size_in_store_share")
class SizeInStoreShare {

    @PrimaryKey
    @ColumnInfo(name = "size_in_store_share_id")
    @SerializedName("size_in_store_share_id")
    @Expose
    var size_in_store_share_id: Int = 0

    @ColumnInfo(name = "customer_id")
    @SerializedName("customer_id")
    @Expose
    var customer_id: Int = 0

    @ColumnInfo(name = "invoice_date")
    @SerializedName("invoice_date")
    @Expose
    var invoice_date: String? = null

    @ColumnInfo(name = "stock_id")
    @SerializedName("stock_id")
    @Expose
    var stock_id: Int = 0

    @ColumnInfo(name = "quantity")
    @SerializedName("quantity")
    @Expose
    var quantity: Int = 0

    @ColumnInfo(name = "status")
    @SerializedName("status")
    @Expose
    var status: String? = null

    @ColumnInfo(name = "remark")
    @SerializedName("remark")
    @Expose
    var remark: String? = null

    @ColumnInfo(name = "sale_man_id")
    @SerializedName("sale_man_id")
    @Expose
    var sale_man_id: Int = 0

    @ColumnInfo(name = "delete_flag")
    @SerializedName("delete_flag")
    @Expose
    var delete_flag: String? = null

}
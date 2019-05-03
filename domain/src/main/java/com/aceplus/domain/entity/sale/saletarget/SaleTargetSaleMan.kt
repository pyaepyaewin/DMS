package com.aceplus.domain.entity.sale.saletarget

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "sale_target_sale_man")
class SaleTargetSaleMan {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    var id: Int = 0

    @ColumnInfo(name = "sale_man_id")
    @SerializedName("sale_man_id")
    @Expose
    var sale_man_id: Int = 0

    @ColumnInfo(name = "category_id")
    @SerializedName("category_id")
    @Expose
    var category_id: Int = 0

    @ColumnInfo(name = "group_code_id")
    @SerializedName("group_code_id")
    @Expose
    var group_code_id: Int = 0

    @ColumnInfo(name = "stock_id")
    @SerializedName("stock_id")
    @Expose
    var stock_id: Int = 0

    @ColumnInfo(name = "invoice_no")
    @SerializedName("invoice_no")
    @Expose
    var invoice_no: String? = null

    @ColumnInfo(name = "target_amount")
    @SerializedName("target_amount")
    @Expose
    var target_amount: Double = 0.0

    @ColumnInfo(name = "from_date")
    @SerializedName("from_date")
    @Expose
    var from_date: String? = null

    @ColumnInfo(name = "to_date")
    @SerializedName("to_date")
    @Expose
    var to_date: String? = null

    @ColumnInfo(name = "date")
    @SerializedName("date")
    @Expose
    var date: String? = null

    @ColumnInfo(name = "day")
    @SerializedName("day")
    @Expose
    var day: String? = null

    @ColumnInfo(name = "date_status")
    @SerializedName("date_status")
    @Expose
    var date_status: String? = null

}
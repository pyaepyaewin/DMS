package com.aceplus.domain.entity.sale.salevisit

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "sale_visit_record_download")
class SaleVisitRecordDownload {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    var id: Int = 0

    @ColumnInfo(name = "sale_man_id")
    @SerializedName("sale_man_id")
    @Expose
    var sale_man_id: Int = 0

    @ColumnInfo(name = "customer_id")
    @SerializedName("customer_id")
    @Expose
    var customer_id: Int = 0

    @ColumnInfo(name = "latitude")
    @SerializedName("latitude")
    @Expose
    var latitude: String? = null

    @ColumnInfo(name = "longitude")
    @SerializedName("longitude")
    @Expose
    var longitude: String? = null

    @ColumnInfo(name = "record_date")
    @SerializedName("record_date")
    @Expose
    var record_date: String? = null

    @ColumnInfo(name = "visit_flag")
    @SerializedName("visit_flag")
    @Expose
    var visit_flag: String? = null

    @ColumnInfo(name = "sale_flag")
    @SerializedName("sale_flag")
    @Expose
    var sale_flag: String? = null

}
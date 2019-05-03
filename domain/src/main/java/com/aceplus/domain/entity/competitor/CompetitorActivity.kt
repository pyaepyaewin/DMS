package com.aceplus.domain.entity.competitor

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "competitor_activity")
class CompetitorActivity {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    var id: Int = 0

    @ColumnInfo(name = "customer_id")
    @SerializedName("customer_id")
    @Expose
    var customer_id: Int = 0

    @ColumnInfo(name = "competitor_name")
    @SerializedName("competitor_name")
    @Expose
    var competitor_name: String? = null

    @ColumnInfo(name = "activity")
    @SerializedName("activity")
    @Expose
    var activity: String? = null

    @ColumnInfo(name = "invoice_date")
    @SerializedName("invoice_date")
    @Expose
    var invoice_date: String? = null

    @ColumnInfo(name = "sale_man_id")
    @SerializedName("sale_man_id")
    @Expose
    var sale_man_id: String? = null

}
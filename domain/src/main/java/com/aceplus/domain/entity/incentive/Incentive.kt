package com.aceplus.domain.entity.incentive

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "incentive")
class Incentive {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    var id: Int = 0

    @ColumnInfo(name = "invoice_no")
    @SerializedName("invoice_no")
    @Expose
    var invoice_no: Int = 0

    @ColumnInfo(name = "invoice_date")
    @SerializedName("invoice_date")
    @Expose
    var invoice_date: String? = null

    @ColumnInfo(name = "standard_external_check_id")
    @SerializedName("standard_external_check_id")
    @Expose
    var standard_external_check_id: Int = 0

    @ColumnInfo(name = "outlet_visibility_id")
    @SerializedName("outlet_visibility_id")
    @Expose
    var outlet_visibility_id: Int = 0

    @ColumnInfo(name = "customer_id")
    @SerializedName("customer_id")
    @Expose
    var customer_id: Int = 0

    @ColumnInfo(name = "sale_man_id")
    @SerializedName("sale_man_id")
    @Expose
    var sale_man_id: Int = 0

    @ColumnInfo(name = "incentive_program_name")
    @SerializedName("incentive_program_name")
    @Expose
    var incentive_program_name: String? = null

}
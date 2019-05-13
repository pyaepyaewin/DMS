package com.aceplus.domain.entity.invoice

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "invoice")
class Invoice {

    @PrimaryKey
    @ColumnInfo(name = "invoice_id")
    @SerializedName("invoice_id")
    @Expose
    var invoice_id: Int = 0

    @ColumnInfo(name = "customer_id")
    @SerializedName("customer_id")
    @Expose
    var customer_id: Int = 0

    @ColumnInfo(name = "sale_date")
    @SerializedName("sale_date")
    @Expose
    var sale_date: String? = null

    @ColumnInfo(name = "total_amount")
    @SerializedName("total_amount")
    @Expose
    var total_amount: Double = 0.0

    @ColumnInfo(name = "total_discount_amount")
    @SerializedName("total_discount_amount")
    @Expose
    var total_discount_amount: Double = 0.0

    @ColumnInfo(name = "pay_amount")
    @SerializedName("pay_amount")
    @Expose
    var pay_amount: Double = 0.0

    @ColumnInfo(name = "refund_amount")
    @SerializedName("refund_amount")
    @Expose
    var refund_amount: Double = 0.0

    @ColumnInfo(name = "receipt_person_name")
    @SerializedName("receipt_person_name")
    @Expose
    var receipt_person_name: String? = null

    @ColumnInfo(name = "sale_person_id")
    @SerializedName("sale_person_id")
    @Expose
    var sale_person_id: Int = 0

    @ColumnInfo(name = "due_date")
    @SerializedName("due_date")
    @Expose
    var due_date: String? = null

    @ColumnInfo(name = "cash_or_credit")
    @SerializedName("cash_or_credit")
    @Expose
    var cash_or_credit: String? = null

    @ColumnInfo(name = "location_code")
    @SerializedName("location_code")
    @Expose
    var location_code: String? = null

    @ColumnInfo(name = "device_id")
    @SerializedName("device_id")
    @Expose
    var device_id: String? = null

    @ColumnInfo(name = "invoice_time")
    @SerializedName("invoice_time")
    @Expose
    var invoice_time: String? = null

    @ColumnInfo(name = "package_invoice_number")
    @SerializedName("package_invoice_number")
    @Expose
    var package_invoice_number: Int = 0

    @ColumnInfo(name = "package_status")
    @SerializedName("package_status")
    @Expose
    var package_status: Int = 0

    @ColumnInfo(name = "volume_amount")
    @SerializedName("volume_amount")
    @Expose
    var volume_amount: Double = 0.0

    @ColumnInfo(name = "package_grade")
    @SerializedName("package_grade")
    @Expose
    var package_grade: String? = null

    @ColumnInfo(name = "invoice_product_id")
    @SerializedName("invoice_product_id")
    @Expose
    var invoice_product_id: Int = 0

    @ColumnInfo(name = "total_quantity")
    @SerializedName("total_quantity")
    @Expose
    var total_quantity: Double = 0.0

    @ColumnInfo(name = "invoice_status")
    @SerializedName("invoice_status")
    @Expose
    var invoice_status: String? = null

    @ColumnInfo(name = "total_discount_percent")
    @SerializedName("total_discount_percent")
    @Expose
    var total_discount_percent: String? = null

    @ColumnInfo(name = "rate")
    @SerializedName("rate")
    @Expose
    var rate: String? = null

    @ColumnInfo(name = "tax_amount")
    @SerializedName("tax_amount")
    @Expose
    var tax_amount: Double = 0.0

    @ColumnInfo(name = "bank_name")
    @SerializedName("bank_name")
    @Expose
    var bank_name: String? = null

    @ColumnInfo(name = "bank_account_no")
    @SerializedName("bank_account_no")
    @Expose
    var bank_account_no: String? = null

    @ColumnInfo(name = "sale_flag")
    @SerializedName("sale_flag")
    @Expose
    var sale_flag: String? = null

}
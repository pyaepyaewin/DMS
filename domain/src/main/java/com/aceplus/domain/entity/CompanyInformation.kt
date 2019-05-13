package com.aceplus.domain.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "company_information")
class CompanyInformation {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    var id: Int = 0

    @ColumnInfo(name = "description")
    @SerializedName("description")
    @Expose
    var description: String? = null

    @ColumnInfo(name = "main_db_name")
    @SerializedName("main_db_name")
    @Expose
    var main_db_name: String? = null

    @ColumnInfo(name = "home_currency_id")
    @SerializedName("home_currency_id")
    @Expose
    var home_currency_id: String? = null

    @ColumnInfo(name = "multi_currency")
    @SerializedName("multi_currency")
    @Expose
    var multi_currency: String? = null

    @ColumnInfo(name = "start_date")
    @SerializedName("start_date")
    @Expose
    var start_date: String? = null

    @ColumnInfo(name = "end_date")
    @SerializedName("end_date")
    @Expose
    var end_date: String? = null

    @ColumnInfo(name = "auto_generate")
    @SerializedName("auto_generate")
    @Expose
    var auto_generate: String? = null

    @ColumnInfo(name = "company_name")
    @SerializedName("company_name")
    @Expose
    var company_name: String? = null

    @ColumnInfo(name = "short_name")
    @SerializedName("short_name")
    @Expose
    var short_name: String? = null

    @ColumnInfo(name = "contact_person")
    @SerializedName("contact_person")
    @Expose
    var contact_person: String? = null

    @ColumnInfo(name = "address")
    @SerializedName("address")
    @Expose
    var address: String? = null

    @ColumnInfo(name = "email")
    @SerializedName("email")
    @Expose
    var email: String? = null

    @ColumnInfo(name = "website")
    @SerializedName("website")
    @Expose
    var website: String? = null

    @ColumnInfo(name = "serial_number")
    @SerializedName("serial_number")
    @Expose
    var serial_number: String? = null

    @ColumnInfo(name = "phone_number")
    @SerializedName("phone_number")
    @Expose
    var phone_number: String? = null

    @ColumnInfo(name = "is_separator")
    @SerializedName("is_separator")
    @Expose
    var separator: String? = null

    @ColumnInfo(name = "amount_format")
    @SerializedName("amount_format")
    @Expose
    var amount_format: String? = null

    @ColumnInfo(name = "price_format")
    @SerializedName("price_format")
    @Expose
    var price_format: String? = null

    @ColumnInfo(name = "quantity_format")
    @SerializedName("quantity_format")
    @Expose
    var quantity_format: String? = null

    @ColumnInfo(name = "rate_format")
    @SerializedName("rate_format")
    @Expose
    var rate_format: String? = null

    @ColumnInfo(name = "valuation_method")
    @SerializedName("valuation_method")
    @Expose
    var valuation_method: String? = null

    @ColumnInfo(name = "font")
    @SerializedName("font")
    @Expose
    var font: String? = null

    @ColumnInfo(name = "report_font")
    @SerializedName("report_font")
    @Expose
    var report_font: String? = null

    @ColumnInfo(name = "receipt_voucher")
    @SerializedName("receipt_voucher")
    @Expose
    var receipt_voucher: String? = null

    @ColumnInfo(name = "print_port")
    @SerializedName("print_port")
    @Expose
    var print_port: String? = null

    @ColumnInfo(name = "pos_voucher_footer1")
    @SerializedName("pos_voucher_footer1")
    @Expose
    var pos_voucher_footer1: String? = null

    @ColumnInfo(name = "pos_voucher_footer2")
    @SerializedName("pos_voucher_footer2")
    @Expose
    var pos_voucher_footer2: String? = null

    @ColumnInfo(name = "is_stock_auto_generate")
    @SerializedName("is_stock_auto_generate")
    @Expose
    var stock_auto_generate: String? = null

    @ColumnInfo(name = "pc_count")
    @SerializedName("pc_count")
    @Expose
    var pc_count: String? = null

    @ColumnInfo(name = "expired_month")
    @SerializedName("expired_month")
    @Expose
    var expired_month: String? = null

    @ColumnInfo(name = "paid_status")
    @SerializedName("paid_status")
    @Expose
    var paid_status: String? = null

    @ColumnInfo(name = "h1")
    @SerializedName("h1")
    @Expose
    var h1: String? = null

    @ColumnInfo(name = "h2")
    @SerializedName("h2")
    @Expose
    var h2: String? = null

    @ColumnInfo(name = "h3")
    @SerializedName("h3")
    @Expose
    var h3: String? = null

    @ColumnInfo(name = "h4")
    @SerializedName("h4")
    @Expose
    var h4: String? = null

    @ColumnInfo(name = "f1")
    @SerializedName("f1")
    @Expose
    var f1: String? = null

    @ColumnInfo(name = "f2")
    @SerializedName("f2")
    @Expose
    var f2: String? = null

    @ColumnInfo(name = "f3")
    @SerializedName("f3")
    @Expose
    var f3: String? = null

    @ColumnInfo(name = "f4")
    @SerializedName("f4")
    @Expose
    var f4: String? = null

    @ColumnInfo(name = "tax")
    @SerializedName("tax")
    @Expose
    var tax: String? = null

    @ColumnInfo(name = "branch_code")
    @SerializedName("branch_code")
    @Expose
    var branch_code: String? = null

    @ColumnInfo(name = "branch_name")
    @SerializedName("branch_name")
    @Expose
    var branch_name: String? = null

    @ColumnInfo(name = "hb_code")
    @SerializedName("hb_code")
    @Expose
    var hb_code: String? = null

    @ColumnInfo(name = "credit_sale")
    @SerializedName("credit_sale")
    @Expose
    var credit_sale: String? = null

    @ColumnInfo(name = "use_combo")
    @SerializedName("use_combo")
    @Expose
    var use_combo: String? = null

    @ColumnInfo(name = "last_day_close_date")
    @SerializedName("last_day_close_date")
    @Expose
    var last_day_close_date: String? = null

    @ColumnInfo(name = "last_update_invoice_date")
    @SerializedName("last_update_invoice_date")
    @Expose
    var last_update_invoice_date: String? = null

    @ColumnInfo(name = "company_type")
    @SerializedName("company_type")
    @Expose
    var company_type: String? = null

    @ColumnInfo(name = "company_code")
    @SerializedName("company_code")
    @Expose
    var company_code: String? = null

    @ColumnInfo(name = "start_time")
    @SerializedName("start_time")
    @Expose
    var start_time: String? = null

    @ColumnInfo(name = "end_time")
    @SerializedName("end_time")
    @Expose
    var end_time: String? = null

    @ColumnInfo(name = "branch_id")
    @SerializedName("branch_id")
    @Expose
    var branch_id: String? = null

    @ColumnInfo(name = "print_copy")
    @SerializedName("print_copy")
    @Expose
    var print_copy: String? = null

    @ColumnInfo(name = "tax_type")
    @SerializedName("tax_type")
    @Expose
    var tax_type: String? = null

    @ColumnInfo(name = "balance_control")
    @SerializedName("balance_control")
    @Expose
    var balance_control: String? = null

    @ColumnInfo(name = "transaction_auto_generate")
    @SerializedName("transaction_auto_generate")
    @Expose
    var transaction_auto_generate: String? = null

    @ColumnInfo(name = "company_tax_reg_no")
    @SerializedName("company_tax_reg_no")
    @Expose
    var company_tax_reg_no: String? = null
}
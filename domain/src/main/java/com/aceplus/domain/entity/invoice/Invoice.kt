package com.aceplus.domain.entity.invoice

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "invoice")
class Invoice() : Parcelable {
    @PrimaryKey
    @ColumnInfo(name = "invoice_id")
    @SerializedName("invoice_id")
    @Expose
    var invoice_id: String = "0"

    @ColumnInfo(name = "customer_id")
    @SerializedName("customer_id")
    @Expose
    var customer_id: String? = ""

    @ColumnInfo(name = "sale_date")
    @SerializedName("sale_date")
    @Expose
    var sale_date: String? = null

    @ColumnInfo(name = "total_amount")
    @SerializedName("total_amount")
    @Expose
    var total_amount: String? = ""

    @ColumnInfo(name = "total_discount_amount")
    @SerializedName("total_discount_amount")
    @Expose
    var total_discount_amount: Double = 0.0

    @ColumnInfo(name = "pay_amount")
    @SerializedName("pay_amount")
    @Expose
    var pay_amount: String? = ""

    @ColumnInfo(name = "refund_amount")
    @SerializedName("refund_amount")
    @Expose
    var refund_amount: String? = ""

    @ColumnInfo(name = "receipt_person_name")
    @SerializedName("receipt_person_name")
    @Expose
    var receipt_person_name: String? = null

    @ColumnInfo(name = "sale_person_id")
    @SerializedName("sale_person_id")
    @Expose
    var sale_person_id: String? = ""

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
    var sale_flag: Int? = 0

    constructor(parcel: Parcel) : this() {
        invoice_id = parcel.readString()
        customer_id = parcel.readString()
        sale_date = parcel.readString()
        total_amount = parcel.readString()
        total_discount_amount = parcel.readDouble()
        pay_amount = parcel.readString()
        refund_amount = parcel.readString()
        receipt_person_name = parcel.readString()
        sale_person_id = parcel.readString()
        due_date = parcel.readString()
        cash_or_credit = parcel.readString()
        location_code = parcel.readString()
        device_id = parcel.readString()
        invoice_time = parcel.readString()
        package_invoice_number = parcel.readInt()
        package_status = parcel.readInt()
        volume_amount = parcel.readDouble()
        package_grade = parcel.readString()
        invoice_product_id = parcel.readInt()
        total_quantity = parcel.readDouble()
        invoice_status = parcel.readString()
        total_discount_percent = parcel.readString()
        rate = parcel.readString()
        tax_amount = parcel.readDouble()
        bank_name = parcel.readString()
        bank_account_no = parcel.readString()
        sale_flag = parcel.readValue(Int::class.java.classLoader) as? Int
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(invoice_id)
        parcel.writeString(customer_id)
        parcel.writeString(sale_date)
        parcel.writeString(total_amount)
        parcel.writeDouble(total_discount_amount)
        parcel.writeString(pay_amount)
        parcel.writeString(refund_amount)
        parcel.writeString(receipt_person_name)
        parcel.writeString(sale_person_id)
        parcel.writeString(due_date)
        parcel.writeString(cash_or_credit)
        parcel.writeString(location_code)
        parcel.writeString(device_id)
        parcel.writeString(invoice_time)
        parcel.writeInt(package_invoice_number)
        parcel.writeInt(package_status)
        parcel.writeDouble(volume_amount)
        parcel.writeString(package_grade)
        parcel.writeInt(invoice_product_id)
        parcel.writeDouble(total_quantity)
        parcel.writeString(invoice_status)
        parcel.writeString(total_discount_percent)
        parcel.writeString(rate)
        parcel.writeDouble(tax_amount)
        parcel.writeString(bank_name)
        parcel.writeString(bank_account_no)
        parcel.writeValue(sale_flag)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Invoice> {
        override fun createFromParcel(parcel: Parcel): Invoice {
            return Invoice(parcel)
        }

        override fun newArray(size: Int): Array<Invoice?> {
            return arrayOfNulls(size)
        }
    }

}
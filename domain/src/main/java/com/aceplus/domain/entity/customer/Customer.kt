package com.aceplus.domain.entity.customer

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "customer")
class Customer() : Parcelable {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    var id: Int = 0

    @ColumnInfo(name = "customer_id")
    @SerializedName("customer_id")
    @Expose
    var customer_id: String? = null

    @ColumnInfo(name = "customer_name")
    @SerializedName("customer_name")
    @Expose
    var customer_name: String? = null

    @ColumnInfo(name = "customer_type_id")
    @SerializedName("customer_type_id")
    @Expose
    var customer_type_id: String? = null

    @ColumnInfo(name = "customer_type_name")
    @SerializedName("customer_type_name")
    @Expose
    var customer_type_name: String? = null

    @ColumnInfo(name = "address")
    @SerializedName("address")
    @Expose
    var address: String? = null

    @ColumnInfo(name = "phone")
    @SerializedName("phone")
    @Expose
    var phone: String? = null

    @ColumnInfo(name = "township")
    @SerializedName("township")
    @Expose
    var township: String? = null

    @ColumnInfo(name = "credit_term")
    @SerializedName("credit_term")
    @Expose
    var credit_term: String? = null

    @ColumnInfo(name = "credit_limit")
    @SerializedName("credit_limit")
    @Expose
    var credit_limit: String? = null

    @ColumnInfo(name = "credit_amount")
    @SerializedName("credit_amount")
    @Expose
    var credit_amount: String? = null

    @ColumnInfo(name = "due_amount")
    @SerializedName("due_amount")
    @Expose
    var due_amount: String? = null

    @ColumnInfo(name = "prepaid_amount")
    @SerializedName("prepaid_amount")
    @Expose
    var prepaid_amount: String? = null

    @ColumnInfo(name = "payment_type")
    @SerializedName("payment_type")
    @Expose
    var payment_type: String? = null

    @ColumnInfo(name = "is_in_route")
    @SerializedName("is_in_route")
    @Expose
    var in_route: String? = null

    @ColumnInfo(name = "latitude")
    @SerializedName("latitude")
    @Expose
    var latitude: String? = null

    @ColumnInfo(name = "longitude")
    @SerializedName("longitude")
    @Expose
    var longitude: String? = null

    @ColumnInfo(name = "visit_record")
    @SerializedName("visit_record")
    @Expose
    var visit_record: String? = null

    @ColumnInfo(name = "district_id")
    @SerializedName("district_id")
    @Expose
    var district_id: Int = 0

    @ColumnInfo(name = "state_division_id")
    @SerializedName("state_division_id")
    @Expose
    var state_division_id: Int = 0

    @ColumnInfo(name = "shop_type_id")
    @SerializedName("shop_type_id")
    @Expose
    var shop_type_id: Int = 0

    @ColumnInfo(name = "street_id")
    @SerializedName("street_id")
    @Expose
    var street_id: Int = 0

    @ColumnInfo(name = "fax")
    @SerializedName("fax")
    @Expose
    var fax: String? = null

    @ColumnInfo(name = "township_number")
    @SerializedName("township_number")
    @Expose
    var township_number: String? = null

    @ColumnInfo(name = "customer_category_no")
    @SerializedName("customer_category_no")
    @Expose
    var customer_category_no: String? = null

    @ColumnInfo(name = "contact_person")
    @SerializedName("contact_person")
    @Expose
    var contact_person: String? = null

    @ColumnInfo(name = "route_schedule_status")
    @SerializedName("route_schedule_status")
    @Expose
    var route_schedule_status: String? = null

    @ColumnInfo(name = "created_user_id")
    @SerializedName("created_user_id")
    @Expose
    var created_user_id: String? = null

    @ColumnInfo(name = "created_date")
    @SerializedName("created_date")
    @Expose
    var created_date: String? = null

    @ColumnInfo(name = "flag")
    @SerializedName("flag")
    @Expose
    var flag: String? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        customer_id = parcel.readString()
        customer_name = parcel.readString()
        customer_type_id = parcel.readString()
        customer_type_name = parcel.readString()
        address = parcel.readString()
        phone = parcel.readString()
        township = parcel.readString()
        credit_term = parcel.readString()
        credit_limit = parcel.readString()
        credit_amount = parcel.readString()
        due_amount = parcel.readString()
        prepaid_amount = parcel.readString()
        payment_type = parcel.readString()
        in_route = parcel.readString()
        latitude = parcel.readString()
        longitude = parcel.readString()
        visit_record = parcel.readString()
        district_id = parcel.readInt()
        state_division_id = parcel.readInt()
        shop_type_id = parcel.readInt()
        street_id = parcel.readInt()
        fax = parcel.readString()
        township_number = parcel.readString()
        customer_category_no = parcel.readString()
        contact_person = parcel.readString()
        route_schedule_status = parcel.readString()
        created_user_id = parcel.readString()
        created_date = parcel.readString()
        flag = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(customer_id)
        parcel.writeString(customer_name)
        parcel.writeString(customer_type_id)
        parcel.writeString(customer_type_name)
        parcel.writeString(address)
        parcel.writeString(phone)
        parcel.writeString(township)
        parcel.writeString(credit_term)
        parcel.writeString(credit_limit)
        parcel.writeString(credit_amount)
        parcel.writeString(due_amount)
        parcel.writeString(prepaid_amount)
        parcel.writeString(payment_type)
        parcel.writeString(in_route)
        parcel.writeString(latitude)
        parcel.writeString(longitude)
        parcel.writeString(visit_record)
        parcel.writeInt(district_id)
        parcel.writeInt(state_division_id)
        parcel.writeInt(shop_type_id)
        parcel.writeInt(street_id)
        parcel.writeString(fax)
        parcel.writeString(township_number)
        parcel.writeString(customer_category_no)
        parcel.writeString(contact_person)
        parcel.writeString(route_schedule_status)
        parcel.writeString(created_user_id)
        parcel.writeString(created_date)
        parcel.writeString(flag)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Customer> {
        override fun createFromParcel(parcel: Parcel): Customer {
            return Customer(parcel)
        }

        override fun newArray(size: Int): Array<Customer?> {
            return arrayOfNulls(size)
        }
    }
}
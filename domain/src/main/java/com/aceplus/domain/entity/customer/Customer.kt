package com.aceplus.domain.entity.customer

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity(tableName = "customer")
class Customer {

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
}
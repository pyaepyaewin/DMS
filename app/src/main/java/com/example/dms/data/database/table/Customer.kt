package com.example.dms.data.database.table

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable
@Entity(tableName = "customer")
data class Customer(
    val ADDRESS: String,
    val CREDIT_AMT: String,
    val CREDIT_LIMIT: Int,
    val CREDIT_TERM: Int,
    @PrimaryKey
    val CUSTOMER_ID: String,
    val CUSTOMER_NAME: String,
    val CUSTOMER_TYPE_ID: Int,
    val CUSTOMER_TYPE_NAME: String?="",
    val DUE_AMT: String,
    val Fax: String,
    val IS_IN_ROUTE: String,
    val LATITUDE: Double,
    val LONGITUDE: Double,
    val PAYMENT_TYPE: String,
    val PH: String,
    val PREPAID_AMT: String,
    val SaleStatus: String,
    val StreetId: Int,
    val VISIT_RECORD: String,
    val contact_person: String,
    val customer_category_no: String,
    val district_id: Int,
    val id: Int,
    val route_schedule_status: Int,
    val shop_type_id: Int,
    val state_division_id: Int,
    val township_number: Int
):Serializable
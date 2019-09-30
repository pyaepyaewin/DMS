package com.example.dms.network.response

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
data class Product(
    @PrimaryKey
    val Id: String,
    val Product_id: String,
    val Product_name: String,
    val category_id: String,
    val class_id: String,
    val device_issue_status: Int,
    val group_id: String,
    val product_type_id: String,
    val purchase_price: String,
    val remaining_qty: Int,
    val selling_price: String,
    val total_qty: Int,
    val um_id: String
)
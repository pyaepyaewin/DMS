package com.example.dms.data.database.table

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "date")
data class Date(
    @PrimaryKey
    val id: String,
    val CUSTOMER_ID: String,
    val amount: String,
    val discountPrice: String,
    val discountAmount: String


)

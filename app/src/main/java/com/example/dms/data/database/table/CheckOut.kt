package com.example.dms.data.database.table

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "checkOut")
data class CheckOut(
    @PrimaryKey
    val id:String,
    val invoice_ID:String,
    val product_id:String,
    val qty:Int,
    val um:String,
    val price:String,
    val promoPrice:String,
    val amount:String
)

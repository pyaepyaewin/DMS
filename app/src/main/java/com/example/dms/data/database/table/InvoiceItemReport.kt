package com.example.dms.data.database.table

import androidx.room.Entity

data class InvoiceItemReport(
    val Product_name: String,
    val um: String,
    val qty: Int,
    val price: String,
    val promo_price: String?,
    val amount: String?)
package com.example.dms.data.database.table

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


data class InvoiceReport(
    @PrimaryKey
    val id: String,
    val CUSTOMER_NAME: String,
    val date:String?=null,
    val amount: String?=null,
    val discountPrice: String?=null,
    val discountAmount: String?=null


)

package com.example.dms.data.database.table

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "invoice_item")
data class InvoiceItem(
    @PrimaryKey val id: String,
    val invoiceId: String,
    val productId: String,
    var um: String,
    var qty: Int,
    var price: String,
    var foc: Boolean,
    var discount: Float
):Serializable

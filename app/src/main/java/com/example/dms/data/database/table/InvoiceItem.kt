package com.example.dms.data.database.table

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "invoice_item")
data class InvoiceItem(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    val id: Int,
    var invoiceId: String,
    val productId: String,
    var um: String,
    var qty: Int,
    var price: String,
    var foc: Boolean,
    var discount: Float
):Serializable

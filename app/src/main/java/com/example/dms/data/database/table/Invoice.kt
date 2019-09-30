package com.example.dms.data.database.table

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "invoice")
data class Invoice(
    @PrimaryKey val id: String,
    val cid: String,
    val date: String,
    val netAmt: String,
    val discPercent: String,
    val discAmt: String
)
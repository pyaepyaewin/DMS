package com.example.dms.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.dms.data.database.table.InvoiceItem

@Dao
interface InvoiceItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(invoiceItem: List<InvoiceItem>)

}
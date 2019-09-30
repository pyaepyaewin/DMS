package com.example.dms.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.dms.data.database.table.CheckOut
import com.example.dms.data.database.table.Invoice
import com.example.dms.data.database.table.InvoiceReport

@Dao
interface CheckOutDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(checkoutItems: MutableList<Invoice>)
}

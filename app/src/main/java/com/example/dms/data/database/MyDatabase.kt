package com.example.dms.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dms.data.database.dao.*
import com.example.dms.data.database.table.CheckOut
import com.example.dms.network.response.Customer
import com.example.dms.data.database.table.Invoice
import com.example.dms.data.database.table.InvoiceItem
import com.example.dms.network.response.Product

@Database(entities = [Customer::class, Product::class, Invoice::class,CheckOut::class,InvoiceItem::class], version = 1, exportSchema = false)

abstract class MyDatabase:RoomDatabase() {
    abstract fun customerDao(): CustomerDao
    abstract fun invoiceDao():InvoiceDao
    abstract fun productDao():ProductDao
    abstract fun saleItemReportDao():SaleItemReportDao
    abstract fun checkOutDao():CheckOutDao
    abstract fun invoiceItemDao():InvoiceItemDao
    abstract fun saleReportDao():SaleReportDao

    companion object {
        private var instance: MyDatabase? = null

        fun getInstance(context: Context): MyDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context, MyDatabase::class.java, "MyDBName")
                    .build()
            }
            return instance as MyDatabase
        }
    }
}
package com.example.dms.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dms.data.database.dao.CheckOutDao
import com.example.dms.data.database.dao.CustomerDao
import com.example.dms.data.database.dao.DateDao
import com.example.dms.data.database.dao.ProductDao
import com.example.dms.data.database.table.CheckOut
import com.example.dms.data.database.table.Customer
import com.example.dms.data.database.table.Date
import com.example.dms.data.database.table.Product

@Database(entities = [Customer::class, Product::class,Date::class,CheckOut::class], version = 1, exportSchema = false)

abstract class MyDatabase:RoomDatabase() {
    abstract fun customerDao(): CustomerDao
    abstract fun productDao():ProductDao
    abstract fun dateDao():DateDao
    abstract fun checkOutDao():CheckOutDao

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
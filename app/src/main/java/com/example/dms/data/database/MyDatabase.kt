package com.example.dms.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dms.data.database.dao.CustomerDao
import com.example.dms.data.database.dao.ProductDao
import com.example.dms.network.response.Customer
import com.example.dms.network.response.Sale.Product


abstract class MyDatabase:RoomDatabase() {
    abstract fun customerDao(): CustomerDao
    abstract fun productDao():ProductDao

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
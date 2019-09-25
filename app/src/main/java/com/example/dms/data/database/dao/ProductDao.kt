package com.example.dms.data.database.dao

import android.database.Observable
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dms.network.response.Sale.Product

@Dao
interface ProductDao {
    @get:Query("select * from product")
    val allProductData: Observable<List<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<Product>)
}
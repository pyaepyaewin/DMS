package com.example.dms.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dms.network.response.Product
import io.reactivex.Observable

@Dao
interface ProductDao {
    @get:Query("select * from product")
    val allProductData: Observable<List<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<Product>)
}
package com.aceplus.data.database.dao.product

import com.aceplus.domain.entity.product.Product
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.aceplus.domain.model.report.ProductBalanceReport
import io.reactivex.Observable


@Dao
interface ProductDao {

    @get:Query("select * from product")
    val allDataLD: LiveData<List<Product>>

    @get:Query("select * from product")
    val allData: List<Product>

    @get:Query("select * from product")
    val allProductData: List<Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<Product>)

    @Query("Delete from product")
    fun deleteAll()

    @Query("select product_name,total_quantity,order_quantity,sold_quantity,exchange_quantity,return_quantity,delivery_quantity,present_quantity,remaining_quantity from product")
    fun getProductBalanceReport(): List<ProductBalanceReport>

}
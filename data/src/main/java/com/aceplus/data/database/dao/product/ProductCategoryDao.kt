package com.aceplus.data.database.dao.product

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.aceplus.domain.entity.product.ProductCategory


@Dao
interface ProductCategoryDao {

    @get:Query("select * from product_category")
    val allDataLD: LiveData<List<ProductCategory>>

    @get:Query("select * from product_category")
    val allData: List<ProductCategory>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<ProductCategory>)

    @Query("Delete from product_category")
    fun deleteAll()


}
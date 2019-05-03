package com.aceplus.data.database.dao.product

import com.aceplus.domain.entity.product.ProductType
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface ProductTypeDao{

    @get:Query("select * from product_type")
    val allDataLD: LiveData<List<ProductType>>

    @get:Query("select * from product_type")
    val allData: List<ProductType>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<ProductType>)

    @Query("Delete from product_type")
    fun deleteAll()

}
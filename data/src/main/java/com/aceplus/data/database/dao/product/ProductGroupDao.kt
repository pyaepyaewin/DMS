package com.aceplus.data.database.dao.product

import com.aceplus.domain.entity.product.ProductGroup
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface ProductGroupDao{

    @get:Query("select * from product_group")
    val allDataLD: LiveData<List<ProductGroup>>

    @get:Query("select * from product_group")
    val allData: List<ProductGroup>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<ProductGroup>)

    @Query("Delete from product_group")
    fun deleteAll()

}
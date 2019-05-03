package com.aceplus.data.database.dao.tdiscount

import com.aceplus.domain.entity.tdiscount.TDiscountByCategoryQuantity
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface TDiscountByCategoryQuantityDao{

    @get:Query("select * from t_discount_by_category_quantity")
    val allDataLD: LiveData<List<TDiscountByCategoryQuantity>>

    @get:Query("select * from t_discount_by_category_quantity")
    val allData: List<TDiscountByCategoryQuantity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<TDiscountByCategoryQuantity>)

    @Query("Delete from t_discount_by_category_quantity")
    fun deleteAll()

}
package com.aceplus.data.database.dao.tdiscount

import com.aceplus.domain.entity.tdiscount.TDiscountByCategoryQuantityItem
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface TDiscountByCategoryQuantityItemDao{

    @get:Query("select * from t_discount_by_category_quantity_item")
    val allDataLD: LiveData<List<TDiscountByCategoryQuantityItem>>

    @get:Query("select * from t_discount_by_category_quantity_item")
    val allData: List<TDiscountByCategoryQuantityItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<TDiscountByCategoryQuantityItem>)

    @Query("Delete from t_discount_by_category_quantity_item")
    fun deleteAll()

}
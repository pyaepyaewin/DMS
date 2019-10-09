package com.aceplus.data.database.dao.classdiscount

import com.aceplus.domain.entity.classdiscount.ClassDiscountByPriceItem
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface ClassDiscountByPriceItemDao {

    @get:Query("select * from class_discount_by_price_item")
    val allDataLD: LiveData<List<ClassDiscountByPriceItem>>

    @get:Query("select * from class_discount_by_price_item")
    val allData: List<ClassDiscountByPriceItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<ClassDiscountByPriceItem>)

    @Query("Delete from class_discount_by_price_item")
    fun deleteAll()

    @Query("SELECT * FROM class_discount_by_price_item WHERE class_discount_id = :classDiscountId")
    fun getClassDiscountByPriceItem(classDiscountId: Int): List<ClassDiscountByPriceItem>

}

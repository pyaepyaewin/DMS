package com.aceplus.data.database.dao.classdiscount

import com.aceplus.domain.entity.classdiscount.ClassDiscountByPrice
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

@Dao
interface ClassDiscountByPriceDao {

    @get:Query("select * from class_discount_by_price")
    val allDataLD: LiveData<List<ClassDiscountByPrice>>

    @get:Query("select * from class_discount_by_price")
    val allData: List<ClassDiscountByPrice>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<ClassDiscountByPrice>)

    @Query("Delete from class_discount_by_price")
    fun deleteAll()

    @Query("SELECT * FROM class_discount_by_price WHERE date(:currentDate) BETWEEN date(start_date) AND date(end_date)")
    fun getClassDiscountByPrice(currentDate: String): List<ClassDiscountByPrice>

}

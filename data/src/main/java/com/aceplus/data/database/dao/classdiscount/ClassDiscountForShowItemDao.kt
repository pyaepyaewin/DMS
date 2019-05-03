package com.aceplus.data.database.dao.classdiscount

import com.aceplus.domain.entity.classdiscount.ClassDiscountForShowItem
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface ClassDiscountForShowItemDao {

    @get:Query("select * from class_discount_for_show_item")
    val allDataLD: LiveData<List<ClassDiscountForShowItem>>

    @get:Query("select * from class_discount_for_show_item")
    val allData: List<ClassDiscountForShowItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<ClassDiscountForShowItem>)

    @Query("Delete from class_discount_for_show_item")
    fun deleteAll()

}

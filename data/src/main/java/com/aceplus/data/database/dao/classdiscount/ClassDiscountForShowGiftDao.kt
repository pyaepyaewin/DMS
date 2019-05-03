package com.aceplus.data.database.dao.classdiscount

import com.aceplus.domain.entity.classdiscount.ClassDiscountForShowGift
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface ClassDiscountForShowGiftDao {

    @get:Query("select * from class_discount_for_show_gift")
    val allDataLD: LiveData<List<ClassDiscountForShowGift>>

    @get:Query("select * from class_discount_for_show_gift")
    val allData: List<ClassDiscountForShowGift>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<ClassDiscountForShowGift>)

    @Query("Delete from class_discount_for_show_gift")
    fun deleteAll()

}

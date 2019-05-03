package com.aceplus.data.database.dao.classdiscount

import com.aceplus.domain.entity.classdiscount.ClassDiscountByPriceGift
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface ClassDiscountByPriceGiftDao {

    @get:Query("select * from class_discount_by_gift")
    val allDataLD: LiveData<List<ClassDiscountByPriceGift>>

    @get:Query("select * from class_discount_by_gift")
    val allData: List<ClassDiscountByPriceGift>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<ClassDiscountByPriceGift>)

    @Query("Delete from class_discount_by_gift")
    fun deleteAll()

}

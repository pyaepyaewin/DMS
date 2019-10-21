package com.aceplus.data.database.dao.classdiscount

import com.aceplus.domain.entity.classdiscount.ClassDiscountByPrice
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.aceplus.domain.model.promotionDataClass.ClassDiscountByPriceDataClass

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

    @Query("select class.name,class_discount_by_price_item.from_quantity,class_discount_by_price_item.to_quantity,class_discount_by_price_item.from_amount,class_discount_by_price_item.to_amount,class_discount_by_price_item.discount_percent from class_discount_by_price_item,class where class_discount_by_price_item.class_id=class.class_id")
    fun getClassDiscountByPriceList():List<ClassDiscountByPriceDataClass>

    @Query("select * from class_discount_by_price where date(:currentDate) between date(start_date) and date(end_date)")
    fun getClassDiscountByPrice(currentDate: String): List<ClassDiscountByPrice>

    @Query("select * from class_discount_by_price where id = (select class_discount_id from class_discount_by_price_item where class_id = :currentClassId) ")
    fun getClassDiscountByPriceOnClassID(currentClassId: String): List<ClassDiscountByPrice>

}

package com.aceplus.data.database.dao.classdiscount

import com.aceplus.domain.entity.classdiscount.ClassDiscountForShowItem
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.aceplus.domain.model.promotionDataClass.ClassDiscountForShowPriceDataClass


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

    @Query("select class_discount_for_show.discount_type,class.name,class_discount_for_show_item.from_quantity,class_discount_for_show_item.to_quantity,class_discount_for_show_item.from_amount,class_discount_for_show_item.to_amount,class_discount_for_show_item.discount_percent from class_discount_for_show_item,class,class_discount_for_show,class_discount_for_show_gift where date(:currentDate) BETWEEN date(start_date) AND date(end_date) and class_discount_for_show.discount_type = 'P' and class_discount_for_show_item.class_discount_id= class_discount_for_show.id and class.class_id=class_discount_for_show_item.class_id ")
    fun getClassDiscountForShowPriceList(currentDate:String):List<ClassDiscountForShowPriceDataClass>

}

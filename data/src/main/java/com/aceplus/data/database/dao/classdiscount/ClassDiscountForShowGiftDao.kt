package com.aceplus.data.database.dao.classdiscount

import com.aceplus.domain.entity.classdiscount.ClassDiscountForShowGift
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.aceplus.domain.model.promotionDataClass.ClassDiscountForShowGiftDataClass


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

    @Query("select CDFI.class_id,CDFI.from_quantity,CDFI.to_quantity,CDFI.from_amount,CDFI.to_amount,C.name,CDFG.quantity from class_discount_for_show_item as CDFI,class_discount_for_show_gift as CDFG,class as C where CDFI.class_id= C.class_id and C.class_id=CDFG.class_id")
     fun getClassDiscountForShowGift(): List<ClassDiscountForShowGiftDataClass>

}

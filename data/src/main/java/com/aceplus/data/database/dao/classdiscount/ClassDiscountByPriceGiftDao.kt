package com.aceplus.data.database.dao.classdiscount

import com.aceplus.domain.entity.classdiscount.ClassDiscountByPriceGift
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.aceplus.domain.model.promotionDataClass.ClassDiscountByGiftDataClass


@Dao
interface ClassDiscountByPriceGiftDao {

    @get:Query("select * from class_discount_by_price_gift")
    val allDataLD: LiveData<List<ClassDiscountByPriceGift>>

    @get:Query("select * from class_discount_by_price_gift")
    val allData: List<ClassDiscountByPriceGift>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<ClassDiscountByPriceGift>)

    @Query("Delete from class_discount_by_price_gift")
    fun deleteAll()

    @Query("select CDP.discount_type,class.name,CDPI.from_quantity,CDPI.to_quantity,CDPI.from_amount,CDPI.to_amount,P.product_name,CDPG.quantity from class_discount_by_price as CDP,class_discount_by_price_item as CDPI,product as P,class_discount_by_price_gift as CDPG,class where CDP.discount_type='G' and CDP.id=CDPI.class_discount_id and class.class_id=CDPI.class_id and CDPG.class_discount_id=CDPI.class_discount_id and P.id=CDPG.stock_id")
    fun getClassDiscountByGiftList(): List<ClassDiscountByGiftDataClass>
}

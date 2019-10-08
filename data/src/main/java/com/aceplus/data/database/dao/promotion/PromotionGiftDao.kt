package com.aceplus.data.database.dao.promotion

import com.aceplus.domain.entity.promotion.PromotionGift
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.aceplus.domain.model.promotionDataClass.PromotionGiftDataClass


@Dao
interface PromotionGiftDao{

    @get:Query("select * from promotion_gift")
    val allDataLD: LiveData<List<PromotionGift>>

    @get:Query("select * from promotion_gift")
    val allData: List<PromotionGift>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<PromotionGift>)

    @Query("Delete from promotion_gift")
    fun deleteAll()
    @Query("select product.product_name,promotion_gift.from_quantity,promotion_gift.to_quantity,promotion_gift_item.stock_id,promotion_gift_item.quantity from product, promotion_gift, promotion_gift_item where product.product_id=promotion_gift.stock_id and promotion_gift_item.stock_id=product.product_id")
    fun getPromotionGiftForReport(): List<PromotionGiftDataClass>

}
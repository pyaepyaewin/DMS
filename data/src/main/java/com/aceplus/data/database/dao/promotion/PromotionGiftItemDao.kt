package com.aceplus.data.database.dao.promotion

import com.aceplus.domain.entity.promotion.PromotionGiftItem
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface PromotionGiftItemDao{

    @get:Query("select * from promotion_gift_item")
    val allDataLD: LiveData<List<PromotionGiftItem>>

    @get:Query("select * from promotion_gift_item")
    val allData: List<PromotionGiftItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<PromotionGiftItem>)

    @Query("Delete from promotion_gift_item")
    fun deleteAll()

}
package com.aceplus.data.database.dao.promotion

import com.aceplus.domain.entity.promotion.PromotionGift
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


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

}
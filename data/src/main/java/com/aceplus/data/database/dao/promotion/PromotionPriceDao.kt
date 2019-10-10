package com.aceplus.data.database.dao.promotion

import com.aceplus.domain.entity.promotion.PromotionPrice
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface PromotionPriceDao{

    @get:Query("select * from promotion_price")
    val allDataLD: LiveData<List<PromotionPrice>>

    @get:Query("select * from promotion_price")
    val allData: List<PromotionPrice>

    @Query("SELECT * FROM promotion_price WHERE promotion_plan_id = :promotionPlanId and from_quantity <= :buy_qty and to_quantity >= :buy_qty")
    fun getPromotionPriceByID(promotionPlanId: String, buy_qty: Int): List<PromotionPrice> // Need to add stock id

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<PromotionPrice>)

    @Query("Delete from promotion_price")
    fun deleteAll()

}
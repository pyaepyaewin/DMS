package com.aceplus.data.database.dao.promotion

import com.aceplus.domain.entity.promotion.PromotionAmount
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface PromotionAmountDao{

    @get:Query("select * from promotion_amount")
    val allDataLD: LiveData<List<PromotionAmount>>

    @get:Query("select * from promotion_amount")
    val allData: List<PromotionAmount>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<PromotionAmount>)

    @Query("Delete from promotion_amount")
    fun deleteAll()

}
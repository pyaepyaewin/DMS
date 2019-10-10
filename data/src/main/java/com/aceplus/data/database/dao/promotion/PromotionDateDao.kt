package com.aceplus.data.database.dao.promotion

import com.aceplus.domain.entity.promotion.PromotionDate
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface PromotionDateDao{

    @get:Query("select * from promotion_date")
    val allDataLD: LiveData<List<PromotionDate>>

    @get:Query("select * from promotion_date")
    val allData: List<PromotionDate>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<PromotionDate>)

    @Query("Delete from promotion_date")
    fun deleteAll()

    @Query("SELECT * FROM promotion_date WHERE DATE(promotion_date) = DATE(:currentDate)")
    fun getCurrentDatePromotion(currentDate: String): List<PromotionDate>

}
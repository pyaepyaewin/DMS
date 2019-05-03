package com.aceplus.data.database.dao.promotion

import com.aceplus.domain.entity.promotion.Promotion
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface PromotionDao{

    @get:Query("select * from promotion")
    val allDataLD: LiveData<List<Promotion>>

    @get:Query("select * from promotion")
    val allData: List<Promotion>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<Promotion>)

    @Query("Delete from promotion")
    fun deleteAll()

}
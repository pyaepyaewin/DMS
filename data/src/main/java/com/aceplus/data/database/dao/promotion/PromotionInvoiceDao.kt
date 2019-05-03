package com.aceplus.data.database.dao.promotion

import com.aceplus.domain.entity.promotion.PromotionInvoice
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface PromotionInvoiceDao{

    @get:Query("select * from promotion_invoice")
    val allDataLD: LiveData<List<PromotionInvoice>>

    @get:Query("select * from promotion_invoice")
    val allData: List<PromotionInvoice>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<PromotionInvoice>)

    @Query("Delete from promotion_invoice")
    fun deleteAll()

}
package com.aceplus.data.database.dao.volumediscount

import com.aceplus.domain.entity.volumediscount.VolumeDiscount
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.aceplus.domain.model.promotionDataClass.VolumeDiscountDataClass


@Dao
interface VolumeDiscountDao{

    @get:Query("select * from volume_discount")
    val allDataLD: LiveData<List<VolumeDiscount>>

    @get:Query("select * from volume_discount")
    val allData: List<VolumeDiscount>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<VolumeDiscount>)

    @Query("Delete from volume_discount")
    fun deleteAll()

    @Query("select volume_discount.start_date,volume_discount.end_date,volume_discount_item.from_sale_amount,volume_discount_item.to_sale_amount,volume_discount_item.discount_percent,volume_discount_item.discount_amount,volume_discount_item.discount_price,volume_discount.exclude from volume_discount,volume_discount_item where volume_discount.id=volume_discount_item.volume_discount_id")
    fun getVolumeDiscountList(): List<VolumeDiscountDataClass>

    @Query("select * from volume_discount where date(:currentDate) between date(start_date) and date(end_date)")
    fun getVolumeDiscountByDate(currentDate: String): List<VolumeDiscount>
}
package com.aceplus.data.database.dao.volumediscount

import com.aceplus.domain.entity.volumediscount.VolumeDiscountFilter
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.aceplus.domain.model.promotionDataClass.VolumeDiscountFilterDataClass


@Dao
interface VolumeDiscountFilterDao{

    @get:Query("select * from volume_discount_filter")
    val allDataLD: LiveData<List<VolumeDiscountFilter>>

    @get:Query("select * from volume_discount_filter")
    val allData: List<VolumeDiscountFilter>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<VolumeDiscountFilter>)

    @Query("Delete from volume_discount_filter")
    fun deleteAll()
    @Query("select volume_discount_filter.start_date,volume_discount_filter.end_date,volume_discount_filter_item.from_sale_amount,volume_discount_filter_item.to_sale_amount,volume_discount_filter_item.group_code_id,product_category.category_name, volume_discount_filter_item.discount_percent,volume_discount_filter_item.discount_amount,volume_discount_filter_item.discount_price,volume_discount_filter.exclude from volume_discount_filter,volume_discount_filter_item,product_category where volume_discount_filter_item.volume_discount_id=volume_discount_filter.id and product_category.category_Id=volume_discount_filter_item.category_id" )
    fun getVolumeDiscountFilter():List<VolumeDiscountFilterDataClass>

}
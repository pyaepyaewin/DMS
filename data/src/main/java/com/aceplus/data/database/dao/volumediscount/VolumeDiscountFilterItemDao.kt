package com.aceplus.data.database.dao.volumediscount

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.aceplus.domain.entity.volumediscount.VolumeDiscountFilterItem


@Dao
interface VolumeDiscountFilterItemDao{

    @get:Query("select * from volume_discount_filter_item")
    val allDataLD: LiveData<List<VolumeDiscountFilterItem>>

    @get:Query("select * from volume_discount_filter_item")
    val allData: List<VolumeDiscountFilterItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<VolumeDiscountFilterItem>)

    @Query("Delete from volume_discount_filter_item")
    fun deleteAll()

}
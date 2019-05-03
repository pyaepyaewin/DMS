package com.aceplus.data.database.dao.volumediscount

import com.aceplus.domain.entity.volumediscount.VolumeDiscountFilter
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


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

}
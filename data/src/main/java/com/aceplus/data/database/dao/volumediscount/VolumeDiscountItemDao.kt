package com.aceplus.data.database.dao.volumediscount

import com.aceplus.domain.entity.volumediscount.VolumeDiscountItem
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

@Dao
interface VolumeDiscountItemDao{

    @get:Query("select * from volume_discount_item")
    val allDataLD: LiveData<List<VolumeDiscountItem>>

    @get:Query("select * from volume_discount_item")
    val allData: List<VolumeDiscountItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<VolumeDiscountItem>)

    @Query("Delete from volume_discount_item")
    fun deleteAll()

}
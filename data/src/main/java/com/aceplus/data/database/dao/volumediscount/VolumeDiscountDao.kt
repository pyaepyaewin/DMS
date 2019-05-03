package com.aceplus.data.database.dao.volumediscount

import com.aceplus.domain.entity.volumediscount.VolumeDiscount
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


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

}
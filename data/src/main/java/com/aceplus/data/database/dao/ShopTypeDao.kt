package com.aceplus.data.database.dao

import com.aceplus.domain.entity.ShopType
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface ShopTypeDao{

    @get:Query("select * from shop_type")
    val allDataLD: LiveData<List<ShopType>>

    @get:Query("select * from shop_type")
    val allData: List<ShopType>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<ShopType>)

    @Query("Delete from shop_type")
    fun deleteAll()

}
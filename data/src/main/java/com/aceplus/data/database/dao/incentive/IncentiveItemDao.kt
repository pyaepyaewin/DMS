package com.aceplus.data.database.dao.incentive

import com.aceplus.domain.entity.incentive.IncentiveItem
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface IncentiveItemDao{

    @get:Query("select * from incentive_item")
    val allDataLD: LiveData<List<IncentiveItem>>

    @get:Query("select * from incentive_item")
    val allData: List<IncentiveItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<IncentiveItem>)

    @Query("Delete from incentive_item")
    fun deleteAll()

}
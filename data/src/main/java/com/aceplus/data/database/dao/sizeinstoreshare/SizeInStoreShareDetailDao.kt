package com.aceplus.data.database.dao.sizeinstoreshare

import com.aceplus.domain.entity.sizeinstoreshare.SizeInStoreShareDetail
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface SizeInStoreShareDetailDao{

    @get:Query("select * from size_in_store_share_detail")
    val allDataLD: LiveData<List<SizeInStoreShareDetail>>

    @get:Query("select * from size_in_store_share_detail")
    val allData: List<SizeInStoreShareDetail>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<SizeInStoreShareDetail>)

    @Query("Delete from size_in_store_share_detail")
    fun deleteAll()

}
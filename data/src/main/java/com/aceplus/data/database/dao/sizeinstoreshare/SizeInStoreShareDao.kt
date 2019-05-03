package com.aceplus.data.database.dao.sizeinstoreshare

import com.aceplus.domain.entity.sizeinstoreshare.SizeInStoreShare
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface SizeInStoreShareDao{

    @get:Query("select * from size_in_store_share")
    val allDataLD: LiveData<List<SizeInStoreShare>>

    @get:Query("select * from size_in_store_share")
    val allData: List<SizeInStoreShare>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<SizeInStoreShare>)

    @Query("Delete from size_in_store_share")
    fun deleteAll()

}
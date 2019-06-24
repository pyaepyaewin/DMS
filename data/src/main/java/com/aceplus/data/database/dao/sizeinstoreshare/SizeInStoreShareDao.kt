package com.aceplus.data.database.dao.sizeinstoreshare

import com.aceplus.domain.entity.sizeinstoreshare.SizeInStoreShare
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*


@Dao
interface SizeInStoreShareDao {

    @get:Query("select * from size_in_store_share")
    val allDataLD: LiveData<List<SizeInStoreShare>>

    @get:Query("select * from size_in_store_share")
    val allData: List<SizeInStoreShare>

    @get:Query("select * from size_in_store_share where delete_flag = 0")
    val allActiveData: List<SizeInStoreShare>

    @get:Query("select count(*) from size_in_store_share")
    val dataCount: Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<SizeInStoreShare>)

    @Query("Delete from size_in_store_share")
    fun deleteAll()

    @Query("update size_in_store_share set delete_flag = 1 where delete_flag = 0")
    fun updateAllInactiveData()
}
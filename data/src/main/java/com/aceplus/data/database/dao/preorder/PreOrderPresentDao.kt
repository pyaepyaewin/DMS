package com.aceplus.data.database.dao.preorder

import com.aceplus.domain.entity.preorder.PreOrderPresent
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface PreOrderPresentDao{

    @get:Query("select * from pre_order_present")
    val allDataLD: LiveData<List<PreOrderPresent>>

    @get:Query("select * from pre_order_present")
    val allData: List<PreOrderPresent>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<PreOrderPresent>)

    @Query("Delete from pre_order_present")
    fun deleteAll()

}
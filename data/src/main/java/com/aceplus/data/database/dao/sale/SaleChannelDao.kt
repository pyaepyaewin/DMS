package com.aceplus.data.database.dao.sale

import com.aceplus.domain.entity.sale.SaleChannel
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface SaleChannelDao{

    @get:Query("select * from sale_channel")
    val allDataLD: LiveData<List<SaleChannel>>

    @get:Query("select * from sale_channel")
    val allData: List<SaleChannel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<SaleChannel>)

    @Query("Delete from sale_channel")
    fun deleteAll()

}
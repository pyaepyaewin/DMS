package com.aceplus.data.database.dao.cash

import com.aceplus.domain.entity.cash.CashReceiveItem
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

@Dao
interface CashReceiveItemDao {

    @get:Query("select * from cash_receive_item")
    val allDataLD: LiveData<List<CashReceiveItem>>

    @get:Query("select * from cash_receive_item")
    val allData: List<CashReceiveItem>

    @Query("select * from cash_receive_item WHERE receive_no = :receive_no")
    fun allDataById(receive_no: String?): List<CashReceiveItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<CashReceiveItem>)

    @Query("Delete from cash_receive_item")
    fun deleteAll()


}

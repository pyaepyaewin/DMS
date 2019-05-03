package com.aceplus.data.database.dao.cash

import com.aceplus.domain.entity.cash.CashReceive
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

@Dao
interface CashReceiveDao {

    @get:Query("select * from cash_receive")
    val allDataLD: LiveData<List<CashReceive>>

    @get:Query("select * from cash_receive")
    val allData: List<CashReceive>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<CashReceive>)

    @Query("Delete from cash_receive")
    fun deleteAll()

}

package com.aceplus.data.database.dao

import com.aceplus.domain.entity.Currency
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface CurrencyDao{

    @get:Query("select * from currency")
    val allDataLD: LiveData<List<Currency>>

    @get:Query("select * from currency")
    val allData: List<Currency>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<Currency>)

    @Query("Delete from currency")
    fun deleteAll()

}
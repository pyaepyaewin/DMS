package com.aceplus.data.database.dao.posm

import com.aceplus.domain.entity.posm.POSM
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface POSMDao{

    @get:Query("select * from posm")
    val allDataLD: LiveData<List<POSM>>

    @get:Query("select * from posm")
    val allData: List<POSM>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<POSM>)

    @Query("Delete from posm")
    fun deleteAll()

}
package com.aceplus.data.database.dao

import com.aceplus.domain.entity.UM
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface UMDao{

    @get:Query("select * from um")
    val allDataLD: LiveData<List<UM>>

    @get:Query("select * from um")
    val allData: List<UM>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<UM>)

    @Query("Delete from um")
    fun deleteAll()

}
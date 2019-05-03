package com.aceplus.data.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.aceplus.domain.entity.Class


@Dao
interface ClassDao{

    @get:Query("select * from class")
    val allDataLD: LiveData<List<Class>>

    @get:Query("select * from class")
    val allData: List<com.aceplus.domain.entity.Class>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<com.aceplus.domain.entity.Class>)

    @Query("Delete from class")
    fun deleteAll()

}
package com.aceplus.data.database.dao.incentive

import com.aceplus.domain.entity.incentive.Incentive
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface IncentiveDao{

    @get:Query("select * from incentive")
    val allDataLD: LiveData<List<Incentive>>

    @get:Query("select * from incentive")
    val allData: List<Incentive>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<Incentive>)

    @Query("Delete from incentive")
    fun deleteAll()

}
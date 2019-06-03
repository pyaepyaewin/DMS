package com.aceplus.data.database.dao.incentive

import com.aceplus.domain.entity.incentive.IncentivePaid
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface IncentivePaidDao{

    @get:Query("select * from incentive_paid")
    val allDataLD: LiveData<List<IncentivePaid>>

    @get:Query("select * from incentive_paid")
    val allData: List<IncentivePaid>

    @get:Query("SELECT * FROM incentive_paid WHERE delete_flag = 0")
    val allActiveData: List<IncentivePaid>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<IncentivePaid>)

    @Query("Delete from incentive_paid")
    fun deleteAll()

}
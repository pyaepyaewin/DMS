package com.aceplus.data.database.dao.competitor

import com.aceplus.domain.entity.competitor.CompetitorActivity
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface CompetitorActivityDao{

    @get:Query("select * from competitor_activity")
    val allDataLD: LiveData<List<CompetitorActivity>>

    @get:Query("select * from competitor_activity")
    val allData: List<CompetitorActivity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<CompetitorActivity>)

    @Query("Delete from competitor_activity")
    fun deleteAll()

}

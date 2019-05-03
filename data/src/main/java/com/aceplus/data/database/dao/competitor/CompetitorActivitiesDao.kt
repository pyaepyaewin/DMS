package com.aceplus.data.database.dao.competitor

import com.aceplus.domain.entity.competitor.CompetitorActivities
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface CompetitorActivitiesDao {

    @get:Query("select * from competitor_activities")
    val allDataLD: LiveData<List<CompetitorActivities>>

    @get:Query("select * from competitor_activities")
    val allData: List<CompetitorActivities>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<CompetitorActivities>)

    @Query("Delete from competitor_activities")
    fun deleteAll()

}

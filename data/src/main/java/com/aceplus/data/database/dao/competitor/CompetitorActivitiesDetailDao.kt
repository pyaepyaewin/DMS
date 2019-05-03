package com.aceplus.data.database.dao.competitor

import com.aceplus.domain.entity.competitor.CompetitorActivitiesDetail
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface CompetitorActivitiesDetailDao{

    @get:Query("select * from competitor_activities_detail")
    val allDataLD: LiveData<List<CompetitorActivitiesDetail>>

    @get:Query("select * from competitor_activities_detail")
    val allData: List<CompetitorActivitiesDetail>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<CompetitorActivitiesDetail>)

    @Query("Delete from competitor_activities_detail")
    fun deleteAll()

}

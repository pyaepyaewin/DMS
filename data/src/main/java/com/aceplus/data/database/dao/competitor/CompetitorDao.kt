package com.aceplus.data.database.dao.competitor

import com.aceplus.domain.entity.competitor.Competitor
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface CompetitorDao {

    @get:Query("select * from competitor")
    val allDataLD: LiveData<List<Competitor>>

    @get:Query("select * from competitor")
    val allData: List<Competitor>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<Competitor>)

    @Query("Delete from competitor")
    fun deleteAll()

}

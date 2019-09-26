package com.example.dms.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dms.data.database.table.Date
import io.reactivex.Observable

@Dao
interface DateDao {
    @get:Query("select * from date")
    val allDateData: Observable<List<Date>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<Date>)
}

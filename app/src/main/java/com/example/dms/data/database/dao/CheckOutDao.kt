package com.example.dms.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dms.data.database.table.CheckOut
import io.reactivex.Observable

@Dao
interface CheckOutDao {
    @get:Query("select * from checkOut")
    val allCheckOutData: Observable<List<CheckOut>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<CheckOut>)
}

package com.aceplus.data.database.dao

import com.aceplus.domain.entity.GroupCode
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface GroupCodeDao{

    @get:Query("select * from group_code")
    val allDataLD: LiveData<List<GroupCode>>

    @get:Query("select * from group_code")
    val allData: List<GroupCode>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<GroupCode>)

    @Query("Delete from group_code")
    fun deleteAll()

    @Query("SELECT * FROM group_code WHERE group_code.id = :groupNo")
    fun selectGroupCodeName(groupNo:Int):List<GroupCode>

}
package com.aceplus.data.database.dao.predefine

import com.aceplus.domain.entity.predefine.Township
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.aceplus.domain.model.roomdb.StringObject


@Dao
interface TownshipDao{

    @get:Query("select * from township")
    val allDataLD: LiveData<List<Township>>

    @get:Query("select * from township")
    val allData: List<Township>

    @Query("select township_name as data from township where id = (select township_id from customer where id = :customerID)")
    fun townshipNameByID(customerID: Int): StringObject?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<Township>)

    @Query("Delete from township")
    fun deleteAll()



}
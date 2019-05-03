package com.aceplus.data.database.dao

import com.aceplus.domain.entity.SMSRecord
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface SMSRecordDao{

    @get:Query("select * from sms_record")
    val allDataLD: LiveData<List<SMSRecord>>

    @get:Query("select * from sms_record")
    val allData: List<SMSRecord>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<SMSRecord>)

    @Query("Delete from sms_record")
    fun deleteAll()

}
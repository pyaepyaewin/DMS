package com.aceplus.data.database.dao.deviceissue

import com.aceplus.domain.entity.deviceissue.DeviceIssueRequestItem
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface DeviceIssueRequestItemDao {

    @get:Query("select * from device_issue_request_item")
    val allDataLD: LiveData<List<DeviceIssueRequestItem>>

    @get:Query("select * from device_issue_request_item")
    val allData: List<DeviceIssueRequestItem>

    @Query("select * from device_issue_request_item where invoice_no =:invoice_no")
    fun allDataById(invoice_no: String?): List<DeviceIssueRequestItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<DeviceIssueRequestItem>)

    @Query("Delete from device_issue_request_item")
    fun deleteAll()


}
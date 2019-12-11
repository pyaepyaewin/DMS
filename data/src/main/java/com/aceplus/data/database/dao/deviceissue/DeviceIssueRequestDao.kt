package com.aceplus.data.database.dao.deviceissue

import com.aceplus.domain.entity.deviceissue.DeviceIssueRequest
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

@Dao
interface DeviceIssueRequestDao {

    @get:Query("select * from device_issue_request")
    val allDataLD: LiveData<List<DeviceIssueRequest>>

    @get:Query("select * from device_issue_request")
    val allData: List<DeviceIssueRequest>

    @get:Query("select count(*) from device_issue_request")
    val dataCount: Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<DeviceIssueRequest>)

    @Query("Delete from device_issue_request")
    fun deleteAll()

    @Query("select * from device_issue_request where invoice_no = :invoiceNo")
    fun getDataByID(invoiceNo: String): List<DeviceIssueRequest>

}
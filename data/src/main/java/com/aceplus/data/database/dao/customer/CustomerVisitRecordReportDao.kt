package com.aceplus.data.database.dao.customer

import com.aceplus.domain.entity.customer.CustomerVisitRecordReport
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.aceplus.domain.vo.report.SalesVisitHistoryReport


@Dao
interface CustomerVisitRecordReportDao {

    @get:Query("select * from customer_visit_record_report")
    val allDataLD: LiveData<List<CustomerVisitRecordReport>>

    @get:Query("select * from customer_visit_record_report")
    val allData: List<CustomerVisitRecordReport>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<CustomerVisitRecordReport>)

    @Query("Delete from customer_visit_record_report")
    fun deleteAll()

    @Query("select customer_name,address,status,sale_status from customer inner join credit on credit.customer_id = customer.id inner join pre_order_present")
    fun getSalesVisitHistoryReport():List<SalesVisitHistoryReport>
}

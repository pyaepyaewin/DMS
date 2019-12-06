package com.aceplus.data.database.dao.customer

import com.aceplus.domain.entity.customer.CustomerFeedback
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.aceplus.domain.vo.report.UnsellReasonReport


@Dao
interface CustomerFeedbackDao {

    @get:Query("select * from customer_feedback")
    val allDataLD: LiveData<List<CustomerFeedback>>

    @get:Query("select * from customer_feedback")
    val allData: List<CustomerFeedback>

//    @get:Query("select * from customer_feedback")
//    val allObservableData: Observable<List<CustomerFeedback>>

    @get:Query("select * from customer_feedback")
    val allObservableData: List<CustomerFeedback>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<CustomerFeedback>)

    @Query("Delete from customer_feedback")
    fun deleteAll()

    @Query("select customer_name,description,remark from did_customer_feedback inner join customer on customer.id = did_customer_feedback.customer_no")
    fun getUnSellReasonReport(): List<UnsellReasonReport>

}

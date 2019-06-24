package com.aceplus.data.database.dao.customer

import com.aceplus.domain.entity.customer.DidCustomerFeedback
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Observable


@Dao
interface DidCustomerFeedbackDao {

    @get:Query("select * from did_customer_feedback")
    val allDataLD: LiveData<List<DidCustomerFeedback>>

    @get:Query("select * from did_customer_feedback")
    val allData: List<DidCustomerFeedback>

    @get:Query("select customer_no from did_customer_feedback")
    val getAllCustomerIdList: Observable<List<String>>

    @get:Query("select COUNT(*) from did_customer_feedback")
    val dataCount: Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<DidCustomerFeedback>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(didCustomerFeedbackEntity: DidCustomerFeedback)

    @Query("Delete from did_customer_feedback")
    fun deleteAll()


}

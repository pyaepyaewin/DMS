package com.aceplus.data.database.dao.preorder

import com.aceplus.domain.entity.preorder.PreOrder
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.aceplus.domain.model.report.SalesOrderHistoryReport


@Dao
interface PreOrderDao {

    @get:Query("select * from pre_order")
    val allDataLD: LiveData<List<PreOrder>>

//    @get:Query("select count(*) from pre_order")
//    val allData: List<PreOrder>

    @get:Query("select * from pre_order")
    val allData: List<PreOrder>

    @get:Query("select * from pre_order WHERE delete_flag = 0")
    val allActiveData: List<PreOrder>

    @get:Query("select count(*) from pre_order WHERE delete_flag = 0")
    val dataCount: Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<PreOrder>)

    @Query("Delete from pre_order")
    fun deleteAll()

    @Query("update pre_order set delete_flag = 1 WHERE delete_flag = 0")
    fun updateAllInactiveData()

    @Query("select invoice.invoice_id,customer_name,address,total_amount,discount,advance_payment_amount,net_amount from invoice inner join pre_order on invoice.invoice_id = pre_order.invoice_id inner join customer on customer.id =  invoice.customer_id ")
    fun getSalesOrderHistoryReport(): List<SalesOrderHistoryReport>

}
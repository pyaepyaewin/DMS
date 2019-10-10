package com.aceplus.data.database.dao.sale.salereturn

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.aceplus.domain.entity.sale.salereturn.SaleReturn
import com.aceplus.domain.vo.report.SalesReturnReport


@Dao
interface SaleReturnDao {

    @get:Query("select * from sale_return")
    val allDataLD: LiveData<List<SaleReturn>>

    @get:Query("select * from sale_return")
    val allData: List<SaleReturn>

    @get:Query("select * from sale_return WHERE delete_flag = 0")
    val allActiveData: List<SaleReturn>

    @get:Query("select count(*) from sale_return WHERE sale_return_id LIKE 'SX%'")
    val dataCountForSaleReturnExchange: Int

    @get:Query("select count(*) from sale_return WHERE sale_return_id LIKE 'SR%'")
    val dataCount: Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<SaleReturn>)

    @Query("Delete from sale_return")
    fun deleteAll()

    @Query("update sale_return set delete_flag = 1 WHERE delete_flag = 0")
    fun updateAllInactiveData()

    @Query("select invoice.invoice_id,customer_name,address,return_date,total_quantity,total_amount from invoice left join customer on customer.customer_id = invoice.customer_id left join sale_return on sale_return.customer_id = customer.customer_id")
    fun getSalesReturnReport(): List<SalesReturnReport>

}
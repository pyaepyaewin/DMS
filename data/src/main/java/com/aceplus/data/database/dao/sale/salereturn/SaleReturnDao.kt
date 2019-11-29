package com.aceplus.data.database.dao.sale.salereturn

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.aceplus.domain.entity.sale.salereturn.SaleReturn
import com.aceplus.domain.vo.report.SalesReturnQtyReport
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

    @Query("select count(*) from sale_return where sale_return_id = :id")
    fun getSaleReturnCountByID(id: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<SaleReturn>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(saleReturn: SaleReturn)

    @Query("Delete from sale_return")
    fun deleteAll()

    @Query("update sale_return set delete_flag = 1 WHERE delete_flag = 0")
    fun updateAllInactiveData()


    @Query("update sale_return set sale_id = :saleID where sale_return_id = :saleReturnInvoiceNo")
    fun updateSaleIdInSaleReturn(saleReturnInvoiceNo: String, saleID: String)

    @Query("select sale_return_id,customer_name,address,return_date,pay_amount from  sale_return inner join customer on customer.id = sale_return.customer_id where sale_return.sale_return_id like 'SR%'")
    fun getSalesReturnQtyReport(): List<SalesReturnQtyReport>

    @Query("select sale_return_id,customer_name,address,return_date,pay_amount from  sale_return inner join customer on customer.id = sale_return.customer_id where sale_return.sale_return_id like 'SX%'")
    fun getSalesExchangeTab1(): List<SalesReturnQtyReport>

}
package com.aceplus.data.database.dao.sale.salereturn

import com.aceplus.domain.entity.sale.salereturn.SaleReturnDetail
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

import com.aceplus.domain.entity.product.Product
import com.aceplus.domain.vo.SaleExchangeProductInfo
import com.aceplus.domain.vo.report.SalesReturnDetailReport
import com.aceplus.domain.vo.report.SalesReturnReport



@Dao
interface SaleReturnDetailDao {

    @get:Query("select * from sale_return_detail")
    val allDataLD: LiveData<List<SaleReturnDetail>>

    @get:Query("select * from sale_return_detail")
    val allData: List<SaleReturnDetail>

    @Query("select * from sale_return_detail WHERE sale_return_id = :sale_return_id AND delete_flag = 0")
    fun allActiveDataById(sale_return_id: String): List<SaleReturnDetail>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<SaleReturnDetail>)

    @Query("Delete from sale_return_detail")
    fun deleteAll()

    @Query("update sale_return_detail set delete_flag = 1 WHERE delete_flag = 0")
    fun updateAllInactiveData()


    @Query("select p.product_name, sr.quantity, sr.price from sale_return_detail as sr left join product as p on sr.product_id = p.id where sr.sale_return_id = :saleReturnInvoiceNo")
    fun getSaleReturnProductInfo(saleReturnInvoiceNo: String): List<SaleExchangeProductInfo>

    @Query("select * from  sale_return_detail where sale_return_id in (:idList) ")
    fun getSalesReturnReport(idList:List<String>): List<SaleReturnDetail>


    @Query("select product_name,quantity from  sale_return_detail inner join product on product.id = sale_return_detail.product_id where sale_return_detail.sale_return_id = :invoiceId ")
    fun getSalesReturnDetailList(invoiceId: String): List<SalesReturnDetailReport>?
}
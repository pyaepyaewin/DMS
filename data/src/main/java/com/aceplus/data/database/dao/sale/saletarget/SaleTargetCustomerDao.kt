package com.aceplus.data.database.dao.sale.saletarget

import com.aceplus.domain.entity.sale.saletarget.SaleTargetCustomer
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.aceplus.domain.vo.report.SaleTargetVO


@Dao
interface SaleTargetCustomerDao{

    @get:Query("select * from sale_target_customer")
    val allDataLD: LiveData<List<SaleTargetCustomer>>

    @get:Query("select * from sale_target_customer")
    val allData: List<SaleTargetCustomer>

    @Query("select * from sale_target_customer where sale_target_customer.customer_id=:customerId")
    fun dataById(customerId: Int): List<SaleTargetCustomer>

    @Query("select invoice_product.total_amount,invoice_product.sale_quantity,product.product_id from invoice_product,product,invoice where product.id = invoice_product.product_id and invoice.customer_id = :customerId and product.group_id = :groupId and product.category_id = :categoryId")
    fun actualSaleData(customerId:Int,groupId:Int,categoryId:Int): List<SaleTargetVO>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<SaleTargetCustomer>)

    @Query("Delete from sale_target_customer")
    fun deleteAll()

}
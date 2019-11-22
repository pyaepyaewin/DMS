package com.aceplus.data.database.dao.preorder

import com.aceplus.domain.entity.preorder.PreOrder
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.aceplus.domain.vo.report.PreOrderReport
import com.aceplus.domain.vo.report.SalesOrderHistoryFullDataReport
import com.aceplus.domain.vo.report.SalesOrderHistoryReport


@Dao
interface PreOrderDao {

    @get:Query("select * from pre_order")
    val allDataLD: LiveData<List<PreOrder>>

    @get:Query("select * from pre_order")
    val allData: List<PreOrder>

    @get:Query("select * from pre_order WHERE delete_flag = 0")
    val allActiveData: List<PreOrder>

    @get:Query("select count(*) from pre_order WHERE delete_flag = 0")
    val dataCount: Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<PreOrder>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(preOrder: PreOrder)

    @Query("Delete from pre_order")
    fun deleteAll()

    @Query("update pre_order set delete_flag = 1 WHERE delete_flag = 0")
    fun updateAllInactiveData()

    @Query("select invoice_id,customer_name,address,discount,advance_payment_amount,net_amount from pre_order inner join customer on customer.id = pre_order.customer_id where sale_flag = 1 ")
    fun getSalesOrderHistoryReport(): List<SalesOrderHistoryFullDataReport>

    @Query("update pre_order set delete_flag = 1 WHERE delete_flag = 0 and invoice_id = :id")
    fun updateInactivePreOrderByID(id: String)

    @Query("select invoice_id,customer_name,advance_payment_amount,net_amount from pre_order inner join customer on customer.id = pre_order.customer_id where sale_flag = 0")
    fun getPreOrderReport(): List<PreOrderReport>

    @Query("select count(*) from pre_order where invoice_id = :invoiceId")
    fun getOrderInvoiceCountByID(invoiceId: String): Int

    @Query("select po.invoice_id, c.customer_name as customer_id, sm.user_name as sale_man_id, po.dev_id, pre_order_date, po.expected_delivery_date, po.advance_payment_amount, po.net_amount, po.location_id, po.discount, po.discount_percent, po.volume_discount, po.volume_discount_percent, po.tax_amount, po.bank_name, po.bank_account_no, po.remark, po.delete_flag, po.sale_flag from pre_order as po left join customer as c on po.customer_id = c.id left join sale_man as sm on po.sale_man_id = sm.id where invoice_id = :invoiceId and delete_flag = 0")
    fun getActivePreOrderByIDWithName(invoiceId: String): List<PreOrder>

}
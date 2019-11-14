package com.aceplus.data.database.dao.preorder

import com.aceplus.domain.entity.preorder.PreOrder
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.aceplus.domain.vo.report.PreOrderReport
import com.aceplus.domain.vo.report.SalesOrderHistoryReport


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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(preOrder: PreOrder)

    @Query("Delete from pre_order")
    fun deleteAll()

    @Query("update pre_order set delete_flag = 1 WHERE delete_flag = 0")
    fun updateAllInactiveData()

    @Query("select invoice.invoice_id,customer_name,address,total_amount,discount,advance_payment_amount,net_amount from invoice inner join pre_order on invoice.invoice_id = pre_order.invoice_id inner join customer on customer.id =  invoice.customer_id ")
    fun getSalesOrderHistoryReport(): List<SalesOrderHistoryReport>

    @Query("select invoice.invoice_id,customer_name,address,total_quantity,prepaid_amount,total_amount from invoice inner join customer on customer.id = invoice.customer_id inner join pre_order on pre_order.invoice_id =  invoice.invoice_id ")
    fun getPreOrderReport(): List<PreOrderReport>

    @Query("select count(*) from pre_order where invoice_id = :invoiceId")
    fun getOrderInvoiceCountByID(invoiceId: String): Int

    @Query("select po.invoice_id, po.customer_id, c.customer_name, sale_man_id, sm.user_name as sale_man_name, dev_id, pre_order_date, expected_delivery_date, advance_payment_amount, net_amount, location_id, discount, discount_percent, volume_discount, volume_discount_percent, tax_amount, bank_name, bank_account_no, remark, delete_flag, sale_flag from pre_order as po left join customer as c on po.customer_id = c.id left join sale_man as sm on po.sale_man_id = sm.id where invoice_id = :invoiceId")
    fun getPreOrderByID(invoiceId: String): List<PreOrder>

}
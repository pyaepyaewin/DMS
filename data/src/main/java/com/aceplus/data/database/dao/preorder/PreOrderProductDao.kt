package com.aceplus.data.database.dao.preorder

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.aceplus.domain.entity.preorder.PreOrderProduct
import com.aceplus.domain.vo.report.PreOrderDetailReport


@Dao
interface PreOrderProductDao {

    @get:Query("select * from pre_order_product")
    val allDataLD: LiveData<List<PreOrderProduct>>

    @get:Query("select * from pre_order_product")
    val allData: List<PreOrderProduct>

    @Query("select pop.id, pop.sale_order_id, p.product_name as product_id, pop.order_quantity, pop.price, pop.total_amount, pop.promotion_price, pop.promotion_plan_id, pop.volume_discount, pop.volume_discount_percent, pop.item_discount_percent, pop.item_discount_amount, pop.exclude, pop.delete_flag from pre_order_product as pop left join product as p on pop.product_id = p.id where pop.sale_order_id = :invoiceId")
    fun getPreOrderProductByInvoiceID(invoiceId: String): List<PreOrderProduct>

    @Query("select * from pre_order_product WHERE sale_order_id =:invoice_id AND delete_flag = 0")
    fun allDataById(invoice_id: String?): List<PreOrderProduct>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<PreOrderProduct>)

    @Query("Delete from pre_order_product")
    fun deleteAll()

    @Query("update pre_order_product set delete_flag = 1 WHERE delete_flag = 0")
    fun updateAllInactiveData()

    @Query("select product.product_name,pre_order_product.order_quantity,pre_order_product.total_amount from pre_order_product inner join product on product.id = pre_order_product.product_id where pre_order_product.sale_order_id = :invoiceId ")
    fun getPreOrderDetailReport(invoiceId: String): List<PreOrderDetailReport>


}
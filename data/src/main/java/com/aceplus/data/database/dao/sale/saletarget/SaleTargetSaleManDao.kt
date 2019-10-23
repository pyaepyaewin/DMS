package com.aceplus.data.database.dao.sale.saletarget

import com.aceplus.domain.entity.sale.saletarget.SaleTargetSaleMan
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.aceplus.domain.model.Product
import com.aceplus.domain.vo.report.TargetAndActualSaleForProduct


@Dao
interface SaleTargetSaleManDao{

    @get:Query("select * from sale_target_sale_man")
    val allDataLD: LiveData<List<SaleTargetSaleMan>>

    @get:Query("select * from sale_target_sale_man")
    val allData: List<SaleTargetSaleMan>

    @get:Query("select * from sale_target_sale_man,product_category,group_code,product WHERE product_category.id = sale_target_sale_man.category_id and group_code.id = sale_target_sale_man.group_code_id and product.id = sale_target_sale_man.stock_id")
    val allDataWithName: List<TargetAndActualSaleForProduct>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<SaleTargetSaleMan>)

    @Query("Delete from sale_target_sale_man")
    fun deleteAll()



}
package com.aceplus.data.database.dao.preorder

import com.aceplus.domain.entity.preorder.PreOrderProduct
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface PreOrderProductDao {

    @get:Query("select * from pre_order_product")
    val allDataLD: LiveData<List<PreOrderProduct>>

    @get:Query("select * from pre_order_product")
    val allData: List<PreOrderProduct>

    @Query("select * from pre_order_product WHERE sale_order_id =:invoice_id AND delete_flag = 0")
    fun allDataById(invoice_id: String?): List<PreOrderProduct>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<PreOrderProduct>)

    @Query("Delete from pre_order_product")
    fun deleteAll()

    @Query("update pre_order_product set delete_flag = 1 WHERE delete_flag = 0")
    fun updateAllInactiveData()


}
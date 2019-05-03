package com.aceplus.data.database.dao.sale.saletarget

import com.aceplus.domain.entity.sale.saletarget.SaleTargetCustomer
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface SaleTargetCustomerDao{

    @get:Query("select * from sale_target_customer")
    val allDataLD: LiveData<List<SaleTargetCustomer>>

    @get:Query("select * from sale_target_customer")
    val allData: List<SaleTargetCustomer>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<SaleTargetCustomer>)

    @Query("Delete from sale_target_customer")
    fun deleteAll()

}
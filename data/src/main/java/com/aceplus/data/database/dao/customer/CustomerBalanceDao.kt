package com.aceplus.data.database.dao.customer

import com.aceplus.domain.entity.customer.CustomerBalance
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface CustomerBalanceDao {

    @get:Query("select * from customer_balance")
    val allDataLD: LiveData<List<CustomerBalance>>

    @get:Query("select * from customer_balance")
    val allData: List<CustomerBalance>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<CustomerBalance>)

    @Query("Delete from customer_balance")
    fun deleteAll()

}
package com.aceplus.data.database.dao.customer

import com.aceplus.domain.entity.customer.Customer

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.aceplus.domain.model.forApi.customer.CustomerForApi
import com.aceplus.domain.model.forApi.customer.ExistingCustomerForApi

@Dao
interface CustomerDao {

    @get:Query("select * from customer")
    val allDataLD: LiveData<List<Customer>>

    @get:Query("select * from customer")
    val allData: List<Customer>

    @get:Query("select id from customer")
    val allID: List<Int>

    @get:Query("select * from customer where flag =1")
    val customerList: List<CustomerForApi>

    @get:Query("select * from customer where flag =2")
    val existingCustomerList: List<ExistingCustomerForApi>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<Customer>)

    @Query("Delete from customer")
    fun deleteAll()

}

package com.aceplus.data.database.dao.customer

import android.arch.lifecycle.LiveData
import com.aceplus.domain.entity.customer.CustomerCategory
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface CustomerCategoryDao {

    @get:Query("select * from customer_category")
    val allDataLD: LiveData<List<CustomerCategory>>

    @get:Query("select * from customer_category")
    val allData: List<CustomerCategory>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<CustomerCategory>)

    @Query("Delete from customer_category")
    fun deleteAll()

}

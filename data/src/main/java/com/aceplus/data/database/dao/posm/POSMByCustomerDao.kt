package com.aceplus.data.database.dao.posm

import com.aceplus.domain.entity.posm.POSMByCustomer
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface POSMByCustomerDao{

    @get:Query("select * from posm_by_customer")
    val allDataLD: LiveData<List<POSMByCustomer>>

    @get:Query("select * from posm_by_customer")
    val allData: List<POSMByCustomer>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<POSMByCustomer>)

    @Query("Delete from posm_by_customer")
    fun deleteAll()

}
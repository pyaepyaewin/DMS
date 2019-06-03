package com.aceplus.data.database.dao.invoice

import com.aceplus.domain.entity.invoice.Invoice
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface InvoiceDao{

    @get:Query("select * from invoice")
    val allDataLD: LiveData<List<Invoice>>

    @get:Query("select * from invoice")
    val allData: List<Invoice>

    @get:Query("select * from invoice where sale_flag = 1")
    val activeData:List<Invoice>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<Invoice>)

    @Query("Delete from invoice")
    fun deleteAll()

}
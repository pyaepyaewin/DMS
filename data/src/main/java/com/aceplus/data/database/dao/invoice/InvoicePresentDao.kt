package com.aceplus.data.database.dao.invoice

import com.aceplus.domain.entity.invoice.InvoicePresent
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface InvoicePresentDao{

    @get:Query("select * from invoice_present")
    val allDataLD: LiveData<List<InvoicePresent>>

    @get:Query("select * from invoice_present")
    val allData: List<InvoicePresent>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<InvoicePresent>)

    @Query("Delete from invoice_present")
    fun deleteAll()

}
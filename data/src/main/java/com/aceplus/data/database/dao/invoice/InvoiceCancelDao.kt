package com.aceplus.data.database.dao.invoice

import com.aceplus.domain.entity.invoice.InvoiceCancel
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface InvoiceCancelDao{

    @get:Query("select * from invoice_cancel")
    val allDataLD: LiveData<List<InvoiceCancel>>

    @get:Query("select * from invoice_cancel")
    val allData: List<InvoiceCancel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<InvoiceCancel>)

    @Query("Delete from invoice_cancel")
    fun deleteAll()

}
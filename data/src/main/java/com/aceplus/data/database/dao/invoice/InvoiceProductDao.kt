package com.aceplus.data.database.dao.invoice

import com.aceplus.domain.entity.invoice.InvoiceProduct
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface InvoiceProductDao{

    @get:Query("select * from invoice_product")
    val allDataLD: LiveData<List<InvoiceProduct>>

    @get:Query("select * from invoice_product")
    val allData: List<InvoiceProduct>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<InvoiceProduct>)

    @Query("Delete from invoice_product")
    fun deleteAll()

}
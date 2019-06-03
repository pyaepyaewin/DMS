package com.aceplus.data.database.dao.invoice

import com.aceplus.domain.entity.invoice.InvoiceCancelProduct
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

@Dao
interface InvoiceCancelProductDao {

    @get:Query("select * from invoice_cancel_product")
    val allDataLD: LiveData<List<InvoiceCancelProduct>>

    @get:Query("select * from invoice_cancel_product")
    val allData: List<InvoiceCancelProduct>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<InvoiceCancelProduct>)

    @Query("Delete from invoice_cancel_product")
    fun deleteAll()


    fun allDataById(id: String?): List<InvoiceCancelProduct>

}
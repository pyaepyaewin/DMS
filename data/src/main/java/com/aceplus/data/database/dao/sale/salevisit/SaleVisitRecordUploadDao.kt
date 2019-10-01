package com.aceplus.data.database.dao.sale.salevisit

import com.aceplus.domain.entity.sale.salevisit.SaleVisitRecordUpload
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.aceplus.domain.entity.customer.Customer

@Dao
interface SaleVisitRecordUploadDao {

    @get:Query("select * from sale_visit_record_upload")
    val allDataLD: LiveData<List<SaleVisitRecordUpload>>

    @get:Query("select * from sale_visit_record_upload")
    val allData: List<SaleVisitRecordUpload>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<SaleVisitRecordUpload>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveData(selectedCustomer: Customer)

    @Query("Delete from sale_visit_record_upload")
    fun deleteAll()

    @Delete
    fun deleteData(selectedCustomer: Customer)


}
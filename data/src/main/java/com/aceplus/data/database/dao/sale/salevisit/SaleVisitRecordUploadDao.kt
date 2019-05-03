package com.aceplus.data.database.dao.sale.salevisit

import com.aceplus.domain.entity.sale.salevisit.SaleVisitRecordUpload
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface SaleVisitRecordUploadDao{

    @get:Query("select * from sale_visit_record_upload")
    val allDataLD: LiveData<List<SaleVisitRecordUpload>>

    @get:Query("select * from sale_visit_record_upload")
    val allData: List<SaleVisitRecordUpload>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<SaleVisitRecordUpload>)

    @Query("Delete from sale_visit_record_upload")
    fun deleteAll()

}
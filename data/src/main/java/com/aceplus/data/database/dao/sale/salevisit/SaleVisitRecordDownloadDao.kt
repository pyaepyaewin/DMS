package com.aceplus.data.database.dao.sale.salevisit

import com.aceplus.domain.entity.sale.salevisit.SaleVisitRecordDownload
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface SaleVisitRecordDownloadDao{

    @get:Query("select * from sale_visit_record_download")
    val allDataLD: LiveData<List<SaleVisitRecordDownload>>

    @get:Query("select * from sale_visit_record_download")
    val allData: List<SaleVisitRecordDownload>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<SaleVisitRecordDownload>)

    @Query("Delete from sale_visit_record_download")
    fun deleteAll()

}
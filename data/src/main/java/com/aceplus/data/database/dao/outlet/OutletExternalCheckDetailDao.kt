package com.aceplus.data.database.dao.outlet

import com.aceplus.domain.entity.outlet.OutletExternalCheckDetail
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface OutletExternalCheckDetailDao{

    @get:Query("select * from outlet_external_check_detail")
    val allDataLD: LiveData<List<OutletExternalCheckDetail>>

    @get:Query("select * from outlet_external_check_detail")
    val allData: List<OutletExternalCheckDetail>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<OutletExternalCheckDetail>)

    @Query("Delete from outlet_external_check_detail")
    fun deleteAll()

}
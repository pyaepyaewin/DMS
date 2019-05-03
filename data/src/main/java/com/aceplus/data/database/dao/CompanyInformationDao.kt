package com.aceplus.data.database.dao

import com.aceplus.domain.entity.CompanyInformation

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

@Dao
interface CompanyInformationDao{

    @get:Query("select * from company_information")
    val allDataLD: LiveData<List<CompanyInformation>>

    @get:Query("select * from company_information")
    val allData: List<CompanyInformation>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<CompanyInformation>)

    @Query("Delete from company_information")
    fun deleteAll()

}
package com.aceplus.data.database.dao.sale

import com.aceplus.domain.entity.sale.SaleMan
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface SaleManDao {

    @get:Query("select * from sale_man")
    val allDataLD: LiveData<List<SaleMan>>

    @get:Query("select * from sale_man")
    val allData: List<SaleMan>

    @Query("select * from sale_man where user_id = :userId and password = :password")
    fun data(userId: String, password: String): List<SaleMan>

    @Query("select user_name from sale_man where id = :saleManID")
    fun getSaleManNameByID(saleManID: String): List<String?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<SaleMan>)

    @Query("Delete from sale_man")
    fun deleteAll()

}
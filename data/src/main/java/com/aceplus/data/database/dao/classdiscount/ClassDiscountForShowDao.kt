package com.aceplus.data.database.dao.classdiscount

import com.aceplus.domain.entity.classdiscount.ClassDiscountForShow
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query


@Dao
interface ClassDiscountForShowDao {

    @get:Query("select * from class_discount_for_show")
    val allDataLD: LiveData<List<ClassDiscountForShow>>

    @get:Query("select * from class_discount_for_show")
    val allData: List<ClassDiscountForShow>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<ClassDiscountForShow>)

    @Query("Delete from class_discount_for_show")
    fun deleteAll()

}

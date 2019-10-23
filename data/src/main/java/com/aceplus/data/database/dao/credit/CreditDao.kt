package com.aceplus.data.database.dao.credit

import com.aceplus.domain.entity.credit.Credit
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.aceplus.domain.model.creditcollectiondataclass.CreditCollectionDataClass


@Dao
interface CreditDao {

    @get:Query("select * from credit")
    val allDataLD: LiveData<List<Credit>>

    @get:Query("select * from credit")
    val allData: List<Credit>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<Credit>)

    @Query("Delete from credit")
    fun deleteAll()

    @Query("select Cus.customer_name,Cb.balance,C.pay_amount,C.amount from customer as Cus,credit as C,customer_balance as Cb where Cus.id=Cb.customer_id and C.customer_id=Cb.customer_id")
    fun getCreditCollection():List<CreditCollectionDataClass>

}

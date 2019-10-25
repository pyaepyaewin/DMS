package com.aceplus.data.database.dao.credit

import com.aceplus.domain.entity.credit.CreditItem
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import android.database.Observable
import com.aceplus.domain.model.creditcollectiondataclass.CreditCollectionCheckoutDataClass


@Dao
interface CreditItemDao {

    @get:Query("select * from credit_item")
    val allDataLD: LiveData<List<CreditItem>>

    @get:Query("select * from credit_item")
    val allData: List<CreditItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<CreditItem>)

    @Query("Delete from credit_item")
    fun deleteAll()

    @Query("select credit.invoice_no,credit.invoice_date,credit.amount,credit.pay_amount from credit where credit.customer_id=:customerId")
    fun getCreditCheckout(customerId:String):List<CreditCollectionCheckoutDataClass>

}

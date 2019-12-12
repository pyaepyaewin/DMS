package com.aceplus.domain.repo.creditcollectionrepo

import com.aceplus.domain.entity.Location
import com.aceplus.domain.entity.cash.CashReceive
import com.aceplus.domain.entity.cash.CashReceiveItem
import com.aceplus.domain.entity.credit.Credit
import com.aceplus.domain.model.creditcollectiondataclass.CreditCollectionDataClass
import io.reactivex.Observable

interface CreditCollectionRepo {
    fun getCreditCollectionList(): Observable<List<CreditCollectionDataClass>>
    fun getCreditCollectionCheckOutList(customerId:String): Observable<List<Credit>>
    fun saveCashReceiveDataIntoDatabase(creditData: MutableList<CashReceive>, creditItem:MutableList<CashReceiveItem>)
    fun updatePayAmount(payAmt:Double,invoiceNo:String)
    fun getLocation():String
    fun getTownShipName(customerID:Int):String
    fun getCashReceiveCount():Observable<Int>
}
package com.aceplus.domain.repo.creditcollectionrepo

import com.aceplus.domain.entity.credit.Credit
import com.aceplus.domain.model.credit.CreditInvoice
import io.reactivex.Observable

interface CreditCollectionCheckOutRepo {
    fun getCreditCollectionCheckOutList(customerId:String): Observable<List<Credit>>
    fun saveDataIntoDatabase(creditDataItems: MutableList<Credit>)


}
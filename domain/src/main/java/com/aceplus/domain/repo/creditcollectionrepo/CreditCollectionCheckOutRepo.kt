package com.aceplus.domain.repo.creditcollectionrepo

import com.aceplus.domain.model.creditcollectiondataclass.CreditCollectionCheckoutDataClass
import io.reactivex.Observable

interface CreditCollectionCheckOutRepo {
    fun getCreditCollectionCheckOutList(customerId:String): Observable<List<CreditCollectionCheckoutDataClass>>

}
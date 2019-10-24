package com.aceplus.data.repoimpl.creditcollectionrepoimpl

import com.aceplus.data.database.MyDatabase
import com.aceplus.domain.model.creditcollectiondataclass.CreditCollectionCheckoutDataClass
import com.aceplus.domain.repo.creditcollectionrepo.CreditCollectionCheckOutRepo
import io.reactivex.Observable

class CreditCollectionCheckoutRepoImpl(val database:MyDatabase):CreditCollectionCheckOutRepo {
    override fun getCreditCollectionCheckOutList(customerId:String): Observable<List<CreditCollectionCheckoutDataClass>> {
        return Observable.just(database.creditItemDao().getCreditCheckout(customerId))

    }
}
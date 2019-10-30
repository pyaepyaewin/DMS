package com.aceplus.data.repoimpl.creditcollectionrepoimpl

import com.aceplus.data.database.MyDatabase
import com.aceplus.domain.entity.credit.Credit
import com.aceplus.domain.repo.creditcollectionrepo.CreditCollectionCheckOutRepo
import io.reactivex.Observable

class CreditCollectionCheckoutRepoImpl(val database:MyDatabase):CreditCollectionCheckOutRepo {
    override fun saveDataIntoDatabase(creditDataItems: MutableList<Credit>) {

    }

    override fun getCreditCollectionCheckOutList(customerId:String): Observable<List<Credit>> {
        return Observable.just(database.creditDao().getCreditCheckout(customerId))

    }
}
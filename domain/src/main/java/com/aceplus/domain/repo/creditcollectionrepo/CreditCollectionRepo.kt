package com.aceplus.domain.repo.creditcollectionrepo

import com.aceplus.domain.model.creditcollectiondataclass.CreditCollectionDataClass
import io.reactivex.Observable

interface CreditCollectionRepo {
    fun getCreditCollectionList(): Observable<List<CreditCollectionDataClass>>
}
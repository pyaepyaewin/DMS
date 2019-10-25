package com.aceplus.data.repoimpl.creditcollectionrepoimpl

import com.aceplus.data.database.MyDatabase
import com.aceplus.domain.model.creditcollectiondataclass.CreditCollectionDataClass
import com.aceplus.domain.repo.creditcollectionrepo.CreditCollectionRepo
import io.reactivex.Observable

class CreditCollectionRepoImpl(val database: MyDatabase):CreditCollectionRepo {
    override fun getCreditCollectionList(): Observable<List<CreditCollectionDataClass>> {
        return Observable.just(database.creditDao().getCreditCollection())

    }

    }
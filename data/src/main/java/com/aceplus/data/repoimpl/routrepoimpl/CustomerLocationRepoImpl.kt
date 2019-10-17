package com.aceplus.data.repoimpl.routrepoimpl

import com.aceplus.data.database.MyDatabase
import com.aceplus.domain.model.routedataclass.CustomerLocationDataClass
import com.aceplus.domain.repo.routerepo.CustomerLocationRepo
import io.reactivex.Observable

class CustomerLocationRepoImpl(val database: MyDatabase): CustomerLocationRepo {
    override fun getCustomerLocation(): Observable<List<CustomerLocationDataClass>> {
        return Observable.just(database.customerDao().getCustomerLocation())
    }
}
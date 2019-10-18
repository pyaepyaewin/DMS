package com.aceplus.domain.repo.routerepo

import com.aceplus.domain.model.routedataclass.CustomerLocationDataClass
import io.reactivex.Observable

interface CustomerLocationRepo {
    fun getCustomerLocation(): Observable<List<CustomerLocationDataClass>>

}
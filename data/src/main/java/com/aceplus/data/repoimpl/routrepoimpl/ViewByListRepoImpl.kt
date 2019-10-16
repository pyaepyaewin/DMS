package com.aceplus.data.repoimpl.routrepoimpl

import com.aceplus.data.database.MyDatabase
import com.aceplus.domain.model.routedataclass.TownshipDataClass
import com.aceplus.domain.model.routedataclass.ViewByListDataClass
import com.aceplus.domain.repo.routerepo.ViewByListRepo
import io.reactivex.Observable

class ViewByListRepoImpl(val database: MyDatabase): ViewByListRepo
 {
    override fun getTownShipDetail(township_id:String): Observable<List<ViewByListDataClass>> {
        return Observable.just(database.routeDao().getCustomerDetail(township_id))

    }

    override fun getTownShipList(): Observable<List<TownshipDataClass>> {
        return Observable.just(database.routeDao().getTownShipList())

    }
}
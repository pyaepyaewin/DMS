package com.aceplus.domain.repo.routerepo

import com.aceplus.domain.model.routedataclass.TownshipDataClass
import com.aceplus.domain.model.routedataclass.ViewByListDataClass
import io.reactivex.Observable

interface ViewByListRepo {
    fun getTownShipList(): Observable<List<TownshipDataClass>>
    fun getTownShipDetail(township_id:String):Observable<List<ViewByListDataClass>>

}
package com.aceplus.domain.repo.prospectcustomer

import com.aceplus.domain.entity.ShopType
import com.aceplus.domain.entity.predefine.District
import com.aceplus.domain.entity.predefine.StateDivision
import com.aceplus.domain.entity.predefine.Street
import com.aceplus.domain.entity.predefine.Township
import io.reactivex.Observable

interface ProspectCustomerRepo {
    fun streetData(): Observable<List<Street>>
    fun townshipData(): Observable<List<Township>>
    fun shopData(): Observable<List<ShopType>>
    fun distinctData(): Observable<List<District>>
    fun stateDivisionData(): Observable<List<StateDivision>>
}
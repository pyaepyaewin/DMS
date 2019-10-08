package com.aceplus.data.repoimpl.promotionrepoImpl

import com.aceplus.data.database.MyDatabase
import com.aceplus.domain.model.promotionDataClass.VolumeDiscountFilterDataClass
import com.aceplus.domain.repo.promotionrepo.VolumeDiscountFilterRepo
import io.reactivex.Observable

class VolumeDiscountFilterRepoImpl(val database: MyDatabase) : VolumeDiscountFilterRepo {
    override fun getVolumeDiscountFilterList(): Observable<List<VolumeDiscountFilterDataClass>> {
        return Observable.just(database.volumeDiscountFilterDao().getVolumeDiscountFilter())

    }
}
package com.aceplus.data.repoimpl.promotionrepoImpl

import com.aceplus.data.database.MyDatabase
import com.aceplus.domain.model.promotionDataClass.VolumeDiscountDataClass
import com.aceplus.domain.repo.promotionrepo.VolumeDiscountRepo
import io.reactivex.Observable

class VolumeDiscountRepoImpl(val database: MyDatabase) : VolumeDiscountRepo {
    override fun getVolumeDiscountList(): Observable<List<VolumeDiscountDataClass>> {
        return Observable.just(database.volumeDiscountDao().getVolumeDiscountList())


}
}
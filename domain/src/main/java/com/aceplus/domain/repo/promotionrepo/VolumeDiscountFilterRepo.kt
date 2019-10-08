package com.aceplus.domain.repo.promotionrepo

import com.aceplus.domain.model.promotionDataClass.VolumeDiscountFilterDataClass
import io.reactivex.Observable

interface VolumeDiscountFilterRepo {
    fun getVolumeDiscountFilterList(): Observable<List<VolumeDiscountFilterDataClass>>

}
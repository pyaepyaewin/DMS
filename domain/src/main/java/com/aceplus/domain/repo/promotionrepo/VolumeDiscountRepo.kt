package com.aceplus.domain.repo.promotionrepo

import com.aceplus.domain.model.promotionDataClass.VolumeDiscountDataClass
import io.reactivex.Observable

interface VolumeDiscountRepo {

    fun getVolumeDiscountList(): Observable<List<VolumeDiscountDataClass>>
}

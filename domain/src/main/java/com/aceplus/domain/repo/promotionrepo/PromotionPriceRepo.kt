package com.aceplus.domain.repo.promotionrepo

import com.aceplus.domain.model.promotionDataClass.PromotionPriceDataClass
import io.reactivex.Observable

interface PromotionPriceRepo {
    fun getPromotionPriceList(): Observable<List<PromotionPriceDataClass>>

}
package com.aceplus.domain.repo.promotionrepo

import com.aceplus.domain.model.promotionDataClass.ClassDiscountForShowPriceDataClass
import io.reactivex.Observable

interface ClassDiscountForShowPriceRepo {
    fun getClassDiscountForShowPrice(): Observable<List<ClassDiscountForShowPriceDataClass>>

}
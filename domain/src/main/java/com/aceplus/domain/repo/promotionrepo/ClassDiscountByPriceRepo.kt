package com.aceplus.domain.repo.promotionrepo

import com.aceplus.domain.model.promotionDataClass.ClassDiscountByPriceDataClass
import io.reactivex.Observable

interface ClassDiscountByPriceRepo {
    fun getClassDiscountByPrice(): Observable<List<ClassDiscountByPriceDataClass>>

}
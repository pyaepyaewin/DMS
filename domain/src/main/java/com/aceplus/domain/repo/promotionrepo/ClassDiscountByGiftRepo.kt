package com.aceplus.domain.repo.promotionrepo

import com.aceplus.domain.model.promotionDataClass.ClassDiscountByGiftDataClass
import io.reactivex.Observable

interface ClassDiscountByGiftRepo {
    fun getClassDiscountByGift(): Observable<List<ClassDiscountByGiftDataClass>>

}
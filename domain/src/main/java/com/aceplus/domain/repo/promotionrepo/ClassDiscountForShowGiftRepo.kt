package com.aceplus.domain.repo.promotionrepo

import com.aceplus.domain.model.promotionDataClass.ClassDiscountForShowGiftDataClass
import io.reactivex.Observable

interface ClassDiscountForShowGiftRepo {
    fun getClassDiscountForShowGift(): Observable<List<ClassDiscountForShowGiftDataClass>>

}
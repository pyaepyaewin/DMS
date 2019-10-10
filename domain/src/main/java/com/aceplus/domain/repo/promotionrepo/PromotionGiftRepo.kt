package com.aceplus.domain.repo.promotionrepo

import com.aceplus.domain.model.promotionDataClass.PromotionGiftDataClass
import io.reactivex.Observable

interface PromotionGiftRepo {
    fun getPromotionGiftList(): Observable<List<PromotionGiftDataClass>>

}
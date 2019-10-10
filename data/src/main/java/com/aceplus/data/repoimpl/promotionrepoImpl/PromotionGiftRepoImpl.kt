package com.aceplus.data.repoimpl.promotionrepoImpl

import com.aceplus.data.database.MyDatabase
import com.aceplus.domain.model.promotionDataClass.PromotionGiftDataClass
import com.aceplus.domain.repo.promotionrepo.PromotionGiftRepo
import io.reactivex.Observable

class PromotionGiftRepoImpl(private val database: MyDatabase): PromotionGiftRepo {
    override fun getPromotionGiftList(): Observable<List<PromotionGiftDataClass>> {
        return Observable.just(database.promotionGiftDao().getPromotionGiftForReport())

    }
}
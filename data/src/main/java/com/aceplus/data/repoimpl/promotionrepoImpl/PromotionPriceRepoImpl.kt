package com.aceplus.data.repoimpl.promotionrepoImpl

import com.aceplus.data.database.MyDatabase
import com.aceplus.domain.model.promotionDataClass.PromotionPriceDataClass
import com.aceplus.domain.repo.promotionrepo.PromotionPriceRepo
import io.reactivex.Observable

class PromotionPriceRepoImpl(val database: MyDatabase):PromotionPriceRepo {
    override fun getPromotionPriceList(): Observable<List<PromotionPriceDataClass>> {
        return Observable.just(database.promotionPriceDao().getPromotionPriceForReport())

    }
}
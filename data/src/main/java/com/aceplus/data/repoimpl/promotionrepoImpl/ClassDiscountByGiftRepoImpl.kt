package com.aceplus.data.repoimpl.promotionrepoImpl

import com.aceplus.data.database.MyDatabase
import com.aceplus.domain.model.promotionDataClass.ClassDiscountByGiftDataClass
import com.aceplus.domain.repo.promotionrepo.ClassDiscountByGiftRepo
import io.reactivex.Observable

class ClassDiscountByGiftRepoImpl(val database: MyDatabase):ClassDiscountByGiftRepo {
    override fun getClassDiscountByGift(): Observable<List<ClassDiscountByGiftDataClass>> {
        return Observable.just(database.classDiscountByPriceGiftDao().getClassDiscountByGiftList())

    }
}
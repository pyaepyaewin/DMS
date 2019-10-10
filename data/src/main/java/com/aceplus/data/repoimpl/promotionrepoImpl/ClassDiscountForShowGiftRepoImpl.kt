package com.aceplus.data.repoimpl.promotionrepoImpl

import com.aceplus.data.database.MyDatabase
import com.aceplus.domain.model.promotionDataClass.ClassDiscountForShowGiftDataClass
import com.aceplus.domain.repo.promotionrepo.ClassDiscountForShowGiftRepo
import io.reactivex.Observable

class ClassDiscountForShowGiftRepoImpl(val database: MyDatabase): ClassDiscountForShowGiftRepo {
    override fun getClassDiscountForShowGift(): Observable<List<ClassDiscountForShowGiftDataClass>> {
        return Observable.just(database.classDiscountForShowGiftDao().getClassDiscountForShowGift())

    }
}
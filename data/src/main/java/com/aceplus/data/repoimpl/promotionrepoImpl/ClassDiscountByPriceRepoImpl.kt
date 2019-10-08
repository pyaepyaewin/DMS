package com.aceplus.data.repoimpl.promotionrepoImpl

import com.aceplus.data.database.MyDatabase
import com.aceplus.domain.model.promotionDataClass.ClassDiscountByPriceDataClass
import com.aceplus.domain.repo.promotionrepo.ClassDiscountByPriceRepo
import io.reactivex.Observable

class ClassDiscountByPriceRepoImpl(val database: MyDatabase): ClassDiscountByPriceRepo {
    override fun getClassDiscountByPrice(): Observable<List<ClassDiscountByPriceDataClass>> {
        return Observable.just(database.classDiscountByPriceDao().getClassDiscountByPriceList())

    }
}
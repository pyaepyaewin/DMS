package com.aceplus.data.repoimpl.promotionrepoImpl

import com.aceplus.data.database.MyDatabase
import com.aceplus.domain.model.promotionDataClass.ClassDiscountForShowPriceDataClass
import com.aceplus.domain.repo.promotionrepo.ClassDiscountForShowPriceRepo
import io.reactivex.Observable

class ClassDiscountForShowPriceRepoImpl(val database: MyDatabase): ClassDiscountForShowPriceRepo {
    override fun getClassDiscountForShowPrice(): Observable<List<ClassDiscountForShowPriceDataClass>> {
        return Observable.just(database.classDiscountForShowItemDao().getClassDiscountForShowPriceList())
    }

}

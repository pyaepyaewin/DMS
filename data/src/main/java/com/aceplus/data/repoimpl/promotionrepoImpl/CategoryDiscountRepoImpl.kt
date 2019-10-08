package com.aceplus.data.repoimpl.promotionrepoImpl

import com.aceplus.data.database.MyDatabase
import com.aceplus.domain.model.promotionDataClass.CategoryDiscountDataClass
import com.aceplus.domain.repo.promotionrepo.CategoryDiscountRepo
import io.reactivex.Observable

class CategoryDiscountRepoImpl (val database: MyDatabase): CategoryDiscountRepo {
    override fun getCategoryDiscountList(): Observable<List<CategoryDiscountDataClass>> {
        return Observable.just(database.tDiscountByCategoryQuantityDao().getCategoryDiscount())

    }
}
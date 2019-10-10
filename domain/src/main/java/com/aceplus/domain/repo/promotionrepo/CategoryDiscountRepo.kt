package com.aceplus.domain.repo.promotionrepo

import com.aceplus.domain.model.promotionDataClass.CategoryDiscountDataClass
import io.reactivex.Observable

interface CategoryDiscountRepo {
    fun getCategoryDiscountList(): Observable<List<CategoryDiscountDataClass>>

}
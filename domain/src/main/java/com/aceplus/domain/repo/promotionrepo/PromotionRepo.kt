package com.aceplus.domain.repo.promotionrepo

import com.aceplus.domain.model.promotionDataClass.*
import io.reactivex.Observable

interface PromotionRepo {
    fun getPromotionPriceList(): Observable<List<PromotionPriceDataClass>>
    fun getPromotionGiftList(): Observable<List<PromotionGiftDataClass>>
    fun getCategoryDiscountList(): Observable<List<CategoryDiscountDataClass>>
    fun getVolumeDiscountList(): Observable<List<VolumeDiscountDataClass>>
    fun getVolumeDiscountFilterList(): Observable<List<VolumeDiscountFilterDataClass>>
    fun getClassDiscountByPrice(currentDate:String): Observable<List<ClassDiscountByPriceDataClass>>
    fun getClassDiscountByGift(currentDate: String): Observable<List<ClassDiscountByGiftDataClass>>
    fun getClassDiscountForShowPrice(currentDate: String): Observable<List<ClassDiscountForShowPriceDataClass>>
    fun getClassDiscountForShowGift(currentDate: String): Observable<List<ClassDiscountForShowGiftDataClass>>


}
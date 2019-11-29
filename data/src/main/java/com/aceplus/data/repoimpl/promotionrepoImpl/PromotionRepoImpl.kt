package com.aceplus.data.repoimpl.promotionrepoImpl

import com.aceplus.data.database.MyDatabase
import com.aceplus.domain.model.promotionDataClass.*
import com.aceplus.domain.repo.promotionrepo.PromotionRepo
import io.reactivex.Observable

class PromotionRepoImpl(val database: MyDatabase):PromotionRepo {
    override fun getPromotionPriceList(): Observable<List<PromotionPriceDataClass>> {
        return Observable.just(database.promotionPriceDao().getPromotionPriceForReport())

    }

    override fun getPromotionGiftList(): Observable<List<PromotionGiftDataClass>> {
        return Observable.just(database.promotionGiftDao().getPromotionGiftForReport())

    }

    override fun getCategoryDiscountList(): Observable<List<CategoryDiscountDataClass>> {
        return Observable.just(database.tDiscountByCategoryQuantityDao().getCategoryDiscount())

    }

    override fun getVolumeDiscountList(): Observable<List<VolumeDiscountDataClass>> {
        return Observable.just(database.volumeDiscountDao().getVolumeDiscountList())

    }

    override fun getVolumeDiscountFilterList(): Observable<List<VolumeDiscountFilterDataClass>> {
        return Observable.just(database.volumeDiscountFilterDao().getVolumeDiscountFilter())

    }

    override fun getClassDiscountByPrice(currentDate: String): Observable<List<ClassDiscountByPriceDataClass>> {
        return Observable.just(database.classDiscountByPriceDao().getClassDiscountByPriceList(currentDate))

    }

    override fun getClassDiscountByGift(currentDate: String): Observable<List<ClassDiscountByGiftDataClass>> {
        return Observable.just(database.classDiscountByPriceGiftDao().getClassDiscountByGiftList(currentDate))

    }

    override fun getClassDiscountForShowPrice(currentDate: String): Observable<List<ClassDiscountForShowPriceDataClass>> {
        return Observable.just(database.classDiscountForShowItemDao().getClassDiscountForShowPriceList(currentDate))

    }

    override fun getClassDiscountForShowGift(currentDate: String): Observable<List<ClassDiscountForShowGiftDataClass>> {
        return Observable.just(database.classDiscountForShowGiftDao().getClassDiscountForShowGift(currentDate))

    }

}
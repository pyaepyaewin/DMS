package com.aceplus.dms.viewmodel.promotionviewmodels

import android.arch.lifecycle.MutableLiveData
import com.aceplus.domain.model.promotionDataClass.*
import com.aceplus.domain.repo.promotionrepo.PromotionRepo
import com.aceplus.shared.viewmodel.BaseViewModel
import com.kkk.githubpaging.network.rx.SchedulerProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PromotionViewModel(
    private val promotionRepo: PromotionRepo,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {

    //promotionprice
    var promotionPriceSuccessState = MutableLiveData<List<PromotionPriceDataClass>>()
    var promotionPriceErrorState = MutableLiveData<String>()
    fun loadPromotionPrice() {
        launch {
            promotionRepo.getPromotionPriceList()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe({
                    promotionPriceSuccessState.postValue(it)
                }, {
                    promotionPriceErrorState.value = it.localizedMessage
                })
        }
    }
//promotiongift
    var promotionGiftSuccessState = MutableLiveData<List<PromotionGiftDataClass>>()
    var promotionGiftErrorState = MutableLiveData<String>()
    fun loadPromotionGift() {
        launch {
            promotionRepo.getPromotionGiftList()

                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe({
                    promotionGiftSuccessState.postValue(it)

                }, {
                    promotionGiftErrorState.value = it.localizedMessage
                })

        }

    }
    //categorydiscount
    var categoryDiscountSuccessState = MutableLiveData<List<CategoryDiscountDataClass>>()
    var categoryDiscountErrorState = MutableLiveData<String>()
    fun loadCategoryDiscount() {
        launch {
           promotionRepo.getCategoryDiscountList()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe({
                    categoryDiscountSuccessState.postValue(it)
                },{
                    categoryDiscountErrorState.value = it.localizedMessage
                })
        }
    }
    //volumediscount
    var volumeDiscountSuccessState = MutableLiveData<List<VolumeDiscountDataClass>>()
    var volumeDiscountErrorState = MutableLiveData<String>()
    fun loadVolumeDiscount() {
        launch {
            promotionRepo.getVolumeDiscountList()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe({
                    volumeDiscountSuccessState.postValue(it)
                },{
                    volumeDiscountErrorState.value = it.localizedMessage
                })
        }
    }
    //volumediscountfilter
    var volumeDiscountFilterSuccessState = MutableLiveData<List<VolumeDiscountFilterDataClass>>()
    var volumeDiscountFilterErrorState = MutableLiveData<String>()
    fun loadVolumeDiscountFilterList() {
        launch {
            promotionRepo.getVolumeDiscountFilterList()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe({
                    volumeDiscountFilterSuccessState.postValue(it)
                },{
                    volumeDiscountFilterErrorState.value = it.localizedMessage
                })
        }
    }
    //classdiscountbyprice
    var classDiscountByPriceSuccessState = MutableLiveData<List<ClassDiscountByPriceDataClass>>()
    var classDiscountByPriceErrorState = MutableLiveData<String>()
    fun loadClassDiscountByPrice(currentDate : String) {
        launch {
            promotionRepo.getClassDiscountByPrice(currentDate)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe({
                    classDiscountByPriceSuccessState.postValue(it)
                },{
                    classDiscountByPriceErrorState.value = it.localizedMessage
                })
        }
    }
    //classdiscountbygift
    var classDiscountByGiftSuccessState = MutableLiveData<List<ClassDiscountByGiftDataClass>>()
    var classDiscountByGiftErrorState = MutableLiveData<String>()
    fun loadClassDiscountByGift(currentDate: String) {
        launch {
            promotionRepo.getClassDiscountByGift(currentDate)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe({
                    classDiscountByGiftSuccessState.postValue(it)
                },{
                    classDiscountByGiftErrorState.value = it.localizedMessage
                })
        }
    }
    //classdiscountforshowprice
    var classDiscountForShowPriceSuccessState = MutableLiveData<List<ClassDiscountForShowPriceDataClass>>()
    var classDiscountForShowPriceErrorState = MutableLiveData<String>()
    fun loadClassDiscountForShowPrice(currentDate: String) {
        launch {
            promotionRepo.getClassDiscountForShowPrice(currentDate)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe({
                    classDiscountForShowPriceSuccessState.postValue(it)
                },{
                    classDiscountForShowPriceErrorState.value = it.localizedMessage
                })
        }
    }
    //classdiscountforshowgift
    var classDiscountForShowGiftSuccessState = MutableLiveData<List<ClassDiscountForShowGiftDataClass>>()
    var classDiscountForShowGiftErrorState = MutableLiveData<String>()
    fun loadClassDiscountForShowGift(currentDate: String) {
        launch {
            promotionRepo.getClassDiscountForShowGift(currentDate)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe({
                    classDiscountForShowGiftSuccessState.postValue(it)
                },{
                    classDiscountForShowGiftErrorState.value = it.localizedMessage
                })
        }
    }
}
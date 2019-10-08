package com.aceplus.dms.viewmodel.promotionviewmodels

import android.arch.lifecycle.MutableLiveData
import com.aceplus.domain.model.promotionDataClass.PromotionGiftDataClass
import com.aceplus.domain.repo.promotionrepo.PromotionGiftRepo
import com.aceplus.shared.viewmodel.BaseViewModel
import com.kkk.githubpaging.network.rx.SchedulerProvider

class PromotionGiftViewModel (private val promotionGiftRepo: PromotionGiftRepo, private val schedulerProvider: SchedulerProvider):
BaseViewModel() {
    var promotionGiftDataList = MutableLiveData<List<PromotionGiftDataClass>>()
    var promotionGiftSuccessState = MutableLiveData<List<PromotionGiftDataClass>>()
    var promotionGiftErrorState = MutableLiveData<String>()
    fun loadPromotionGift() {
        launch {
            promotionGiftRepo.getPromotionGiftList()

                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe {
                    promotionGiftDataList.postValue(it)

                }

        }

    }
}
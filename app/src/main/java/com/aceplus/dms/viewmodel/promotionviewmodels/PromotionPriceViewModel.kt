package com.aceplus.dms.viewmodel.promotionviewmodels

import android.arch.lifecycle.MutableLiveData
import com.aceplus.domain.model.promotionDataClass.PromotionPriceDataClass
import com.aceplus.domain.repo.promotionrepo.PromotionPriceRepo
import com.aceplus.shared.viewmodel.BaseViewModel
import com.kkk.githubpaging.network.rx.SchedulerProvider
import io.reactivex.android.schedulers.AndroidSchedulers

class PromotionPriceViewModel (
    private val promotionPriceRepo: PromotionPriceRepo,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {
    var promotionPriceSuccessState = MutableLiveData<List<PromotionPriceDataClass>>()
    var promotionPriceErrorState = MutableLiveData<String>()
    fun loadPromotionPrice() {
        launch {
            promotionPriceRepo.getPromotionPriceList()
                .subscribeOn(schedulerProvider.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    promotionPriceSuccessState.postValue(it)
                }, {
                    promotionPriceErrorState.value = it.localizedMessage
                })
        }
    }
}
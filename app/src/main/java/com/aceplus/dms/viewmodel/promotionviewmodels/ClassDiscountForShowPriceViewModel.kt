package com.aceplus.dms.viewmodel.promotionviewmodels

import android.arch.lifecycle.MutableLiveData
import com.aceplus.domain.model.promotionDataClass.ClassDiscountForShowPriceDataClass
import com.aceplus.domain.repo.promotionrepo.ClassDiscountForShowPriceRepo
import com.aceplus.shared.viewmodel.BaseViewModel
import com.kkk.githubpaging.network.rx.SchedulerProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ClassDiscountForShowPriceViewModel (private val classDiscountForShowPriceRepo: ClassDiscountForShowPriceRepo, private val schedulerProvider: SchedulerProvider):
    BaseViewModel() {
    var classDiscountForShowPriceSuccessState = MutableLiveData<List<ClassDiscountForShowPriceDataClass>>()
    var classDiscountForShowPriceErrorState = MutableLiveData<String>()
    fun loadClassDiscountByPrice() {
        launch {
            classDiscountForShowPriceRepo.getClassDiscountForShowPrice()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    classDiscountForShowPriceSuccessState.postValue(it)
                },{
                    classDiscountForShowPriceErrorState.value = it.localizedMessage
                })
        }
    }
}
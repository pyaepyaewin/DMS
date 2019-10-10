package com.aceplus.dms.viewmodel.promotionviewmodels

import android.arch.lifecycle.MutableLiveData
import com.aceplus.domain.model.promotionDataClass.ClassDiscountForShowGiftDataClass
import com.aceplus.domain.repo.promotionrepo.ClassDiscountForShowGiftRepo
import com.aceplus.shared.viewmodel.BaseViewModel
import com.kkk.githubpaging.network.rx.SchedulerProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ClassDiscountForShowGiftViewModel(private val classDiscountForShowGiftRepo: ClassDiscountForShowGiftRepo, private val schedulerProvider: SchedulerProvider):
    BaseViewModel() {
    var classDiscountForShowGiftSuccessState = MutableLiveData<List<ClassDiscountForShowGiftDataClass>>()
    var classDiscountForShowGiftErrorState = MutableLiveData<String>()
    fun loadClassDiscountForShowGift() {
        launch {
            classDiscountForShowGiftRepo.getClassDiscountForShowGift()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    classDiscountForShowGiftSuccessState.postValue(it)
                },{
                    classDiscountForShowGiftErrorState.value = it.localizedMessage
                })
        }
    }
}
package com.aceplus.dms.viewmodel.promotionviewmodels

import android.arch.lifecycle.MutableLiveData
import com.aceplus.domain.model.promotionDataClass.ClassDiscountByGiftDataClass
import com.aceplus.domain.repo.promotionrepo.ClassDiscountByGiftRepo
import com.aceplus.shared.viewmodel.BaseViewModel
import com.github.mikephil.charting.utils.Utils
import com.kkk.githubpaging.network.rx.SchedulerProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ClassDiscountByGiftViewModel(private val classDiscountByGiftRepo: ClassDiscountByGiftRepo, private val schedulerProvider: SchedulerProvider):
    BaseViewModel() {
    var classDiscountByGiftSuccessState = MutableLiveData<List<ClassDiscountByGiftDataClass>>()
    var classDiscountByGiftErrorState = MutableLiveData<String>()
    fun loadClassDiscountByPrice() {
        launch {
            classDiscountByGiftRepo.getClassDiscountByGift()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    classDiscountByGiftSuccessState.postValue(it)
                },{
                    classDiscountByGiftErrorState.value = it.localizedMessage
                })
        }
    }
}
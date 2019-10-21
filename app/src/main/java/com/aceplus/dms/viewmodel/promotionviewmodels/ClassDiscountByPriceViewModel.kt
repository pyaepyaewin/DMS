package com.aceplus.dms.viewmodel.promotionviewmodels

import android.arch.lifecycle.MutableLiveData
import com.aceplus.domain.model.promotionDataClass.ClassDiscountByPriceDataClass
import com.aceplus.domain.repo.promotionrepo.ClassDiscountByPriceRepo
import com.aceplus.shared.viewmodel.BaseViewModel
import com.github.mikephil.charting.utils.Utils
import com.kkk.githubpaging.network.rx.SchedulerProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ClassDiscountByPriceViewModel (private val classDiscountByPriceRepo: ClassDiscountByPriceRepo, private val schedulerProvider: SchedulerProvider):
BaseViewModel() {
    var classDiscountByPriceSuccessState = MutableLiveData<List<ClassDiscountByPriceDataClass>>()
    var classDiscountByPriceErrorState = MutableLiveData<String>()
    fun loadClassDiscountByPrice(currentDate : String) {
        launch {
            classDiscountByPriceRepo.getClassDiscountByPrice(currentDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    classDiscountByPriceSuccessState.postValue(it)
                },{
                    classDiscountByPriceErrorState.value = it.localizedMessage
                })
        }
    }
}
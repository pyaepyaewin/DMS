package com.aceplus.dms.viewmodel.promotionviewmodels

import android.arch.lifecycle.MutableLiveData
import com.aceplus.domain.model.promotionDataClass.VolumeDiscountDataClass
import com.aceplus.domain.repo.promotionrepo.VolumeDiscountRepo
import com.aceplus.shared.viewmodel.BaseViewModel
import com.kkk.githubpaging.network.rx.SchedulerProvider
import io.reactivex.android.schedulers.AndroidSchedulers

class VolumeDiscountViewModel (private val volumeDiscountRepo: VolumeDiscountRepo, private val schedulerProvider: SchedulerProvider):
    BaseViewModel() {
    var volumeDiscountSuccessState = MutableLiveData<List<VolumeDiscountDataClass>>()
    var volumeDiscountErrorState = MutableLiveData<String>()
    fun loadVolumeDiscount() {
        launch {
            volumeDiscountRepo.getVolumeDiscountList()
                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    volumeDiscountSuccessState.postValue(it)
                },{
                    volumeDiscountErrorState.value = it.localizedMessage
                })
        }
    }
}

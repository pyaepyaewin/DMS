package com.aceplus.dms.viewmodel.promotionviewmodels

import android.arch.lifecycle.MutableLiveData
import com.aceplus.domain.model.promotionDataClass.VolumeDiscountFilterDataClass
import com.aceplus.domain.repo.promotionrepo.VolumeDiscountFilterRepo
import com.aceplus.shared.viewmodel.BaseViewModel
import com.kkk.githubpaging.network.rx.SchedulerProvider
import io.reactivex.android.schedulers.AndroidSchedulers

class VolumeDiscountFilterViewModel (private val volumeDiscountFilterRepo: VolumeDiscountFilterRepo, private val schedulerProvider: SchedulerProvider):
    BaseViewModel() {
    var volumeDiscountFilterSuccessState = MutableLiveData<List<VolumeDiscountFilterDataClass>>()
    var volumeDiscountFilterErrorState = MutableLiveData<String>()
    fun loadVolumeDiscountFilterList() {
        launch {
            volumeDiscountFilterRepo.getVolumeDiscountFilterList()
                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    volumeDiscountFilterSuccessState.postValue(it)
                },{
                    volumeDiscountFilterErrorState.value = it.localizedMessage
                })
        }
    }
}
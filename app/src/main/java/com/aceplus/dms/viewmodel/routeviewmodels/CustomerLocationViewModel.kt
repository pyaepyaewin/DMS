package com.aceplus.dms.viewmodel.routeviewmodels

import android.arch.lifecycle.MutableLiveData
import com.aceplus.domain.model.routedataclass.CustomerLocationDataClass
import com.aceplus.domain.repo.routerepo.CustomerLocationRepo
import com.aceplus.shared.viewmodel.BaseViewModel
import com.kkk.githubpaging.network.rx.SchedulerProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CustomerLocationViewModel(private val customerLocationRepo: CustomerLocationRepo, private val schedulerProvider: SchedulerProvider): BaseViewModel()
{
    var customerLocationSuccessState = MutableLiveData<List<CustomerLocationDataClass>>()
    var customerLocationErrorState = MutableLiveData<String>()
    fun loadCustomerLocation() {
        launch {
            customerLocationRepo.getCustomerLocation()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe({
                    customerLocationSuccessState.postValue(it)
                },{
                    customerLocationErrorState.value = it.localizedMessage
                })
        }
    }
}
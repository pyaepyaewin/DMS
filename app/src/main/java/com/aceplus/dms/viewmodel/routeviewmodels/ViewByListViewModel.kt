package com.aceplus.dms.viewmodel.routeviewmodels

import android.arch.lifecycle.MutableLiveData
import com.aceplus.domain.model.routedataclass.TownshipDataClass
import com.aceplus.domain.model.routedataclass.ViewByListDataClass
import com.aceplus.domain.repo.routerepo.ViewByListRepo
import com.aceplus.shared.viewmodel.BaseViewModel
import com.kkk.githubpaging.network.rx.SchedulerProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ViewByListViewModel(private val viewByListRepo: ViewByListRepo, private val schedulerProvider: SchedulerProvider):
    BaseViewModel() {
    var townshipListSuccessState = MutableLiveData<List<TownshipDataClass>>()
    var townshipErrorState = MutableLiveData<String>()
    var townShipDetailListSuccessState=MutableLiveData<List<ViewByListDataClass>>()
    var townShipDetailErrorState = MutableLiveData<String>()

    fun loadTownShipData() {
        launch {
            viewByListRepo.getTownShipList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    townshipListSuccessState.postValue(it)
                },{
                    townshipErrorState.value = it.localizedMessage
                })
        }
    }
    fun loadTownShipDetail()
    {
        launch {
            viewByListRepo.getTownShipDetail()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        townShipDetailListSuccessState.postValue(it)
                    },{
                        townShipDetailErrorState.value=it.localizedMessage
                    }
                )
        }
    }
}
package com.aceplus.dms.viewmodel.creditcollection

import android.arch.lifecycle.MutableLiveData
import com.aceplus.domain.model.creditcollectiondataclass.CreditCollectionDataClass
import com.aceplus.domain.repo.creditcollectionrepo.CreditCollectionRepo
import com.aceplus.shared.viewmodel.BaseViewModel
import com.kkk.githubpaging.network.rx.SchedulerProvider
import io.reactivex.android.schedulers.AndroidSchedulers

class CreditCollectionViewModel(
    private val creditCollectionRepo:CreditCollectionRepo,
    private val schedulerProvider: SchedulerProvider
) :BaseViewModel() {
    var creditCollectionSuccessState = MutableLiveData<List<CreditCollectionDataClass>>()
    var creditCollectionErrorState = MutableLiveData<String>()
    fun loadCreditCollection() {
        launch {
            creditCollectionRepo.getCreditCollectionList()
                .subscribeOn(schedulerProvider.io())
                //.subscribeOn(schedulerProvider.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    creditCollectionSuccessState.postValue(it)
                }, {
                    creditCollectionErrorState.value = it.localizedMessage
                })
        }
    }
}
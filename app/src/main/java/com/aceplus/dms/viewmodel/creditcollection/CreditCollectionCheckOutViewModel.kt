package com.aceplus.dms.viewmodel.creditcollection

import android.arch.lifecycle.MutableLiveData
import com.aceplus.domain.model.creditcollectiondataclass.CreditCollectionCheckoutDataClass
import com.aceplus.domain.repo.creditcollectionrepo.CreditCollectionCheckOutRepo
import com.aceplus.shared.viewmodel.BaseViewModel
import com.kkk.githubpaging.network.rx.SchedulerProvider
import io.reactivex.android.schedulers.AndroidSchedulers

class CreditCollectionCheckOutViewModel(
    private val creditCollectionCheckOutRepo:CreditCollectionCheckOutRepo,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {
    var creditCollectionCheckOutSuccessState = MutableLiveData<List<CreditCollectionCheckoutDataClass>>()
    var creditCollectionCheckOutErrorState = MutableLiveData<String>()
    fun loadCreditCollectionCheckOut(customerId:String) {
        launch {
            creditCollectionCheckOutRepo.getCreditCollectionCheckOutList(customerId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    creditCollectionCheckOutSuccessState.postValue(it)
                }, {
                    creditCollectionCheckOutErrorState.value = it.localizedMessage
                })
        }
    }
}

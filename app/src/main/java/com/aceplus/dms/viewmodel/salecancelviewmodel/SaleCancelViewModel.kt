package com.aceplus.dms.viewmodel.salecancelviewmodel

import android.arch.lifecycle.MutableLiveData
import com.aceplus.domain.entity.product.Product
import com.aceplus.domain.model.sale.salecancel.SaleCancelItem
import com.aceplus.domain.repo.salecancelrepo.SaleCancelRepo
import com.aceplus.domain.vo.SoldProductInfo
import com.aceplus.shared.viewmodel.BaseViewModel
import com.kkk.githubpaging.network.rx.SchedulerProvider
import io.reactivex.android.schedulers.AndroidSchedulers

class SaleCancelViewModel(
    private val saleCancelRepo: SaleCancelRepo,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {
    var saleCancelSuccessState = MutableLiveData<List<SaleCancelItem>>()
    var saleCancelErrorState = MutableLiveData<String>()
    fun loadSaleCancelList() {
        launch {
            saleCancelRepo.getSaleCancelList()
                .subscribeOn(schedulerProvider.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    saleCancelSuccessState.postValue(it)

                }, {
                    saleCancelErrorState.value = it.localizedMessage
                })
        }
    }




    }


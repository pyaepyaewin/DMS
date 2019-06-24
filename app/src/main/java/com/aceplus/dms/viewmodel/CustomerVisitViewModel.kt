package com.aceplus.dms.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.aceplus.domain.repo.CustomerVisitRepo
import com.aceplus.shared.viewmodel.BaseViewModel
import com.kkk.githubpaging.network.rx.SchedulerProvider

class CustomerVisitViewModel(
    val customerVisitRepo: CustomerVisitRepo,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {
    var errorState = MutableLiveData<Pair<String, Int>>()
    var successState = MutableLiveData<String>()

}
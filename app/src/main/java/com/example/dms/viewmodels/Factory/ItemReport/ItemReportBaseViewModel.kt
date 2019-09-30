package com.example.dms.viewmodels.Factory.ItemReport

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class ItemReportBaseViewModel  : ViewModel() {
    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    private val disposable = CompositeDisposable()

    fun launch(observable: () -> Disposable) {
        disposable.add(observable())
    }


}
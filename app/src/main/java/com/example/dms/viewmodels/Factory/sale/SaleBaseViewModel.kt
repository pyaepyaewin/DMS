package com.example.dms.viewmodels.Factory.sale

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class SaleBaseViewModel : ViewModel() {
    private val disposable = CompositeDisposable()

    fun launch(observable: () -> Disposable) {
        disposable.add(observable())
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}
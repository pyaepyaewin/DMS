package com.example.dms.viewmodels.Factory.print

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class PrintBaseViewModel:ViewModel() {
    private val disposable = CompositeDisposable()

    fun launch(observable: () -> Disposable) {
        disposable.add(observable())
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}
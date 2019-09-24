package com.example.dms.viewmodels.Factory.checkout

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class CheckOutBaseViewModel:ViewModel() {
    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    private val disposable = CompositeDisposable()

    fun launch(observable: () -> Disposable) {
        disposable.add(observable())
    }


}
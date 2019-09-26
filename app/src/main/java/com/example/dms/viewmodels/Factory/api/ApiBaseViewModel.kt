package com.example.dms.viewmodels.Factory.api

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class ApiBaseViewModel:ViewModel() {
    private val disposable = CompositeDisposable()

    fun launch(observable: () -> Disposable) {
        disposable.add(observable())
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}
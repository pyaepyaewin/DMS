package com.aceplus.shared.viewmodel

import android.arch.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

//    val disposable = CompositeDisposable()
//
//    fun launch(observable: () -> Disposable) {
//        disposable.add(observable())
//    }

    override fun onCleared() {
        super.onCleared()
//        disposable.clear()
    }

}
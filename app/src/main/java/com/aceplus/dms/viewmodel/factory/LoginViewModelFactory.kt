package com.aceplus.dms.viewmodel.factory

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.aceplus.dms.viewmodel.LoginViewModel
import com.aceplus.domain.repo.LoginRepo


class LoginViewModelFactory(private val loginRepo: LoginRepo) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(loginRepo) as T
    }
}

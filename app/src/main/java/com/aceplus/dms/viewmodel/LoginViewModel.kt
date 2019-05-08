package com.aceplus.dms.viewmodel

import android.util.Log
import com.aceplus.data.repoimpl.LoginRepoImpl
import com.aceplus.domain.repo.LoginRepo
import com.aceplus.shared.viewmodel.BaseViewModel
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_home.view.*

class LoginViewModel(val loginRepo: LoginRepo) : BaseViewModel() {

    fun login(username: String, password: String) {
        val paramData = username + password
        loginRepo.loginUser(paramData)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.e("Response", it.aceplusStatusMessage)
            }, {
                Log.e("Response", it.localizedMessage)
            })
    }
}
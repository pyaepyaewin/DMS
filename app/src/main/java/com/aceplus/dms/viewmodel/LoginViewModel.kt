package com.aceplus.dms.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import android.widget.Toast
import com.aceplus.data.utils.Constant
import com.aceplus.dms.utils.Utils
import com.aceplus.domain.model.forApi.login.DataForLogin
import com.aceplus.domain.model.forApi.login.LoginResponse
import com.aceplus.domain.repo.LoginRepo
import com.aceplus.shared.viewmodel.BaseViewModel
import com.aceplussolutions.rms.constants.AppUtils
import com.kkk.githubpaging.network.rx.SchedulerProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.ArrayList

class LoginViewModel(private var loginRepo: LoginRepo, private val schedulerProvider: SchedulerProvider) :
    BaseViewModel() {
    var errorState = MutableLiveData<Pair<String, Int>>()
    var successState = MutableLiveData<String>()

    fun login(userId: String, password: String, deviceId: String) {
        if (loginRepo.isSaleManExist()) {
            if (loginRepo.isSaleManCorrect(userId, Utils.encodePassword(password))) {
                successState.postValue("Successfully Login")
            } else {
                errorState.postValue(Pair("User ID or Password is incorrect!", 1))
            }
        } else {
            val paramData = Utils.createLoginParamData(userId, Utils.encodePassword(password), 0, deviceId)
            launch {
                loginRepo.loginUser(paramData)
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.mainThread())
                    .subscribe({
                        doActionWhenSuccessLogin(it)
                    }, {
                        errorState.postValue(Pair(it.localizedMessage, 1))
                    })
            }
        }
    }

    fun setLoginRepo(loginRepo: LoginRepo) {
        this.loginRepo = loginRepo
    }

    private fun doActionWhenSuccessLogin(response: LoginResponse) {
        if (response.aceplusStatusCode == 200) {
            if (response.route != 0) {

                val dataForLoginArrayList: List<DataForLogin> = response.dataForLogin
                if (dataForLoginArrayList[0].saleMan.size != 0) {
                    loginRepo.saveLoginData(response)
                    successState.postValue("Successfully Login")
                } else {
                    errorState.postValue(Pair("No route for this sale man!", 2))
                }
            } else {
                errorState.postValue(Pair("You have no route.", 2))
            }
        } else {
            errorState.postValue(Pair(response.aceplusStatusMessage, 1))
        }
    }

    fun isCustomer(): Boolean {
        return loginRepo.isCustomerInDB()
    }

}
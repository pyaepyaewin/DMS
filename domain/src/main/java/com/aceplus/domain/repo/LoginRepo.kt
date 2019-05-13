package com.aceplus.domain.repo

import com.aceplus.domain.model.forApi.login.DataForLogin
import com.aceplus.domain.model.forApi.login.LoginResponse
import io.reactivex.Observable

interface LoginRepo {
    fun isSaleManExist():Boolean
    fun isSaleManCorrect(userId:String,password:String):Boolean
    fun loginUser(paramData: String): Observable<LoginResponse>
    fun saveLoginData(loginResponse: LoginResponse)
    fun isCustomerInDB():Boolean
}
package com.aceplus.domain.repo

import com.aceplus.domain.model.forApi.login.LoginResponse
import io.reactivex.Observable

interface LoginRepo {
    fun loginUser(paramData: String): Observable<LoginResponse>
}
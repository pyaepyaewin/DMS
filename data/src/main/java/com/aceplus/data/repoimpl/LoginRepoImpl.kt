package com.aceplus.data.repoimpl

import com.aceplus.data.remote.DownloadApiService
import com.aceplus.domain.model.forApi.login.LoginResponse
import com.aceplus.domain.repo.LoginRepo
import io.reactivex.Observable

class LoginRepoImpl(private val downloadApi:DownloadApiService):LoginRepo{
    override fun loginUser(paramData: String): Observable<LoginResponse> {
        return downloadApi.login(paramData)
    }

}
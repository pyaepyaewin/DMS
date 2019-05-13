package com.aceplus.dms.di

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.SharedPreferences
import android.support.v4.app.FragmentActivity
import com.aceplus.data.database.MyDatabase
import com.aceplus.data.di.createDownloadWebService
import com.aceplus.data.di.createOkHttpClient
import com.aceplus.data.remote.DownloadApiService
import com.aceplus.data.repoimpl.LoginRepoImpl
import com.aceplus.data.utils.Constant
import com.aceplus.dms.viewmodel.LoginViewModel
import com.aceplus.dms.viewmodel.factory.LoginViewModelFactory
import com.aceplus.domain.repo.LoginRepo
import com.aceplussolutions.rms.constants.AppConstants

fun provideDB(context: Context): MyDatabase = MyDatabase.getInstance(context)!!
fun provideDownloadApi() = createDownloadWebService(createOkHttpClient(), Constant.BASE_URL)
fun provideSharedPreferences(context: Context): SharedPreferences =
    context.getSharedPreferences(AppConstants.SHP_NAME, 0)

fun provideLoginRepo(context: Context) =
    LoginRepoImpl(provideDownloadApi(), provideDB(context), provideSharedPreferences(context))

fun provideLoginViewModelFactory(context: Context) = LoginViewModelFactory(provideLoginRepo(context))
fun provideLoginViewModel(fragmentActivity: FragmentActivity) =
    ViewModelProviders.of(fragmentActivity, provideLoginViewModelFactory(fragmentActivity.applicationContext)).get(
        LoginViewModel::class.java
    )

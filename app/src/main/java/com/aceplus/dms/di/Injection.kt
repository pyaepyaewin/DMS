package com.aceplus.dms.di

import android.content.Context
import android.content.SharedPreferences
import com.aceplus.data.database.MyDatabase
import com.aceplus.data.di.createDownloadWebService
import com.aceplus.data.di.createOkHttpClient
import com.aceplus.data.utils.Constant
import com.aceplussolutions.rms.constants.SharedConstants

fun provideDB(context: Context): MyDatabase = MyDatabase.getInstance(context)!!
fun provideDownloadApi() = createDownloadWebService(createOkHttpClient(), Constant.BASE_URL)
fun provideSharedPreferences(context: Context): SharedPreferences =
    context.getSharedPreferences(SharedConstants.SHP_NAME, 0)

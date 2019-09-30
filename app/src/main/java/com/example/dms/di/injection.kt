package com.example.dms.di

import android.content.Context
import com.example.dms.data.database.MyDatabase
import com.example.dms.data.repositories.*
import com.example.dms.network.ApiService
import com.example.dms.util.Appconstants
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object injection {
    private fun provideApiService(): ApiService
    {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(Appconstants.baseurl)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
        return retrofit.create(ApiService::class.java)
    }
    private fun provideDatabase(context:Context):MyDatabase{
        return MyDatabase.getInstance(context)
    }

//    f  un provideApiRepository(context: Context):ApiRepository
//        {
//        return ApiRepositoryImpl(context, provideApiService(), provideDatabase(context))
//    }
    fun provideCustomerRepository(context: Context):CustomerRepository{
        return CustomerRepositoryImpl(context,provideApiService(), provideDatabase(context))
    }
    fun provideSaleRepository(context: Context):SaleRepository{
        return SaleRepositoryImpl(context,provideApiService(), provideDatabase(context))
    }
    fun provideCheckOutRepository(context: Context):CheckOutRepository{
        return CheckOutRepositoryImpl(context, provideApiService(),provideDatabase(context))
    }
    fun provideReportRepository(context: Context):ReportRepository
    {
        return ReportRepositoryImpl(provideDatabase(context))
    }
    fun provideItemReportRepository(context: Context):ItemReportRepository
    {
        return ItemReportRepositoryImpl(provideDatabase(context))
    }

}
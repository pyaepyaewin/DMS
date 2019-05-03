//package com.aceplus.data.di
//
//import com.aceplus.data.remote.DownloadApiService
//import com.aceplus.data.remote.UploadApiService
//import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
//import com.kkk.githubpaging.network.ApiService
//import com.kkk.githubpaging.utility.AppConstants
//import io.reactivex.schedulers.Schedulers.single
//import okhttp3.OkHttpClient
//import okhttp3.logging.HttpLoggingInterceptor
//import org.koin.dsl.module.module
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import java.util.concurrent.TimeUnit
//
//fun createOkHttpClient(): OkHttpClient {
//    val httpLoggingInterceptor = HttpLoggingInterceptor()
//    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
//    return OkHttpClient.Builder()
//        .connectTimeout(60L, TimeUnit.SECONDS)
//        .readTimeout(60L, TimeUnit.SECONDS)
//        .addInterceptor(httpLoggingInterceptor).build()
//}
//
//fun createUploadWebService(okHttpClient: OkHttpClient, url: String): UploadApiService {
//    val retrofit = Retrofit.Builder()
//        .baseUrl(url)
//        .client(okHttpClient)
//        .addConverterFactory(GsonConverterFactory.create())
//        .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
//    return retrofit.create(UploadApiService::class.java)
//}
//
//fun createDownloadWebService(okHttpClient: OkHttpClient, url: String): DownloadApiService {
//    val retrofit = Retrofit.Builder()
//        .baseUrl(url)
//        .client(okHttpClient)
//        .addConverterFactory(GsonConverterFactory.create())
//        .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
//    return retrofit.create(DownloadApiService::class.java)
//}
//
//fun createUploadRealtimeWebService(okHttpClient: OkHttpClient, url: String): UploadApiService {
//    val retrofit = Retrofit.Builder()
//        .baseUrl(url)
//        .client(okHttpClient)
//        .addConverterFactory(GsonConverterFactory.create())
//        .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
//    return retrofit.create(UploadApiService::class.java)
//}
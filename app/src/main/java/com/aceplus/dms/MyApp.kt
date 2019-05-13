package com.aceplus.dms

import android.app.Application
import android.arch.lifecycle.ViewModelProvider
import com.aceplus.data.di.createDownloadWebService
import com.aceplus.data.di.createOkHttpClient
import com.aceplus.data.di.createUploadRealtimeWebService
import com.aceplus.data.di.createUploadWebService
import com.aceplus.data.remote.DownloadApiService
import com.aceplus.data.remote.RealTimeUploadApiService
import com.aceplus.data.remote.UploadApiService
import com.aceplus.data.repoimpl.LoginRepoImpl
import com.aceplus.data.repoimpl.SyncRepoImpl
import com.aceplus.dms.di.provideDB
import com.aceplus.dms.di.provideSharedPreferences
import com.aceplus.dms.viewmodel.LoginViewModel
import com.aceplus.dms.viewmodel.SyncViewModel
import com.aceplus.dms.viewmodel.factory.KodeinViewModelFactory
import com.aceplus.domain.repo.LoginRepo
import com.aceplus.domain.repo.SyncRepo
import com.aceplussolutions.rms.constants.AppConstants
import com.crashlytics.android.Crashlytics
import com.kkk.githubpaging.network.rx.AndroidSchedulerProvider
import com.kkk.githubpaging.network.rx.SchedulerProvider
import io.fabric.sdk.android.Fabric
import okhttp3.OkHttpClient
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class MyApp : Application(), KodeinAware {
    override val kodein by Kodein.lazy {

        // provided web components
        bind<OkHttpClient>() with singleton { createOkHttpClient() }
        bind<DownloadApiService>() with singleton {
            createDownloadWebService(
                okHttpClient = instance(),
                url = AppConstants.BASE_URL
            )
        }
        bind<UploadApiService>() with singleton {
            createUploadWebService(
                okHttpClient = instance(),
                url = AppConstants.BASE_URL
            )
        }
        bind<RealTimeUploadApiService>() with singleton {
            createUploadRealtimeWebService(
                okHttpClient = instance(),
                url = AppConstants.BACKEND_URL
            )
        }

        //provide database & cache
        bind() from singleton { provideDB(this@MyApp) }
        bind() from singleton { provideSharedPreferences(this@MyApp) }

        //provide schedule provider
        bind<SchedulerProvider>() with singleton { AndroidSchedulerProvider() }

        //provide data repository
        bind<LoginRepo>() with singleton { LoginRepoImpl(downloadApi = instance(), db = instance(), shf = instance()) }
        bind<SyncRepo>() with singleton {
            SyncRepoImpl(
                downloadApiService = instance(),
                db = instance(),
                shf = instance()
            )
        }

        //ViewModel Factory for all view model
        bind<ViewModelProvider.Factory>() with singleton { KodeinViewModelFactory(kodein) }

        //ViewModel
        bind() from singleton { LoginViewModel(instance(),instance()) }
        bind() from singleton { SyncViewModel(instance(),instance()) }
    }

    override fun onCreate() {
        super.onCreate()
        Fabric.with(applicationContext, Crashlytics())
        val fabric = Fabric.Builder(applicationContext)
            .kits(Crashlytics())
            .debuggable(true)  // Enables Crashlytics debugger
            .build()
        Fabric.with(fabric)
        //app crash error handling
        //        Thread.setDefaultUncaughtExceptionHandler(new MyExceptionHandler(this));//to auto reopen app when crash

    }

}
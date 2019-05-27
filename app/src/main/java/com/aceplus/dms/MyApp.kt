package com.aceplus.dms

import android.app.Application
import android.arch.lifecycle.ViewModelProvider
import com.aceplus.dms.di.*
import com.aceplus.dms.viewmodel.factory.KodeinViewModelFactory
import com.crashlytics.android.Crashlytics
import com.kkk.githubpaging.network.rx.AndroidSchedulerProvider
import com.kkk.githubpaging.network.rx.SchedulerProvider
import io.fabric.sdk.android.Fabric
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.*

class MyApp : Application(), KodeinAware {
    override val kodein by Kodein.lazy {

        // provided web components
        import(networkModule)

        //provide database & cache
        import(dbModule)

        //provide schedule provider
        bind<SchedulerProvider>() with singleton { AndroidSchedulerProvider() }

        //provide data repository
        import(repoModule)

        //ViewModel Factory for all view model
        bind<ViewModelProvider.Factory>() with singleton { KodeinViewModelFactory(kodein) }

        //ViewModel
        import(vmModule)
    }

    private val dbModule = Kodein.Module{
        bind() from singleton { provideDB(this@MyApp) }
        bind() from singleton { provideSharedPreferences(this@MyApp) }
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
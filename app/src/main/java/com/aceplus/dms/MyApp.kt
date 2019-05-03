package com.aceplus.dms

import android.app.Application
import android.content.Context
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric

class MyApp:Application() {
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

//    override fun attachBaseContext(base: Context) {
//        super.attachBaseContext(base)
//        MultiDex.install(this)
//    }
}
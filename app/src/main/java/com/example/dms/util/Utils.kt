package com.example.dms.util

import android.content.Context
import android.net.ConnectivityManager

object Utils {

    fun isOnline(context: Context): Boolean {
        val connMgr = context.getSystemService("connectivity") as ConnectivityManager
        val networkInfo = connMgr.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}
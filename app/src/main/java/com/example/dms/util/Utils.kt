package com.example.dms.util

import android.content.Context
import android.net.ConnectivityManager
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    fun isOnline(context: Context): Boolean {
        val connMgr = context.getSystemService("connectivity") as ConnectivityManager
        val networkInfo = connMgr.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
    fun getCurrentDate(): String{
        val currentDate = Calendar.getInstance().time
        val format = SimpleDateFormat("yyyy-MM-dd")
        return format.format(currentDate)
    }
    fun getInvoiceId():String
    {
        val currentDate=Calendar.getInstance().time.toString()
        return currentDate
    }
}
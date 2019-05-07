package com.aceplussolutions.rms.constants

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Environment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.widget.Toast
import com.aceplus.shared.R
import com.google.gson.Gson
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

object AppUtils {

    fun saveStringToShp(key: String, value: String, ctxt: Context) {
        val shp = ctxt.getSharedPreferences(AppConstants.SHP_NAME, 0)
        shp!!.edit().putString(key, value).apply()
    }

    fun getStringFromShp(key: String, ctxt: Context): String? {
        val shp = ctxt.getSharedPreferences(AppConstants.SHP_NAME, 0)
        return shp!!.getString(key, AppConstants.SHP_STRING_ERR)
    }

    fun saveIntToShp(key: String, value: Int, ctxt: Context) {
        val shp = ctxt.getSharedPreferences(AppConstants.SHP_NAME, 0)
        shp!!.edit().putInt(key, value).apply()
    }

    fun getIntFromShp(key: String, ctxt: Context): Int? {
        val shp = ctxt.getSharedPreferences(AppConstants.SHP_NAME, 0)
        return shp!!.getInt(key, AppConstants.SHP_INT_ERR)
    }

    fun getDialog(ctxt: Context): ProgressDialog {
        val pd = ProgressDialog(ctxt)
        pd.setMessage(AppConstants.PROGRESS_TEXT)
        return pd
    }


    fun getJsonString(obj: Any): String {
        val gson = Gson()
        return gson.toJson(obj).toString()
    }

//    @SuppressLint("InflateParams")
//    fun getAlertDialog(str: String, ctxt: Context): AlertDialog {
//        val ab: AlertDialog.Builder = AlertDialog.Builder(ctxt, R.style.InvitationDialog)
//        ab.setTitle("Alert")
//        val view = LayoutInflater.from(ctxt).inflate(R.layout.alert_dialog, null)
//        ab.setView(view)
//        view.alertMessage.text = str
//        ab.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
//        return ab.create()
//    }
//
//    @SuppressLint("InflateParams")
//    fun getProfileDialog(str: String, ctxt: Context): AlertDialog {
//        val ab: AlertDialog.Builder = AlertDialog.Builder(ctxt, R.style.InvitationDialog)
//        ab.setTitle("Profile")
//        val view = LayoutInflater.from(ctxt).inflate(R.layout.alert_dialog, null)
//        ab.setView(view)
//        view.alertMessage.text = str
//        ab.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
//        return ab.create()
//    }
//
//    fun getListenerDailog(str: String, ctxt: Context, listener: DialogInterface.OnClickListener): AlertDialog.Builder {
//        var ab: AlertDialog.Builder = AlertDialog.Builder(ctxt, R.style.InvitationDialog)
//        ab.setTitle("Alert")
//        ab.setMessage(str)
//        ab.setPositiveButton("ok", listener)
//        return ab
//    }

    fun getAppVersion(context: Context): String {
        try {
            val pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            val version = pInfo.versionName
            return "Version - $version"
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return ""
    }

    private fun getCurrentDate(withTime: Boolean): String {
        var dateFormat = "yyyy-MM-dd"
        if (withTime) {
            dateFormat += " HH:mm:ss"
        }
        return SimpleDateFormat(dateFormat).format(Date())
    }

    fun backupDatabase(context: Context) {
        val today = getCurrentDate(true)

        try {
            val sd = Environment.getExternalStorageDirectory()
            val data = Environment.getDataDirectory()

            if (sd.canWrite()) {
                Toast.makeText(context, "Backup database is starting...",
                        Toast.LENGTH_SHORT).show()
                val currentDBPath = "/data/com.aceplussolutions.rms/databases/ros.db"

                val backupDBPath = "RoS_DB_Backup_$today.db"
                val currentDB = File(data, currentDBPath)

                val folderPath = "mnt/sdcard/RoS_DB_Backup"
                val f = File(folderPath)
                f.mkdir()
                val backupDB = File(f, backupDBPath)
                val source = FileInputStream(currentDB).channel
                val destination = FileOutputStream(backupDB).channel
                destination.transferFrom(source, 0, source.size())
                source.close()
                destination.close()
                Toast.makeText(context, "Backup database Successful!",
                        Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Please set Permission for Storage in Setting!", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(context, "Cannot Backup!", Toast.LENGTH_SHORT).show()
        }

    }

}
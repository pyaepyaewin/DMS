package com.aceplus.dms.ui.activities

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.aceplus.dms.R
import com.firetrap.permissionhelper.action.OnDenyAction
import com.firetrap.permissionhelper.action.OnGrantAction
import com.firetrap.permissionhelper.helper.PermissionHelper
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    internal var saleman_Id = ""
    internal var saleman_No = ""
    internal var saleman_Name = ""
    internal var saleman_Pwd = ""

    internal var dialogBoxView: View? = null
    internal lateinit var builder: AlertDialog.Builder
    internal var dialog: AlertDialog? = null

    internal val mode = Activity.MODE_PRIVATE
    internal val MyPREFS = "SamparooPreference"

    //    public static SharedPreferences aPreference;
    lateinit var mySharedPreference: SharedPreferences

    lateinit var myEditor: SharedPreferences.Editor

    private var permissionRequest: PermissionHelper.PermissionBuilder? = null
    private val REQUEST_STORAGE = 41
    private val onDenyAction = object : OnDenyAction() {
        override fun call(i: Int, b: Boolean) {
            Toast.makeText(
                this@LoginActivity,
                "Application Exit Cannot Read Permission for read internal storage is deny.",
                Toast.LENGTH_SHORT
            ).show()
            finish()
        }
    }
    private val onGrantAction = object : OnGrantAction() {
        override fun call(i: Int) {
            Toast.makeText(this@LoginActivity, "Read Storage Permission Granted.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        permissionRequest!!.onRequestPermissionsResult(requestCode, permissions, grantResults)
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        buttonLogin.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        mySharedPreference = getSharedPreferences(MyPREFS, mode)
        myEditor = mySharedPreference.edit()

//        if (mySharedPreference.getString(Constant.KEY_CHANGE_URL, "") != "") {
//            Constant.changeUrl(mySharedPreference.getString(Constant.KEY_CHANGE_URL, ""))
//            Log.i("Current URL1", Constant.BASE_URL)
//        }
//        Log.i("Current URL2", Constant.BASE_URL)

        editTextUserID.setText("T1")
        editTextPassword.setText("aceplus")

//        if (Utils.isOsMarshmallow()) {
            askPermission()
//        }
    }

    private fun askPermission() {
        val permissionArr = arrayOfNulls<String>(6)
        permissionArr[0] = android.Manifest.permission.READ_EXTERNAL_STORAGE
        permissionArr[1] = Manifest.permission.ACCESS_COARSE_LOCATION
        permissionArr[2] = Manifest.permission.ACCESS_FINE_LOCATION
        permissionArr[3] = Manifest.permission.ACCESS_NETWORK_STATE
        permissionArr[4] = Manifest.permission.ACCESS_WIFI_STATE
        permissionArr[5] = Manifest.permission.SEND_SMS

        permissionRequest = PermissionHelper.with(this@LoginActivity)
            .build(*permissionArr)
            .onPermissionsGranted(onGrantAction)
            .onPermissionsDenied(onDenyAction)
            .request(REQUEST_STORAGE)
    }

}

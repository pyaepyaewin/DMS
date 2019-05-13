package com.aceplus.dms.ui.activities

import android.Manifest
import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.aceplus.data.database.MyDatabase
import com.aceplus.data.di.createDownloadWebService
import com.aceplus.data.di.createOkHttpClient
import com.aceplus.data.remote.DownloadApiService
import com.aceplus.data.repoimpl.LoginRepoImpl
import com.aceplus.dms.R
import com.aceplus.data.utils.Constant
import com.aceplus.dms.R.layout.activity_login
import com.aceplus.dms.di.provideDB
import com.aceplus.dms.di.provideDownloadApi
import com.aceplus.dms.di.provideSharedPreferences
import com.aceplus.dms.utils.Utils
import com.aceplus.dms.viewmodel.LoginViewModel
import com.aceplus.dms.viewmodel.factory.LoginViewModelFactory
import com.aceplus.domain.repo.LoginRepo
import com.aceplussolutions.rms.constants.AppConstants
import com.aceplussolutions.rms.constants.AppUtils
import com.aceplussolutions.rms.ui.activities.BaseActivity
import com.firetrap.permissionhelper.action.OnDenyAction
import com.firetrap.permissionhelper.action.OnGrantAction
import com.firetrap.permissionhelper.helper.PermissionHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.change_ip_layout.view.*

class LoginActivity : BaseActivity() {
    override val layoutId: Int
        get() = activity_login

    private var permissionRequest: PermissionHelper.PermissionBuilder? = null
    private val REQUEST_STORAGE = 41

    private lateinit var db: MyDatabase
    private lateinit var downloadApi: DownloadApiService
    private lateinit var shf: SharedPreferences
    private lateinit var loginRepo: LoginRepo
    private lateinit var loginViewModelFactory: LoginViewModelFactory

    private lateinit var loginViewModel: LoginViewModel

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

        if (AppUtils.getStringFromShp(Constant.KEY_CHANGE_URL, this) != "") {
            AppUtils.getStringFromShp(Constant.KEY_CHANGE_URL, this)?.let {
                Constant.changeUrl(it)
            }
        }

        db = provideDB(applicationContext)
        downloadApi = provideDownloadApi()
        shf = provideSharedPreferences(applicationContext)
        loginRepo = LoginRepoImpl(downloadApi, db, shf)

        loginViewModelFactory = LoginViewModelFactory(loginRepo)
        loginViewModel = ViewModelProviders.of(this, loginViewModelFactory).get(LoginViewModel::class.java)


        editTextUserID.setText("T1")
        editTextPassword.setText("aceplus")

        if (Utils.isOsMarshmallow) {
            askPermission()
        }

        imageView.setOnClickListener { AppUtils.backupDatabase(this) }
        textViewChangeIP.setOnClickListener { showDialogToChangeIP() }
        buttonLogin.setOnClickListener { doLoginAction() }

        loginViewModel.successState.observe(this, Observer {
            Utils.cancelDialog()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        })
        loginViewModel.errorState.observe(this, Observer {
            Utils.cancelDialog()
            it?.let { it1 -> Utils.commonDialog(it1.first, this, it1.second) }
        })
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

    private fun showDialogToChangeIP() {
        val dialogBuilder = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.change_ip_layout, null)
        val dialog = dialogBuilder.setView(dialogView).create()
        dialog.show()
        if (AppUtils.getStringFromShp(Constant.KEY_CHANGE_URL, this) != "") {
            dialogView.textViewCurrentIP.text = AppUtils.getStringFromShp(Constant.KEY_CHANGE_URL, this)
        }
        dialogView.editTextNewIP.setText("http://192.168.:9999/api/v1/")
        dialogView.textViewCancel.setOnClickListener { dialog.dismiss() }
        dialogView.textViewOk.setOnClickListener {
            val newIp = dialogView.editTextNewIP.text.toString()
            AppUtils.saveStringToShp(Constant.KEY_CHANGE_URL, newIp, this)

            Constant.BASE_URL = newIp

            downloadApi = createDownloadWebService(createOkHttpClient(), Constant.BASE_URL)
            loginRepo = LoginRepoImpl(downloadApi, db, shf)
            loginViewModel.setLoginRepo(loginRepo)

            dialog.dismiss()
        }

    }

    private fun doLoginAction() {
        if (editTextUserID.text.isEmpty()) {
            editTextUserID.error = "User ID is required."
            editTextUserID.requestFocus()
            return
        }
        if (editTextPassword.text.isEmpty()) {
            editTextPassword.error = "Password is required."
            editTextPassword.requestFocus()
            return
        }
        val deviceId = Utils.getDeviceId(this@LoginActivity)
        Utils.callDialog("Please wait...", this)
        loginViewModel.login(editTextUserID.text.toString(), editTextPassword.text.toString(), deviceId)
    }

}

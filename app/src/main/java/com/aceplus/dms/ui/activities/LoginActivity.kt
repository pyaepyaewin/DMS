package com.aceplus.dms.ui.activities

import android.Manifest
import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import com.aceplus.data.database.MyDatabase
import com.aceplus.data.repoimpl.LoginRepoImpl
import com.aceplus.dms.R
import com.aceplus.data.utils.Constant
import com.aceplus.dms.R.layout.activity_login
import com.aceplus.dms.di.provideDownloadApi
import com.aceplus.dms.utils.Utils
import com.aceplus.dms.viewmodel.LoginViewModel
import com.aceplussolutions.rms.constants.AppUtils
import com.aceplussolutions.rms.ui.activities.BaseActivity
import com.firetrap.permissionhelper.action.OnDenyAction
import com.firetrap.permissionhelper.action.OnGrantAction
import com.firetrap.permissionhelper.helper.PermissionHelper
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.change_ip_layout.view.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class LoginActivity : BaseActivity(), KodeinAware {
    override val kodein: Kodein by kodein()

    override val layoutId: Int
        get() = activity_login

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context,LoginActivity::class.java)
        }
    }

    private var permissionRequest: PermissionHelper.PermissionBuilder? = null
    private val requestStorage = 41

    private val loginViewModel: LoginViewModel by viewModel()

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
            it?.let { success ->
                //null setter is to avoid sucessState liveData  bcoz loginviewmodel use in main activity too
                loginViewModel.successState.postValue(null)
                startActivity(MainActivity.newIntent(this))
                finish()
            }
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
            .request(requestStorage)
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

            val downloadApi = provideDownloadApi()//need to create new instance (don't call instance from kodein)
//            var dapi: DownloadApiService by instance()
            val db: MyDatabase by instance()
            val shf: SharedPreferences by instance()
            val loginRepo =
                LoginRepoImpl(downloadApi, db, shf) // need to create new instance(don't call instance from kodein)
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

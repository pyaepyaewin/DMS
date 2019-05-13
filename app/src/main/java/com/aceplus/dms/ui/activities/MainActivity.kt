package com.aceplus.dms.ui.activities

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import com.aceplus.data.database.MyDatabase
import com.aceplus.data.repoimpl.LoginRepoImpl
import com.aceplus.data.utils.Constant
import com.aceplus.dms.R
import com.aceplus.dms.di.provideDB
import com.aceplus.dms.di.provideDownloadApi
import com.aceplus.dms.di.provideSharedPreferences
import com.aceplus.dms.utils.Utils
import com.aceplus.dms.viewmodel.LoginViewModel
import com.aceplus.dms.viewmodel.factory.LoginViewModelFactory
import com.aceplussolutions.rms.constants.AppUtils
import com.aceplussolutions.rms.ui.activities.BaseActivity
import kotlinx.android.synthetic.main.activity_home.*

class MainActivity : BaseActivity(), Utils.OnActionClickListener {
    override var layoutId: Int = R.layout.activity_home

    lateinit var loginViewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = provideDB(applicationContext)
        val downloadApi = provideDownloadApi()
        val shf = provideSharedPreferences(applicationContext)
        val loginRepo = LoginRepoImpl(downloadApi, db, shf)

        val loginViewModelFactory = LoginViewModelFactory(loginRepo)
        loginViewModel = ViewModelProviders.of(this, loginViewModelFactory).get(LoginViewModel::class.java)


        //db data test
//        val saleMans = db?.saleManDao()?.allData
//        val routes = db?.routeDao()?.allData
//        val routeAssigns = db?.routeAssignDao()?.allData
//        val routeScheduleV2 = db?.routeScheduleV2Dao()?.allData
//        val routeScheduleItemV2 = db?.routeScheduleItemV2Dao()?.allData

        username.text = AppUtils.getStringFromShp(Constant.SALEMAN_NAME, this)
        date.text = Utils.getCurrentDate(false)

        Utils.setOnActionClickListener(this)//set on action click listener
        cancel_img.setOnClickListener { onBackPressed() }
        buttonSync.setOnClickListener { startActivity(Intent(this, SyncActivity::class.java)) }
        buttonRoute.setOnClickListener {
            if (loginViewModel.isCustomer()) {
                startActivity(Intent(this, RouteActivity::class.java))
            } else {
                Utils.commonDialog("NO CUSTOMER", this, 1)
            }
        }
        buttonCustomerVisit.setOnClickListener { startActivity(Intent(this, CustomerVisitActivity::class.java)) }
        buttonPromotion.setOnClickListener { startActivity(Intent(this, PromotionActivity::class.java)) }
        button_vanIssue.setOnClickListener { startActivity(Intent(this, VanIssueActivity::class.java)) }
        buttonReport.setOnClickListener { startActivity(Intent(this, ReportActivity::class.java)) }

    }


    override fun onBackPressed() {
        Utils.askConfirmationDialog("Logout", "Do you want to logout?", "", this)
    }

    override fun onActionClick(type: String) {
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

}

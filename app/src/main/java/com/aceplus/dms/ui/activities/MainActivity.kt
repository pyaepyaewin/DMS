package com.aceplus.dms.ui.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import com.aceplus.data.database.MyDatabase
import com.aceplus.data.utils.Constant
import com.aceplus.dms.R
import com.aceplus.dms.utils.Utils
import com.aceplus.dms.viewmodel.LoginViewModel
import com.aceplus.domain.repo.LoginRepo
import com.aceplussolutions.rms.constants.AppUtils
import com.aceplussolutions.rms.ui.activities.BaseActivity
import com.kkk.githubpaging.network.rx.SchedulerProvider
import kotlinx.android.synthetic.main.activity_home.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class MainActivity : BaseActivity(), KodeinAware {
    override val kodein: Kodein by kodein()

    override var layoutId: Int = R.layout.activity_home

    private val loginViewModel: LoginViewModel by viewModel()

    //you can use it too
//    private val viewModeFactory: ViewModelProvider.Factory by instance()
//
//    private val loginViewModel: LoginViewModel by lazy {
//        ViewModelProviders.of(this, viewModeFactory).get(LoginViewModel::class.java)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        username.text = AppUtils.getStringFromShp(Constant.SALEMAN_NAME, this)
        date.text = Utils.getCurrentDate(false)

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
        Utils.askConfirmationDialog("Logout", "Do you want to logout?", "", this) {
            val intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }

}

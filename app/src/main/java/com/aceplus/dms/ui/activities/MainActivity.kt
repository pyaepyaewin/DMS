package com.aceplus.dms.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.aceplus.data.utils.Constant
import com.aceplus.dms.R
import com.aceplus.dms.utils.Utils
import com.aceplus.dms.viewmodel.LoginViewModel
import com.aceplussolutions.rms.constants.AppUtils
import com.aceplussolutions.rms.ui.activities.BaseActivity
import kotlinx.android.synthetic.main.activity_home.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein

class MainActivity : BaseActivity(), KodeinAware {
    override val kodein: Kodein by kodein()

    override var layoutId: Int = R.layout.activity_home

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

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
        buttonSync.setOnClickListener { startActivity(SyncActivity.newIntent(this)) }
        buttonRoute.setOnClickListener {
            if (loginViewModel.isCustomer()) {
                startActivity(RouteActivity.newIntent(this))
            } else {
                Utils.commonDialog("NO CUSTOMER", this, 1)
            }
        }
        buttonCustomerVisit.setOnClickListener { startActivity(CustomerVisitActivity.newIntent(this)) }
        buttonPromotion.setOnClickListener { startActivity(PromotionActivity.newIntent(this)) }
        button_vanIssue.setOnClickListener { startActivity(VanIssueActivity.newIntent(this)) }
        buttonReport.setOnClickListener { startActivity(ReportActivity.newIntent(this)) }

    }


    override fun onBackPressed() {
        Utils.askConfirmationDialog("Logout", "Do you want to logout?", "", this) { type ->
            val intent = LoginActivity.newIntent(this)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }

}

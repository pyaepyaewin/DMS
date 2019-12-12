package com.aceplus.dms.ui.activities.customer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.aceplus.data.utils.Constant
import com.aceplus.dms.R
import com.aceplus.dms.ui.activities.CustomerVisitActivity
import com.aceplus.dms.ui.fragments.customer.FragmentDeliveryReport
import com.aceplussolutions.rms.ui.activities.BaseActivity
import kotlinx.android.synthetic.main.activity_report.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein

class DeliveryActivity : BaseActivity(), KodeinAware {
    override val kodein: Kodein by kodein()
    override val layoutId: Int
        get() = R.layout.activity_report

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, DeliveryActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        reports.visibility = View.GONE
        reportTitle.text = "DELIVERY"
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_report, FragmentDeliveryReport())
        transaction.addToBackStack(null)
        transaction.commit()


        cancel_img.setOnClickListener {
            onBackPressed()
        }

    }

    override fun onBackPressed() {
        finish()
    }
}

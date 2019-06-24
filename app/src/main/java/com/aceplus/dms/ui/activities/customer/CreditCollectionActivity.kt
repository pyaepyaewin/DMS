package com.aceplus.dms.ui.activities.customer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.aceplus.dms.R
import com.aceplus.dms.ui.activities.CustomerVisitActivity
import com.aceplussolutions.rms.ui.activities.BaseActivity

class CreditCollectionActivity : BaseActivity() {
    override val layoutId: Int
        get() = R.layout.activity_credit_collect

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, CreditCollectionActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}

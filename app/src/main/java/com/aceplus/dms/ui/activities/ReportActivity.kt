package com.aceplus.dms.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.aceplus.dms.R
import com.aceplussolutions.rms.ui.activities.BaseActivity

class ReportActivity : BaseActivity() {
    override val layoutId: Int
        get() = R.layout.activity_report

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context,ReportActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}

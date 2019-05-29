package com.aceplus.dms.ui.activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.aceplus.dms.R
import com.aceplussolutions.rms.ui.activities.BaseActivity

class PromotionActivity : BaseActivity() {
    override val layoutId: Int
        get() = R.layout.activity_promotion


    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context,PromotionActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}

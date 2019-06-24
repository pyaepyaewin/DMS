package com.aceplus.dms.ui.activities.customer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.aceplus.dms.R
import com.aceplussolutions.rms.ui.activities.BaseActivity
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein

class CustomerLocationActivity : BaseActivity(), KodeinAware {
    override val kodein: Kodein by kodein()

    override val layoutId: Int
        get() = R.layout.activity_customer_location


    companion object {
        private const val IE_LATITUDE: String = "IE_LATITUDE"
        private const val IE_LONGITUDE: String = "IE_LONGITUDE"
        private const val IE_CUSTOMER_NAME: String = "IE_CUSTOMER_NAME"
        private const val IE_ADDRESS = "IE_ADDRESS"
        private const val IE_VISIT_RECORD = "IE_VISIT_RECORD"
        fun newIntent(
            context: Context,
            latitude: String,
            longitude: String,
            customerName: String,
            address: String,
            visitRecord: String
        ): Intent {
            val intent = Intent(context, CustomerLocationActivity::class.java)
            intent.putExtra(IE_LATITUDE, latitude)
            intent.putExtra(IE_LONGITUDE, longitude)
            intent.putExtra(IE_CUSTOMER_NAME, customerName)
            intent.putExtra(IE_ADDRESS, address)
            intent.putExtra(IE_VISIT_RECORD, visitRecord)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}

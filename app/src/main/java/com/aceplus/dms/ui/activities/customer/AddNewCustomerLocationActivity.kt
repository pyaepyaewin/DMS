package com.aceplus.dms.ui.activities.customer

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.aceplus.dms.R
import com.aceplus.domain.entity.customer.Customer
import com.aceplussolutions.rms.ui.activities.BaseActivity
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein

class AddNewCustomerLocationActivity : BaseActivity(), KodeinAware {
    override val kodein: Kodein by kodein()

    override val layoutId: Int
        get() = R.layout.activity_add_new_customer_location

    companion object {
        private const val IE_FROM = "IE_FROM"
        private const val IE_SALE_MAN_ID = "IE_SALE_MAN_ID"
        private const val IE_CUSTOMER_DATA = "IE_CUSTOMER_DATA"

        fun newIntentFromCustomerActivity(context: Context, salemanId: String, customer: Customer): Intent {
            val intent = Intent(context, AddNewCustomerLocationActivity::class.java)
            intent.putExtra(IE_FROM, "customerActivity")
            intent.putExtra(IE_SALE_MAN_ID, salemanId)
            intent.putExtra(IE_CUSTOMER_DATA, customer)
            return intent
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}

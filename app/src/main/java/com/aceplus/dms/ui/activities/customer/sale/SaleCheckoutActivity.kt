package com.aceplus.dms.ui.activities.customer.sale

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.aceplus.dms.R
import com.aceplus.domain.entity.customer.Customer
import com.aceplussolutions.rms.ui.activities.BaseActivity
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein

class SaleCheckoutActivity : BaseActivity(), KodeinAware {
    override val kodein: Kodein by kodein()

    override val layoutId: Int
        get() = R.layout.activity_sale_checkout

    companion object{

        private const val IE_CUSTOMER_DATA = "IE_CUSTOMER_DATA"

        fun newIntentFromSale(context: Context, customerData: Customer): Intent{
            val intent = Intent(context, SaleCheckoutActivity::class.java)
            intent.putExtra(IE_CUSTOMER_DATA, customerData)
            return intent
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getIntentData()

    }

    private fun getIntentData(){

        // ToDo - get intent data

    }
}

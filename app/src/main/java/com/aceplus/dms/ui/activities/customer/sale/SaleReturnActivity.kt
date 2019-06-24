package com.aceplus.dms.ui.activities.customer.sale

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.aceplus.dms.R
import com.aceplus.dms.ui.activities.customer.CustomerLocationActivity
import com.aceplus.domain.entity.customer.Customer
import com.aceplussolutions.rms.ui.activities.BaseActivity
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein

class SaleReturnActivity : BaseActivity(), KodeinAware {
    override val kodein: Kodein by kodein()

    override val layoutId: Int
        get() = R.layout.activity_sale_return


    companion object {
        private const val IE_SALE_EXCHANGE = "IE_SALE_EXCHANGE"
        private const val IE_CUSTOMER_DATA = "IE_CUSTOMER_DATA"

        fun newIntentFromCustomer(context: Context, isSaleExchange: String, customer: Customer): Intent {
            val intent = Intent(context, SaleReturnActivity::class.java)
            intent.putExtra(IE_SALE_EXCHANGE, isSaleExchange)
            intent.putExtra(IE_CUSTOMER_DATA, customer)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}

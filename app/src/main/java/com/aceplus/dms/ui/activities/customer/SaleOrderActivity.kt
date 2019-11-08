package com.aceplus.dms.ui.activities.customer

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.aceplus.dms.R
import com.aceplus.dms.ui.activities.customer.sale.SaleActivity
import com.aceplus.domain.entity.customer.Customer
import com.aceplussolutions.rms.ui.activities.BaseActivity
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein

class SaleOrderActivity : BaseActivity(), KodeinAware {

    override val kodein: Kodein by kodein()

    override val layoutId: Int
        get() = R.layout.activity_sale

    companion object {

        private const val IE_PRE_ORDER = "IE_PRE_ORDER"
        private const val IE_CUSTOMER_DATA = "IE_CUSTOMER_DATA"

        fun newIntentFromCustomer(context: Context, isPreOrder: Boolean, customerData: Customer): Intent {
            val intent = Intent(context, SaleOrderActivity::class.java)
            intent.putExtra(IE_PRE_ORDER, isPreOrder)
            intent.putExtra(IE_CUSTOMER_DATA, customerData)
            return intent
        }

        fun newIntentFromSaleExchange(context: Context, isPreOrder: String, customerId: String): Intent {
            val intent = Intent(context, SaleOrderActivity::class.java)
            intent.putExtra(IE_PRE_ORDER, isPreOrder)
            intent.putExtra(IE_CUSTOMER_DATA, customerId)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}

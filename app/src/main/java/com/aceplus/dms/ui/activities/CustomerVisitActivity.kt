package com.aceplus.dms.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.aceplus.dms.R
import com.aceplus.dms.ui.activities.customer.AddNewCustomerActivity
import com.aceplus.dms.ui.activities.customer.CreditCollectionActivity
import com.aceplus.dms.ui.activities.customer.CustomerActivity
import com.aceplus.dms.ui.activities.customer.DeliveryActivity
import com.aceplus.dms.ui.activities.customer.sale.SaleCancelActivity
import com.aceplus.dms.utils.Utils
import com.aceplussolutions.rms.ui.activities.BaseActivity
import kotlinx.android.synthetic.main.activity_customer_visit.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein

class CustomerVisitActivity : BaseActivity(), KodeinAware {
    override val kodein: Kodein by kodein()

    override val layoutId: Int
        get() = R.layout.activity_customer_visit

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, CustomerVisitActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        buttonCustomer.setOnClickListener { startActivity(CustomerActivity.newIntent(applicationContext)) }

        buttonAddNewCustomer.setOnClickListener { startActivity(AddNewCustomerActivity.newIntent(applicationContext)) }

        buttonCreditCollections.setOnClickListener { startActivity(CreditCollectionActivity.newIntent(applicationContext)) }

        buttonSaleExchange.setOnClickListener { startActivity(CustomerActivity.newIntentForSaleExchange(applicationContext, true)) }

        buttonDelivery.setOnClickListener { startActivity(DeliveryActivity.newIntent(applicationContext)) }

        buttonInvoiceCancel.setOnClickListener { startActivity(SaleCancelActivity.newIntentFromCustomer(applicationContext)) }

        cancel_img.setOnClickListener { onBackPressed() }
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    /*override fun onBackPressed() {
        super.onBackPressed()
        Utils.backToLogin(this)
    }*/
}

package com.aceplus.dms.ui.activities.customer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import com.aceplus.dms.R
import com.aceplus.domain.entity.customer.Customer
import com.aceplus.domain.model.SoldProduct
import com.aceplus.domain.model.delivery.Deliver
import com.aceplussolutions.rms.ui.activities.BaseActivity
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import java.util.*

class SaleOrderActivity : BaseActivity(), KodeinAware {
    override val kodein: Kodein by kodein()

    override val layoutId: Int
        get() = R.layout.activity_sale

    companion object {
        private const val IE_PRE_ORDER = "IE_PRE_ORDER"
        private const val IE_CUSTOMER_DATA = "IE_CUSTOMER_DATA"
        private const val IE_IS_DELIVERY = "is-delivery"
        private const val IE_CUSTOMER_INFO_KEY = "customer-info-key"
        private const val IE_SOLD_PROUDCT_LIST_KEY = "sold-product-list-key"
        private const val IE_ORDERED_INVOICE_KEY = "ordered_invoice_key"

        private var isDelivery: Boolean = false
        private var customer: Customer? = null
        private var soldProductList = ArrayList<SoldProduct>()
        private var orderedInvoice: Deliver? = null
        fun newIntentFromCustomer(
            context: Context,
            isPreOrder: Boolean,
            customerData: Customer
        ): Intent {
            val intent = Intent(context, SaleOrderActivity::class.java)
            intent.putExtra(IE_PRE_ORDER, isPreOrder)
            intent.putExtra(IE_CUSTOMER_DATA, customerData)
            return intent
        }

        fun newIntentFromSaleExchange(
            context: Context,
            isPreOrder: String,
            customerId: String
        ): Intent {
            val intent = Intent(context, SaleOrderActivity::class.java)
            intent.putExtra(IE_PRE_ORDER, isPreOrder)
            intent.putExtra(IE_CUSTOMER_DATA, customerId)
            return intent
        }

        fun newIntentFromDelivery(
            context: FragmentActivity,
            b: Boolean,
            customer: com.aceplus.domain.model.customer.Customer,
            soldProductList: ArrayList<SoldProduct>,
            deliver: Deliver
        ): Intent? {
            val intent = Intent(context, SaleOrderActivity::class.java)
            intent.putExtra(IE_IS_DELIVERY, b)
            intent.putExtra(IE_CUSTOMER_INFO_KEY, customer)
            intent.putExtra(IE_SOLD_PROUDCT_LIST_KEY, soldProductList)
            intent.putExtra(IE_ORDERED_INVOICE_KEY, deliver)
            return intent

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isDelivery = intent.getBooleanExtra(IE_IS_DELIVERY, false)
        customer = intent.getSerializableExtra(IE_CUSTOMER_INFO_KEY) as Customer

        if (intent.getSerializableExtra(IE_SOLD_PROUDCT_LIST_KEY) != null) {
            soldProductList =
                intent.getSerializableExtra(IE_SOLD_PROUDCT_LIST_KEY) as ArrayList<SoldProduct>
        }
        if (intent.getSerializableExtra(IE_ORDERED_INVOICE_KEY) != null) {

            orderedInvoice =
                intent.getSerializableExtra(IE_ORDERED_INVOICE_KEY) as Deliver
        }
    }
}

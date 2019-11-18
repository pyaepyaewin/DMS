package com.aceplus.dms.ui.activities.customer.sale

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.aceplus.data.utils.Constant
import com.aceplus.dms.R
import com.aceplus.dms.ui.activities.customer.CustomerLocationActivity
import com.aceplus.dms.utils.Utils
import com.aceplus.dms.viewmodel.customer.sale.SalesReturnViewModel
import com.aceplus.domain.entity.customer.Customer
import com.aceplussolutions.rms.ui.activities.BaseActivity
import kotlinx.android.synthetic.main.activity_sale_return.*
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

        fun newIntentFromCustomer(context: Context, isSaleExchange: Boolean, customer: Customer): Intent {
            val intent = Intent(context, SaleReturnActivity::class.java)
            intent.putExtra(IE_SALE_EXCHANGE, isSaleExchange)
            intent.putExtra(IE_CUSTOMER_DATA, customer)
            return intent
        }

    }

    private val salesReturnViewModel: SalesReturnViewModel by viewModel()

    private var isSaleExchange: Boolean = false
    private var customer: Customer? = null
    private var salePersonID: String? = null
    private var saleReturnID: String? = null
    private var locationCode: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN) // Hide keyboard on startup.

        getIntentData()
        initializeData()
        setupUI()
        catchEvents()

    }

    private fun getIntentData(){

        customer = intent.getParcelableExtra(IE_CUSTOMER_DATA)
        isSaleExchange = intent.getBooleanExtra(IE_SALE_EXCHANGE, false)

    }

    private fun initializeData(){

        salePersonID = salesReturnViewModel.getSaleManID()
        locationCode = salesReturnViewModel.getRouteID() // Check point - route id or location id - main thread

        saleReturnID = if (isSaleExchange)
            Utils.getInvoiceNo(salePersonID!!, locationCode.toString(), Constant.FOR_SALE_RETURN_EXCHANGE, salesReturnViewModel.getLastCountForInvoiceNumber(Constant.FOR_SALE_RETURN_EXCHANGE))
        else
            Utils.getInvoiceNo(salePersonID!!, locationCode.toString(), Constant.FOR_SALE_RETURN, salesReturnViewModel.getLastCountForInvoiceNumber(Constant.FOR_SALE_RETURN))
    }

    private fun setupUI(){

        if (isSaleExchange) tvTitle.text = resources.getString(R.string.sale_exchange)

    }

    private fun catchEvents(){

    }

}

package com.aceplus.dms.ui.activities.customer.sale

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.aceplus.dms.R
import com.aceplus.dms.utils.Utils
import com.aceplus.domain.entity.customer.Customer
import com.aceplus.domain.entity.promotion.Promotion
import com.aceplus.domain.vo.SoldProductInfo
import com.aceplussolutions.rms.ui.activities.BaseActivity
import kotlinx.android.synthetic.main.activity_sale_checkout.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein

class SaleCheckoutActivity : BaseActivity(), KodeinAware {
    override val kodein: Kodein by kodein()

    override val layoutId: Int
        get() = R.layout.activity_sale_checkout


    companion object{

        private const val IE_CUSTOMER_DATA = "IE_CUSTOMER_DATA"
        private const val IE_SOLD_PRODUCT_LIST = "IE_SOLD_PRODUCT_LIST"
        private const val IE_PROMOTION_LIST = "IE_PROMOTION_LIST"

        fun newIntentFromSale(context: Context, customerData: Customer, soldProductList: ArrayList<SoldProductInfo>, promotionList: ArrayList<Promotion>): Intent{
            val intent = Intent(context, SaleCheckoutActivity::class.java)
            intent.putExtra(IE_CUSTOMER_DATA, customerData)
            intent.putExtra(IE_SOLD_PRODUCT_LIST, soldProductList)
            intent.putExtra(IE_PROMOTION_LIST, promotionList)
            return intent
        }

    }

    private var customer: Customer? = null
    private var soldProductList: ArrayList<SoldProductInfo> = ArrayList()
    private var promotionList: ArrayList<Promotion> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getIntentData()
        setupUI()
        catchEvents()

    }

    private fun setupUI(){

        saleDateTextView.text = Utils.getDate(false)

        advancedPaidAmountLayout.visibility = View.GONE
        totalInfoForPreOrder.visibility = View.GONE
        tax_layout.visibility = View.VISIBLE
        tableHeaderDiscount.text = "Promotion Price"

    }

    private fun catchEvents(){

        back_img.setOnClickListener { onBackPressed() }

        confirmAndPrint_img.setOnClickListener {  }

    }

    private fun getIntentData(){

        customer = intent.getParcelableExtra(IE_CUSTOMER_DATA)
        soldProductList = intent.getSerializableExtra(IE_SOLD_PRODUCT_LIST) as ArrayList<SoldProductInfo> // Check serializable
        promotionList = intent.getParcelableArrayListExtra(IE_PROMOTION_LIST)

    }

}

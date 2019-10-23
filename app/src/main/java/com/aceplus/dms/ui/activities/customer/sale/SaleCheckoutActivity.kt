package com.aceplus.dms.ui.activities.customer.sale

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.CompoundButton
import com.aceplus.dms.R
import com.aceplus.dms.ui.adapters.sale.CheckoutSoldProductListAdapter
import com.aceplus.dms.utils.Utils
import com.aceplus.domain.entity.customer.Customer
import com.aceplus.domain.entity.promotion.Promotion
import com.aceplus.domain.vo.SoldProductInfo
import com.aceplussolutions.rms.ui.activities.BaseActivity
import kotlinx.android.synthetic.main.activity_sale_checkout.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SaleCheckoutActivity : BaseActivity(), KodeinAware {
    override val kodein: Kodein by kodein()

    override val layoutId: Int
        get() = R.layout.activity_sale_checkout


    companion object{

        private const val IE_CUSTOMER_DATA = "IE_CUSTOMER_DATA"
        private const val IE_SOLD_PRODUCT_LIST = "IE_SOLD_PRODUCT_LIST"
        private const val IE_PROMOTION_LIST = "IE_PROMOTION_LIST"
        private const val IE_SALE_EXCHANGE = "IE_SALE_EXCHANGE"

        fun newIntentFromSale(context: Context, customerData: Customer, soldProductList: ArrayList<SoldProductInfo>, promotionList: ArrayList<Promotion>): Intent{
            val intent = Intent(context, SaleCheckoutActivity::class.java)
            intent.putExtra(IE_CUSTOMER_DATA, customerData)
            intent.putExtra(IE_SOLD_PRODUCT_LIST, soldProductList)
            intent.putExtra(IE_PROMOTION_LIST, promotionList)
            return intent
        }

    }

    private val checkoutSoldProductListAdapter: CheckoutSoldProductListAdapter by lazy { CheckoutSoldProductListAdapter() }

    private val df = DecimalFormat(".##")
    private var customer: Customer? = null
    private var soldProductList: ArrayList<SoldProductInfo> = ArrayList()
    private var promotionList: ArrayList<Promotion> = ArrayList()
    private var totalAmount: Double = 0.0
    private var netAmount: Double = 0.0
    private var refundAmount: Double = 0.0
    private var taxType: String = ""
    private var taxPercent: Double = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        getIntentData()
        setupUI()
        catchEvents()

        checkoutSoldProductListAdapter.setNewList(soldProductList)
        calculateTotalAmount()
        setPromotionProductList()

    }

    private fun setupUI(){

        saleDateTextView.text = Utils.getDate(false)

        advancedPaidAmountLayout.visibility = View.GONE
        totalInfoForPreOrder.visibility = View.GONE
        tax_layout.visibility = View.VISIBLE
        tableHeaderDiscount.text = "Promotion Price"

        rvSoldProductList.adapter = checkoutSoldProductListAdapter
        rvSoldProductList.layoutManager = LinearLayoutManager(this)

    }

    private fun catchEvents(){

        back_img.setOnClickListener { onBackPressed() }
        confirmAndPrint_img.setOnClickListener {  }
        btn_disOk.setOnClickListener { calculateDiscPercentToAmt() }
        btn_amtOk.setOnClickListener { calculateDiscAmtToPercent() }
        edt_dueDate.setOnClickListener { chooseDueDate() }

        payAmount.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                calculateRefundAmount()
            }
        })

        activity_sale_checkout_radio_bank.setOnCheckedChangeListener { button, isChecked ->
            bank_branch_layout.visibility = if (isChecked) View.VISIBLE else View.GONE
            bank_account_layout.visibility = if (isChecked) View.VISIBLE else View.GONE
        }

    }

    private fun getIntentData(){

        customer = intent.getParcelableExtra(IE_CUSTOMER_DATA)
        soldProductList = intent.getParcelableArrayListExtra(IE_SOLD_PRODUCT_LIST)
        promotionList = intent.getParcelableArrayListExtra(IE_PROMOTION_LIST)

    }

    private fun setPromotionProductList(){

        // ToDo - show promo list

    }

    private fun calculateTotalAmount(){
        var total = 0.0
        for (soldProduct in soldProductList){
            total += soldProduct.totalAmt
        }
        totalAmount = total
        netAmount = total
        tvTotalAmount.text = Utils.formatAmount(total)
        tvNetAmount.text = total.toString()
    }

    private fun calculateDiscPercentToAmt(){

        if (edtVolumeDiscountPercent.text.toString().isNotBlank() && edtVolumeDiscountPercent.text.toString() != "."){
            val discountPercent = edtVolumeDiscountPercent.text.toString().toDouble()
            var discountAmount = totalAmount * discountPercent / 100
            var netAmount = totalAmount - discountAmount

            discountAmount = df.format(discountAmount).toDouble()
            netAmount = df.format(netAmount).toDouble()

            edtVolumeDiscountAmt.setText(discountAmount.toString())
            tvNetAmount.text = netAmount.toString()
            this.netAmount = netAmount
            calculateRefundAmount()
        }

    }

    private fun calculateDiscAmtToPercent(){

        if (edtVolumeDiscountAmt.text.toString().isNotBlank() && edtVolumeDiscountAmt.text.toString() != "."){
            val discountAmount = edtVolumeDiscountAmt.text.toString().toDouble()
            var discountPercent = 100 * (discountAmount / totalAmount)
            var netAmount = totalAmount - discountAmount

            discountPercent = df.format(discountPercent).toDouble()
            netAmount = df.format(netAmount).toDouble()

            edtVolumeDiscountPercent.setText(discountPercent.toString())
            tvNetAmount.text = netAmount.toString()
            this.netAmount = netAmount
            calculateRefundAmount()
        }

    }

    private fun calculateRefundAmount(){

        if (payAmount.text.isNotBlank()){
            val payAmount = payAmount.text.toString().toDouble()
            var refundAmount = payAmount - netAmount

            if (refundAmount >= 0){
                refundAmount = df.format(refundAmount).toDouble()
                refund.text = Utils.formatAmount(refundAmount)
            }
            else refund.text = "0"

            this.refundAmount = refundAmount
        }

    }

    private fun setInvoiceId(){

        try {
            val check = intent.getStringExtra(IE_SALE_EXCHANGE)
            if (check != null && !check.equals("yes", true)){
                // ToDo - get invoice id
            }
        } catch (e: NullPointerException){
            e.printStackTrace()
        }

    }

    private fun getTaxAmount(){
        // ToDo - get tax from database
    }

    private fun calculateTax(): Double{
        var taxAmt = 0.0
        if (taxPercent != 0.0) taxAmt = netAmount / 21
        return taxAmt
    }

    private fun chooseDueDate(){

        val myCalendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyy/MM/dd")

        val dateDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            myCalendar.set(year, month, dayOfMonth)
            edt_dueDate.setText(sdf.format(myCalendar.time))
        }, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH))

        dateDialog.show()

    }

}

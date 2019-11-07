package com.aceplus.dms.ui.activities.customer.saleorder

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.aceplus.dms.R
import com.aceplus.dms.utils.Utils
import com.aceplus.domain.entity.customer.Customer
import com.aceplus.domain.entity.promotion.Promotion
import com.aceplus.domain.vo.SoldProductInfo
import com.aceplussolutions.rms.ui.activities.BaseActivity
import kotlinx.android.synthetic.main.activity_sale_checkout.*
import kotlinx.android.synthetic.main.activity_sale_order_checkout.*
import kotlinx.android.synthetic.main.activity_sale_order_checkout.back_img
import kotlinx.android.synthetic.main.activity_sale_order_checkout.confirmAndPrint_img
import kotlinx.android.synthetic.main.activity_sale_order_checkout.saleDateTextView
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlinx.android.synthetic.main.activity_sale_checkout.activity_sale_checkout_radio_bank as activity_sale_checkout_radio_bank1
import kotlinx.android.synthetic.main.activity_sale_checkout.bank_account_layout as bank_account_layout1
import kotlinx.android.synthetic.main.activity_sale_checkout.btn_amtOk as btn_amtOk1
import kotlinx.android.synthetic.main.activity_sale_checkout.btn_disOk as btn_disOk1
import kotlinx.android.synthetic.main.activity_sale_checkout.checkout_delivery_date_chooser_text as checkout_delivery_date_chooser_text1
import kotlinx.android.synthetic.main.activity_sale_checkout.checkout_delivery_date_layout as checkout_delivery_date_layout1
import kotlinx.android.synthetic.main.activity_sale_checkout.checkout_remark_layout as checkout_remark_layout1
import kotlinx.android.synthetic.main.activity_sale_checkout.edtVolumeDiscountPercent as edtVolumeDiscountPercent1
import kotlinx.android.synthetic.main.activity_sale_checkout.netAmountLayout as netAmountLayout1
import kotlinx.android.synthetic.main.activity_sale_checkout.payAmountLayout as payAmountLayout1
import kotlinx.android.synthetic.main.activity_sale_checkout.tableHeaderDiscount as tableHeaderDiscount1
import kotlinx.android.synthetic.main.activity_sale_checkout.tableHeaderQty as tableHeaderQty1
import kotlinx.android.synthetic.main.activity_sale_checkout.tax_layout as tax_layout1
import kotlinx.android.synthetic.main.activity_sale_checkout.edt_dueDate as edt_dueDate1
import kotlinx.android.synthetic.main.activity_sale_checkout.tvNetAmount as tvNetAmount1
import kotlinx.android.synthetic.main.activity_sale_checkout.tvTotalAmount as tvTotalAmount1
import kotlinx.android.synthetic.main.activity_sale_order_checkout.advancedPaidAmountLayout as advancedPaidAmountLayout1
import kotlinx.android.synthetic.main.activity_sale_order_checkout.bank_branch_layout as bank_branch_layout1
import kotlinx.android.synthetic.main.activity_sale_order_checkout.duedateLayout as duedateLayout1
import kotlinx.android.synthetic.main.activity_sale_order_checkout.edtVolumeDiscountAmt as edtVolumeDiscountAmt1
import kotlinx.android.synthetic.main.activity_sale_order_checkout.layout_receipt_person as layout_receipt_person1
import kotlinx.android.synthetic.main.activity_sale_order_checkout.refundLayout as refundLayout1
import kotlinx.android.synthetic.main.activity_sale_order_checkout.tableHeaderUM as tableHeaderUM1
import kotlinx.android.synthetic.main.activity_sale_order_checkout.volDisForPreOrderLayout as volDisForPreOrderLayout1

class SaleOrderCheckoutActivity: BaseActivity(), KodeinAware {

    override val kodein: Kodein by kodein()

    override val layoutId: Int
        get() = R.layout.activity_sale_order_checkout


    companion object{

        private const val IE_CUSTOMER_DATA = "IE_CUSTOMER_DATA"
        private const val IE_SOLD_PRODUCT_LIST = "IE_SOLD_PRODUCT_LIST"
        private const val IE_PROMOTION_LIST = "IE_PROMOTION_LIST"
        private const val IE_IS_DELIVERY = "IE_IS_DELIVERY"

        fun getIntentFromSaleOrder(context: Context, customerData: Customer, soldProductList: ArrayList<SoldProductInfo>, promotionList: ArrayList<Promotion>): Intent{

            val intent = Intent(context, SaleOrderCheckoutActivity::class.java)
            intent.putExtra(IE_CUSTOMER_DATA, customerData)
            intent.putExtra(IE_SOLD_PRODUCT_LIST, soldProductList)
            intent.putExtra(IE_PROMOTION_LIST, promotionList)
            return intent

        }

    }

    private val df = DecimalFormat(".##")
    private var customer: Customer? = null
    private var isDelivery: Boolean = false
    private var soldProductList: ArrayList<SoldProductInfo> = ArrayList()
    private var promotionList: ArrayList<Promotion> = ArrayList()
    private var totalAmount: Double = 0.0
    private var netAmount: Double = 0.0
    private var volDisPercent: Double = 0.0
    private var volDisAmount:Double = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getIntentData()
        initializeData()
        setupUI()
        catchEvents()

    }

    private fun setupUI(){

        saleDateTextView.text = Utils.getDate(false)
        tableHeaderDiscount.text = "Promotion Price"
        tax_layout.visibility = View.VISIBLE
        checkout_remark_layout.visibility = View.VISIBLE
        tableHeaderQty.text = getString(R.string.ordered_qty)
        if (isDelivery) tableHeaderQty.text = getString(R.string.delivery_checkout)
        tableHeaderUM.visibility = View.GONE
        advancedPaidAmountLayout.visibility = View.GONE
        payAmountLayout.visibility = View.GONE
        refundLayout.visibility = View.GONE
        layout_receipt_person.visibility = View.GONE
        volDisForPreOrderLayout.visibility = View.GONE
        checkout_delivery_date_layout.visibility = View.VISIBLE
        duedateLayout.visibility = View.GONE

    }

    private fun initializeData(){

        calculateTotalAmount()
//        saleCheckoutViewModel.calculateFinalAmount(soldProductList, totalAmount)
//        salePersonId = saleCheckoutViewModel.getSaleManID()
//        locationCode = saleCheckoutViewModel.getRouteID() // Check point

    }

    private fun catchEvents(){

        back_img.setOnClickListener { onBackPressed() }
        btn_disOk.setOnClickListener { calculateDiscPercentToAmt() }
        btn_amtOk.setOnClickListener { calculateDiscAmtToPercent() }

        confirmAndPrint_img.setOnClickListener { Utils.askConfirmationDialog("Save", "Do you want to confirm?", "save", this, this::onClickSaveButton) }

        if (!isDelivery) checkout_delivery_date_chooser_text.setOnClickListener { chooseDeliveryDate() }

        activity_sale_checkout_radio_bank.setOnCheckedChangeListener { button, isChecked ->
            bank_branch_layout.visibility = if (isChecked) View.VISIBLE else View.GONE
            bank_account_layout.visibility = if (isChecked) View.VISIBLE else View.GONE
        }

    }

    private fun getIntentData(){

        isDelivery = intent.getBooleanExtra(IE_IS_DELIVERY, false)
        if (intent.getParcelableExtra<Customer>(IE_CUSTOMER_DATA) != null) customer = intent.getParcelableExtra(IE_CUSTOMER_DATA)
        if (intent.getParcelableArrayListExtra<SoldProductInfo>(IE_SOLD_PRODUCT_LIST) != null) soldProductList = intent.getParcelableArrayListExtra(IE_SOLD_PRODUCT_LIST)
        if (intent.getParcelableArrayListExtra<Promotion>(IE_PROMOTION_LIST) != null) promotionList = intent.getParcelableArrayListExtra(IE_PROMOTION_LIST)

    }

    private fun onClickSaveButton(type: String){

        if (type == "save"){

            Toast.makeText(this, "Save", Toast.LENGTH_SHORT).show()

        }

    }

    private fun chooseDeliveryDate(){

        val myCalendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyy/MM/dd")

        val dateDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            myCalendar.set(year, month, dayOfMonth)
            edt_dueDate.setText(sdf.format(myCalendar.time))
        }, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH))

        dateDialog.show()

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
            this.volDisAmount = discountAmount
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
            this.volDisAmount = discountAmount
        }

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

}
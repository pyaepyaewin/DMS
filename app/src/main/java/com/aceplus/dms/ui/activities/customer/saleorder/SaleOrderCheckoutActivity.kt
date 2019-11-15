package com.aceplus.dms.ui.activities.customer.saleorder

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.aceplus.dms.R
import com.aceplus.dms.ui.activities.customer.DeliveryActivity
import com.aceplus.dms.ui.fragments.customer.FragmentDeliveryReport
import com.aceplus.dms.utils.Utils
import com.aceplus.dms.viewmodel.customer.sale.SaleCheckoutViewModel
import com.aceplus.domain.entity.customer.Customer
import com.aceplus.domain.entity.promotion.Promotion
import com.aceplus.domain.model.delivery.Deliver
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
import kotlinx.android.synthetic.main.activity_sale_checkout.tax_label_saleCheckout as tax_label_saleCheckout1
import kotlinx.android.synthetic.main.activity_sale_checkout.tvNetAmount as tvNetAmount1
import kotlinx.android.synthetic.main.activity_sale_checkout.tvTotalAmount as tvTotalAmount1
import kotlinx.android.synthetic.main.activity_sale_checkout.tax_txtView as tax_txtView1
import kotlinx.android.synthetic.main.activity_sale_order_checkout.advancedPaidAmount as advancedPaidAmount1
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
        // For pre order
        private const val IS_PRE_ORDER = "is-pre-order"
        //For delivery
        private const val ORDERED_INVOICE_KEY = "ordered_invoice_key"


        fun getIntentFromSaleOrder(context: Context, customerData: Customer, soldProductList: ArrayList<SoldProductInfo>, promotionList: ArrayList<Promotion>): Intent{

            val intent = Intent(context, SaleOrderCheckoutActivity::class.java)
            intent.putExtra(IE_CUSTOMER_DATA, customerData)
            intent.putExtra(IE_SOLD_PRODUCT_LIST, soldProductList)
            intent.putExtra(IE_PROMOTION_LIST, promotionList)
            return intent

        }

        fun getIntent(saleOrderActivity: SaleOrderActivity, orderedInvoice: Deliver): Intent? {
            val intent = Intent(saleOrderActivity, SaleOrderCheckoutActivity::class.java)
            intent.putExtra(ORDERED_INVOICE_KEY,orderedInvoice)
            return intent
        }

    }

    private val saleCheckoutViewModel: SaleCheckoutViewModel by viewModel()

    private val df = DecimalFormat(".##")
    private var orderedInvoice: Deliver? = null
    private var isPreOrder: Boolean = false
    private var customer: Customer? = null
    private var isDelivery: Boolean = false
    private var soldProductList: ArrayList<SoldProductInfo> = ArrayList()
    private var promotionList: ArrayList<Promotion> = ArrayList()
    private var totalAmount: Double = 0.0
    private var netAmount: Double = 0.0
    private var totalVolumeDiscount: Double = 0.0
    private var totalVolumeDiscountPercent:Double = 0.0
    private var totalDiscountAmount:Double = 0.0
    private var volDisPercent: Double = 0.0
    private var volDisAmount:Double = 0.0
    private var taxType: String = ""
    private var taxPercent: Int = 0
    private var taxAmt: Double = 0.0


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
        if (isDelivery) {
            advancedPaidAmount.text = Utils.formatAmount(orderedInvoice!!.paidAmount)
        }
        if (isDelivery) {
            tvTitle.text = (R.string.delivery_checkout).toString()
        }

    }

    private fun initializeData(){

        calculateTotalAmount()
        saleCheckoutViewModel.calculateFinalAmount(soldProductList, totalAmount) // Check - should do only for pre-order
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

        saleCheckoutViewModel.finalData.observe(this, android.arch.lifecycle.Observer {
            if (it != null){
                totalVolumeDiscount = it.totalVolumeDiscount
                totalVolumeDiscountPercent = it.totalVolumeDiscountPercent
                taxType = it.taxType
                taxPercent = it.taxPercent

                val totalItemDisAmt = it.amountAndPercentage["Amount"] ?: 0.0

                if (!isDelivery)
                    displayFinalAmount(totalItemDisAmt)
                else
                    displayFinalDataForDelivery() // ToDo - need to update for delivery

            }
        })

    }

    private fun getIntentData(){
        isPreOrder = intent.getBooleanExtra(IS_PRE_ORDER, false)
        isDelivery = intent.getBooleanExtra(IE_IS_DELIVERY, false)
        if (intent.getParcelableExtra<Customer>(IE_CUSTOMER_DATA) != null) customer = intent.getParcelableExtra(IE_CUSTOMER_DATA)
        if (intent.getParcelableArrayListExtra<SoldProductInfo>(IE_SOLD_PRODUCT_LIST) != null) soldProductList = intent.getParcelableArrayListExtra(IE_SOLD_PRODUCT_LIST)
        if (intent.getParcelableArrayListExtra<Promotion>(IE_PROMOTION_LIST) != null) promotionList = intent.getParcelableArrayListExtra(IE_PROMOTION_LIST)
        if (intent.getSerializableExtra(ORDERED_INVOICE_KEY) != null) orderedInvoice = intent.getSerializableExtra(ORDERED_INVOICE_KEY) as Deliver

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
            checkout_delivery_date_chooser_text.setText(sdf.format(myCalendar.time))
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

    private fun displayFinalAmount(itemDisAmt: Double){

        val taxAmt = calculateTax()
        var netAmount = 0.0

        totalDiscountAmount = totalVolumeDiscount + itemDisAmt

        if (totalAmount != 0.0)
            totalVolumeDiscountPercent = totalDiscountAmount * 100 / totalAmount

        if (taxType.equals("E", true)){
            tax_label_saleCheckout.text = "Tax (Exclude) : "
            netAmount = totalAmount - totalDiscountAmount + taxAmt
        } else{
            tax_label_saleCheckout.text = "Tax (Include) : "
            netAmount = totalAmount - totalDiscountAmount
        }

        if (volDisAmount != 0.0) netAmount -= volDisAmount

        this.netAmount = netAmount
        tvNetAmount.text = Utils.formatAmount(netAmount)

        if (volDisPercent != 0.0) edtVolumeDiscountPercent.setText(volDisPercent.toString())
        else edtVolumeDiscountPercent.setText(totalVolumeDiscountPercent.toString())

        if (volDisAmount != 0.0) edtVolumeDiscountAmt.setText(volDisAmount.toString())
        else edtVolumeDiscountAmt.setText(totalDiscountAmount.toString())

        tax_txtView.text = df.format(taxAmt)

    }

    private fun displayFinalDataForDelivery(){

        if (taxType.equals("E", true)){
            tax_label_saleCheckout.text = "Tax (Exclude) : "
            netAmount = totalAmount + taxAmt
        } else{
            tax_label_saleCheckout.text = "Tax (Include) : "
            netAmount = totalAmount
        }

        // ToDo - for delivery

    }

    private fun calculateTax(): Double{

        var taxAmt = 0.0
        if (taxPercent != 0)
            taxAmt = netAmount / 21

        this.taxAmt = taxAmt

        return taxAmt

    }

    override fun onBackPressed() {
        if (isPreOrder){ }
        else if(isDelivery){ toDeliveryActivity()}
        super.onBackPressed()
    }
    private fun toDeliveryActivity(){
        val intent = Intent(this@SaleOrderCheckoutActivity, DeliveryActivity::class.java)
        startActivity(intent)
        finish()
    }

}
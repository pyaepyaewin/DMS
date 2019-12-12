package com.aceplus.dms.ui.activities.customer.saleorder

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.telephony.SmsManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.aceplus.data.utils.Constant
import com.aceplus.dms.R
import com.aceplus.dms.ui.activities.PrintInvoiceActivity
import com.aceplus.dms.ui.adapters.saleorder.OrderCheckoutListAdapter
import com.aceplus.dms.utils.SmsDeliveredReceiver
import com.aceplus.dms.utils.SmsSentReceiver
import com.aceplus.dms.ui.activities.customer.DeliveryActivity
import com.aceplus.dms.utils.Utils
import com.aceplus.dms.viewmodel.customer.delivery.DeliveryViewModel
import com.aceplus.dms.viewmodel.customer.sale.SaleCheckoutViewModel
import com.aceplus.domain.entity.SMSRecord
import com.aceplus.domain.entity.customer.Customer
import com.aceplus.domain.entity.delivery.DeliveryUpload
import com.aceplus.domain.entity.invoice.Invoice
import com.aceplus.domain.entity.promotion.Promotion
import com.aceplus.domain.model.delivery.Deliver
import com.aceplus.domain.model.forApi.delivery.DeliveryApi
import com.aceplus.domain.model.forApi.delivery.DeliveryItemApi
import com.aceplus.domain.repo.CustomerVisitRepo
import com.aceplus.domain.repo.deliveryrepo.DeliveryRepo
import com.aceplus.domain.vo.SoldProductInfo
import com.aceplussolutions.rms.constants.AppUtils
import com.aceplussolutions.rms.ui.activities.BaseActivity
import kotlinx.android.synthetic.main.activity_sale_order_checkout.*
import kotlinx.android.synthetic.main.activity_sale_order_checkout.back_img
import kotlinx.android.synthetic.main.activity_sale_order_checkout.confirmAndPrint_img
import kotlinx.android.synthetic.main.activity_sale_order_checkout.saleDateTextView
import kotlinx.android.synthetic.main.activity_sale_order_checkout.view.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import java.lang.Exception
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt
import kotlinx.android.synthetic.main.activity_sale_checkout.activity_sale_checkout_radio_bank as activity_sale_checkout_radio_bank1
import kotlinx.android.synthetic.main.activity_sale_checkout.activity_sale_checkout_radio_group as activity_sale_checkout_radio_group1
import kotlinx.android.synthetic.main.activity_sale_checkout.bank_account_layout as bank_account_layout1
import kotlinx.android.synthetic.main.activity_sale_checkout.btn_amtOk as btn_amtOk1
import kotlinx.android.synthetic.main.activity_sale_checkout.btn_disOk as btn_disOk1
import kotlinx.android.synthetic.main.activity_sale_checkout.checkout_delivery_date_chooser_text as checkout_delivery_date_chooser_text1
import kotlinx.android.synthetic.main.activity_sale_checkout.checkout_delivery_date_layout as checkout_delivery_date_layout1
import kotlinx.android.synthetic.main.activity_sale_checkout.checkout_remark_layout as checkout_remark_layout1
import kotlinx.android.synthetic.main.activity_sale_checkout.edit_txt_account_name as edit_txt_account_name1
import kotlinx.android.synthetic.main.activity_sale_checkout.edit_txt_branch_name as edit_txt_branch_name1
import kotlinx.android.synthetic.main.activity_sale_checkout.edtVolumeDiscountPercent as edtVolumeDiscountPercent1
import kotlinx.android.synthetic.main.activity_sale_checkout.netAmountLayout as netAmountLayout1
import kotlinx.android.synthetic.main.activity_sale_checkout.payAmountLayout as payAmountLayout1
import kotlinx.android.synthetic.main.activity_sale_checkout.tableHeaderDiscount as tableHeaderDiscount1
import kotlinx.android.synthetic.main.activity_sale_checkout.tableHeaderQty as tableHeaderQty1
import kotlinx.android.synthetic.main.activity_sale_checkout.tax_layout as tax_layout1
import kotlinx.android.synthetic.main.activity_sale_checkout.edt_dueDate as edt_dueDate1
import kotlinx.android.synthetic.main.activity_sale_checkout.payAmount as payAmount1
import kotlinx.android.synthetic.main.activity_sale_checkout.receiptPerson as receiptPerson1
import kotlinx.android.synthetic.main.activity_sale_checkout.tax_label_saleCheckout as tax_label_saleCheckout1
import kotlinx.android.synthetic.main.activity_sale_checkout.tvNetAmount as tvNetAmount1
import kotlinx.android.synthetic.main.activity_sale_checkout.tvTotalAmount as tvTotalAmount1
import kotlinx.android.synthetic.main.activity_sale_checkout.tax_txtView as tax_txtView1
import kotlinx.android.synthetic.main.activity_sale_checkout.tvInvoiceId as tvInvoiceId1
import kotlinx.android.synthetic.main.activity_sale_checkout.checkout_remark_edit_text as checkout_remark_edit_text1
import kotlinx.android.synthetic.main.activity_sale_checkout.llSaleStatus as llSaleStatus1
import kotlinx.android.synthetic.main.activity_sale_checkout.paymentMethodLayout as paymentMethodLayout1
import kotlinx.android.synthetic.main.activity_sale_checkout.tvPrepaidAmount as tvPrepaidAmount1
import kotlinx.android.synthetic.main.activity_sale_order_checkout.advancedPaidAmount as advancedPaidAmount1
import kotlinx.android.synthetic.main.activity_sale_order_checkout.advancedPaidAmountLayout as advancedPaidAmountLayout1
import kotlinx.android.synthetic.main.activity_sale_order_checkout.bank_branch_layout as bank_branch_layout1
import kotlinx.android.synthetic.main.activity_sale_order_checkout.duedateLayout as duedateLayout1
import kotlinx.android.synthetic.main.activity_sale_order_checkout.edtVolumeDiscountAmt as edtVolumeDiscountAmt1
import kotlinx.android.synthetic.main.activity_sale_order_checkout.layout_receipt_person as layout_receipt_person1
import kotlinx.android.synthetic.main.activity_sale_order_checkout.refundLayout as refundLayout1
import kotlinx.android.synthetic.main.activity_sale_order_checkout.rvSoldProductList as rvSoldProductList1
import kotlinx.android.synthetic.main.activity_sale_order_checkout.saleDateLayout as saleDateLayout1
import kotlinx.android.synthetic.main.activity_sale_order_checkout.tableHeaderUM as tableHeaderUM1
import kotlinx.android.synthetic.main.activity_sale_order_checkout.totalvolumeDiscountLayout as totalvolumeDiscountLayout1
import kotlinx.android.synthetic.main.activity_sale_order_checkout.volDisForPreOrderLayout as volDisForPreOrderLayout1
import kotlinx.android.synthetic.main.activity_sale_order_checkout.volumeDiscountLayout as volumeDiscountLayout1
import kotlinx.android.synthetic.main.activity_sale_order_checkout.volumeDiscountLayout2 as volumeDiscountLayout21

class SaleOrderCheckoutActivity: BaseActivity(), KodeinAware {
    override val kodein: Kodein by kodein()

    override val layoutId: Int
        get() = R.layout.activity_sale_order_checkout

    companion object{

        private const val IE_CUSTOMER_DATA = "IE_CUSTOMER_DATA"
        private const val IE_SOLD_PRODUCT_LIST = "IE_SOLD_PRODUCT_LIST"
        private const val IE_PROMOTION_LIST = "IE_PROMOTION_LIST"
        private const val IE_IS_DELIVERY = "IE_IS_DELIVERY"
        private const val ORDERED_INVOICE_KEY = "ordered_invoice_key"
        private const val RC_REQUEST_SEND_SMS = 101

        fun getIntentFromSaleOrder(
            context: Context,
            customerData: Customer,
            soldProductList:
            ArrayList<SoldProductInfo>,
            promotionList: ArrayList<Promotion>
        ): Intent{
            val intent = Intent(context, SaleOrderCheckoutActivity::class.java)
            intent.putExtra(IE_CUSTOMER_DATA, customerData)
            intent.putExtra(IE_SOLD_PRODUCT_LIST, soldProductList)
            intent.putExtra(IE_PROMOTION_LIST, promotionList)
            return intent
        }

        fun getIntentForDeliveryFromSaleOrder(
            saleOrderActivity: SaleOrderActivity,
            isDelivery:Boolean,
            soldProductList: java.util.ArrayList<SoldProductInfo>,
            orderedInvoice:Deliver,
            deliveryCustomer:Customer
        ): Intent? {
            val intent = Intent(saleOrderActivity, SaleOrderCheckoutActivity::class.java)
            intent.putExtra(IE_SOLD_PRODUCT_LIST,soldProductList)
            intent.putExtra(ORDERED_INVOICE_KEY,orderedInvoice)
            intent.putExtra(IE_IS_DELIVERY,isDelivery)
            intent.putExtra(IE_CUSTOMER_DATA,deliveryCustomer)
            return intent
        }

    }

    private val saleCheckoutViewModel: SaleCheckoutViewModel by viewModel()
    private val deliveryCheckoutViewModel: DeliveryViewModel by viewModel()
    private val orderCheckoutListAdapter: OrderCheckoutListAdapter by lazy { OrderCheckoutListAdapter() }

    private val df = DecimalFormat(".##")
    private var orderedInvoice: Deliver? = null
    private var customer: Customer? = null
    private var isDelivery: Boolean = false
    private var soldProductList: ArrayList<SoldProductInfo> = ArrayList()
    private var promotionList: ArrayList<Promotion> = ArrayList()
    private var totalAmount: Double = 0.0
    private var netAmount: Double = 0.0
    private var taxType: String = ""
    private var taxPercent: Int = 0
    private var taxAmt: Double = 0.0
    private var salePersonId: String? = null
    private var locationCode: Int = 0
    private var invoiceId: String = ""
    private var invoice: Invoice? = null
    private var selectedDeliveryDate: String? = null
    private var routeID: Int = 0

    private var sale_man_id = ""                            //Should be the same
    private var saleManId = ""                              //Should be the same
    private var saleManNo = ""                              //Should be the same

    private var totalVolumeDiscount: Double = 0.0           //Disc by date
    private var totalVolumeDiscountPercent:Double = 0.0     //Disc by date
    private var salesmanDisAmount: Double = 0.0             //Disc by salesman
    private var salesmanDisPercent:Double = 0.0             //Disc by salesman
    private var totalItemDiscountAmount: Double = 0.0       //Disc by amount
    private var totalItemDiscountPercent: Double = 0.0      //Disc by amount
    private var totalDiscountAmount:Double = 0.0            //Total of disc
    private var totalDiscountPercent:Double = 0.0           //Total of disc


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        saleManId = AppUtils.getStringFromShp(Constant.SALEMAN_ID, this)!!
        saleManNo = AppUtils.getStringFromShp(Constant.SALEMAN_NO, this)!!

        getIntentData()
        initializeData()
        setupUI()
        catchEvents()

    }

    private fun setupUI(){

        if (isDelivery){
            deliveryHeaderLayout.tvTitle.text = "DELIVERY CHECKOUT"
            tvInvoiceId.text = orderedInvoice!!.invoiceNo
            tvNetAmount.text = (orderedInvoice!!.amount - orderedInvoice!!.paidAmount).toString()
            tvTotalAmount.text = orderedInvoice!!.amount.toString()

            btn_disOk.visibility = View.GONE
            btn_amtOk.visibility = View.GONE
            paymentMethodLayout.visibility = View.GONE
            refundLayout1.visibility = View.GONE
            llSaleStatus.visibility = View.GONE
            duedateLayout1.visibility = View.GONE
            payAmountLayout.visibility = View.VISIBLE
            checkout_delivery_date_layout.visibility = View.GONE
            checkout_remark_layout.visibility = View.GONE
            layout_receipt_person1.visibility = View.VISIBLE
            payAmountLayout.visibility = View.VISIBLE
            tableHeaderUM1.visibility = View.GONE
            tableHeaderQty.text = getString(R.string.ordered_qty)
            tableHeaderDiscount.text = "Promotion Price"
            advancedPaidAmountLayout1.visibility = View.GONE
            volDisForPreOrderLayout1.visibility = View.GONE
        }
        else{
            saleDateTextView.text = Utils.getDate(false)
            tableHeaderDiscount.text = "Promotion Price"
            tax_layout.visibility = View.VISIBLE
            checkout_remark_layout.visibility = View.VISIBLE
            tableHeaderQty.text = getString(R.string.ordered_qty)
            tableHeaderUM1.visibility = View.GONE
            advancedPaidAmountLayout1.visibility = View.GONE
            payAmountLayout.visibility = View.GONE
            refundLayout1.visibility = View.GONE
            layout_receipt_person1.visibility = View.GONE
            volDisForPreOrderLayout1.visibility = View.GONE
            checkout_delivery_date_layout.visibility = View.VISIBLE
            duedateLayout1.visibility = View.GONE
        }

        rvSoldProductList1.adapter = orderCheckoutListAdapter
        rvSoldProductList1.layoutManager = LinearLayoutManager(this)

        orderCheckoutListAdapter.setNewList(soldProductList)

    }

    private fun initializeData(){

        calculateTotalAmount()
        saleCheckoutViewModel.calculateFinalAmount(soldProductList, totalAmount) //Check - should do only for pre-order
        salePersonId = saleCheckoutViewModel.getSaleManID()
        locationCode = saleCheckoutViewModel.getLocationCode()
        routeID = saleCheckoutViewModel.getRouteID()

        if (isDelivery) deliveryCheckoutViewModel.loadRouteId(saleManId) //exceeding

    }

    private fun catchEvents(){

        back_img.setOnClickListener { onBackPressed() }
        btn_disOk.setOnClickListener { calculateDiscPercentToAmt() }
        btn_amtOk.setOnClickListener { calculateDiscAmtToPercent() }

        confirmAndPrint_img.setOnClickListener {
            Utils.askConfirmationDialog("Save", "Do you want to confirm?", "save", this, this::onClickSaveButton)
        }

        if (!isDelivery) checkout_delivery_date_chooser_text.setOnClickListener { chooseDeliveryDate() }

        activity_sale_checkout_radio_bank.setOnCheckedChangeListener { button, isChecked ->
            bank_branch_layout1.visibility = if (isChecked) View.VISIBLE else View.GONE
            bank_account_layout.visibility = if (isChecked) View.VISIBLE else View.GONE
        }

        saleCheckoutViewModel.finalData.observe(this, android.arch.lifecycle.Observer {
            if (it != null){
                totalVolumeDiscount = it.totalVolumeDiscount
                totalVolumeDiscountPercent = it.totalVolumeDiscountPercent
                taxType = it.taxType
                taxPercent = it.taxPercent

                totalItemDiscountAmount = it.amountAndPercentage["Amount"] ?: 0.0
                totalItemDiscountPercent = it.amountAndPercentage["Percentage"] ?: 0.0

                if (!isDelivery)
                    displayFinalAmount()
                else
                    displayFinalDataForDelivery()
            }
        })

        saleCheckoutViewModel.invoice.observe(this, android.arch.lifecycle.Observer {
            if (it != null){
                invoice = it
                saleCheckoutViewModel.invoice.value = null
                if (Utils.isInternetAccess(this)){
                    Utils.callDialog("Please wait...", this)
                    saleCheckoutViewModel.getPreOrderRequest(salePersonId!!, locationCode.toString())
                }
                else
                    sendSMSMessage()
            }
        })

        saleCheckoutViewModel.messageInfo.observe(this, android.arch.lifecycle.Observer {
            if (it != null){
                sendSMS(it.first, it.second)
                insertSMSRecord(it.first, it.second)
                //To check - found no update commend for delete flag
            }
        })

        saleCheckoutViewModel.uploadResult.observe(this, android.arch.lifecycle.Observer { uploaded ->
            if (uploaded != null){
                if (uploaded){
                    Utils.cancelDialog()
                    Snackbar.make(rvSoldProductList1, resources.getString(R.string.upload_succes_msg), Snackbar.LENGTH_INDEFINITE)
                        .setAction("Done"){
                            toPrintActivity("S")
                        }
                        .show()
                } else{
                    sendSMSMessage()
                }
                saleCheckoutViewModel.uploadResult.value = null
            }
        })

        deliveryCheckoutViewModel.routeDataList.observe(this,android.arch.lifecycle.Observer {
            sale_man_id = it!!.sale_man_id
        })

    }

    private fun getIntentData(){

        isDelivery = intent.getBooleanExtra(IE_IS_DELIVERY, false)
        customer = intent.getParcelableExtra(IE_CUSTOMER_DATA)
        soldProductList = intent.getParcelableArrayListExtra(IE_SOLD_PRODUCT_LIST)

        if (intent.getParcelableArrayListExtra<Promotion>(IE_PROMOTION_LIST) != null)
            promotionList = intent.getParcelableArrayListExtra(IE_PROMOTION_LIST)

        if (intent.getSerializableExtra(ORDERED_INVOICE_KEY) != null)
            orderedInvoice = intent.getSerializableExtra(ORDERED_INVOICE_KEY) as Deliver

    }

    private fun onClickSaveButton(type: String){

        if (type == "save"){
            var paymentMethod = when(activity_sale_checkout_radio_group.checkedRadioButtonId){
                R.id.activity_sale_checkout_radio_bank -> "B"
                R.id.activity_sale_checkout_radio_cash -> "CA"
                else -> ""
            }
            val paidAmount = if (tvPrepaidAmount.text.isNotBlank()) tvPrepaidAmount.text.toString().toDouble() else 0.0
            if (!isDelivery){
                if (validationInput(paymentMethod == "B", paidAmount)){
                    if (paidAmount < netAmount){
                        setInvoiceId()
                        savePreOrderInformation("CR")
                    } else{
                        setInvoiceId()
                        savePreOrderInformation("CA")
                    }
                }
            }
            else{
                val varPayAmount = if (payAmount.text.isNotBlank()) payAmount.text.toString().toDouble() else 0.0
                val nAmount = orderedInvoice!!.amount - orderedInvoice!!.paidAmount
                when {
                    varPayAmount > nAmount -> {
                        AlertDialog.Builder(this@SaleOrderCheckoutActivity)
                            .setTitle("Delivery")
                            .setMessage("Your pay amount must be less than Net Amount.")
                            .setPositiveButton("OK") { arg0, arg1 ->
                                payAmount.setText("0")
                                payAmount.requestFocus()
                            }
                            .show()

                        return
                    }
                    this@SaleOrderCheckoutActivity.receiptPerson.text.isEmpty() -> {
                        AlertDialog.Builder(this@SaleOrderCheckoutActivity)
                            .setTitle("Delivery")
                            .setMessage("You must provide 'Receipt Person'.")
                            .setPositiveButton(
                                "OK"
                            ) { arg0, arg1 -> receiptPerson.requestFocus() }
                            .show()
                        return
                    }
                    else -> toPrintActivity("D")
                }


            }

        }

    }

    private fun chooseDeliveryDate(){

        val myCalendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyy/MM/dd")
        val sdf2 = SimpleDateFormat("yyyy-MM-dd")

        val dateDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            myCalendar.set(year, month, dayOfMonth)
            checkout_delivery_date_chooser_text.setText(sdf.format(myCalendar.time))
            selectedDeliveryDate = sdf2.format(myCalendar.time)
        }, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH))

        dateDialog.show()

    }

    private fun calculateDiscPercentToAmt(){

        if (edtVolumeDiscountPercent.text.toString().isNotBlank() && edtVolumeDiscountPercent.text.toString() != "."){
            val discountPercent = edtVolumeDiscountPercent.text.toString().toDouble()
            var discountAmount = totalAmount * discountPercent / 100

            discountAmount = df.format(discountAmount).toDouble()
            edtVolumeDiscountAmt1.setText(discountAmount.toString())

            this.salesmanDisAmount = discountAmount
            this.salesmanDisPercent = discountPercent

            displayFinalAmount()
        }

    }

    private fun calculateDiscAmtToPercent(){

        if (edtVolumeDiscountAmt1.text.toString().isNotBlank() && edtVolumeDiscountAmt1.text.toString() != "."){
            val discountAmount = edtVolumeDiscountAmt1.text.toString().toDouble()
            var discountPercent = 100 * (discountAmount / totalAmount)

            discountPercent = df.format(discountPercent).toDouble()
            edtVolumeDiscountPercent.setText(discountPercent.toString())

            this.salesmanDisAmount = discountAmount
            this.salesmanDisPercent = discountPercent

            displayFinalAmount()
        }

    }

    private fun calculateTotalAmount(){

        var total = 0.0
        for (soldProduct in soldProductList){
            total += soldProduct.totalAmt
        }
        totalAmount = total
        tvTotalAmount.text = Utils.formatAmount(total)

    }

    private fun displayFinalAmount(){

        totalDiscountAmount = totalVolumeDiscount + totalItemDiscountAmount + salesmanDisAmount
        totalDiscountPercent = totalDiscountAmount * 100 / totalAmount  //check after fixing disc

        val taxAmt = calculateTax()
        var netAmount: Double

        if (taxType.equals("E", true)){
            tax_label_saleCheckout.text = "Tax (Exclude) : "
            netAmount = totalAmount - totalDiscountAmount + taxAmt
        } else{
            tax_label_saleCheckout.text = "Tax (Include) : "
            netAmount = totalAmount - totalDiscountAmount
        }

        this.netAmount = netAmount
        tvNetAmount.text = Utils.formatAmount(netAmount)
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

        val volDiscount = java.lang.Double.parseDouble(String.format("%.3f", totalAmount * orderedInvoice!!.discountPercent / 100))
        orderedInvoice!!.discount = volDiscount
        netAmount = java.lang.Double.parseDouble(String.format("%.3f", netAmount - orderedInvoice!!.discount - orderedInvoice!!.paidAmount))
        edtVolumeDiscountAmt1.setText("${orderedInvoice!!.discount}")
        edtVolumeDiscountPercent.setText("${orderedInvoice!!.discountPercent}")
        tvPrepaidAmount.setText("${orderedInvoice!!.paidAmount}")
        edtVolumeDiscountAmt1.isEnabled = false
        edtVolumeDiscountPercent.isEnabled = false
        tvPrepaidAmount.isEnabled = false
        tax_txtView.text = Utils.formatAmount((orderedInvoice!!.amount - orderedInvoice!!.discount)/21)

    }

    private fun calculateTax(): Double{

        var taxAmt = 0.0
        if (taxPercent != 0)
            taxAmt = (totalAmount - totalDiscountAmount) / 21

        this.taxAmt = taxAmt
        return taxAmt

    }

    private fun validationInput(withBankInfo: Boolean, paidAmount: Double): Boolean{

        var deliDate = false
        var bank = false
        var acc = false

        if (paidAmount > netAmount){
            AlertDialog.Builder(this)
                .setTitle("Delivery")
                .setMessage("Your pay amount must be less than Net Amount.")
                .setPositiveButton("OK") { dialog, which ->
                    payAmount.setText("0")
                    payAmount.requestFocus()
                }
                .show()

            return false
        }
        else{
            if (withBankInfo && paidAmount < netAmount){
                Utils.commonDialog("Insufficient Pay Amount!", this, 1)
                return false
            }
        }

        if (!isDelivery){

            if (checkout_delivery_date_chooser_text.text.isBlank())
                checkout_delivery_date_chooser_text.error = "Please enter DELIVERY DATE"
            else
                deliDate = true

            return if (withBankInfo){
                if (edit_txt_branch_name.text.isNotBlank()) bank = true
                else edit_txt_branch_name.error = "Please enter bank name"

                if (edit_txt_account_name.text.isNotBlank()) acc = true
                else edit_txt_account_name.error = "Please enter bank account"

                deliDate && bank && acc
            } else{
                deliDate
            }

        } else{

            if (receiptPerson.text.isBlank()){
                AlertDialog.Builder(this)
                    .setTitle("Delivery")
                    .setMessage("You must provide 'Receipt Person'.")
                    .setPositiveButton("OK") { dialog, which ->
                        receiptPerson.requestFocus()
                    }
                    .show()

                return false
            }

            return true
        }

    }

    private fun setInvoiceId(){

        val invoiceCount = AppUtils.getIntFromShp(Constant.INVOICE_COUNT, this) ?: 0
        if (invoiceCount >= 0) AppUtils.saveIntToShp(Constant.INVOICE_COUNT, invoiceCount + 1, this)
        val invoiceID = saleCheckoutViewModel.getInvoiceNumber( salePersonId!!, locationCode, Constant.FOR_PRE_ORDER_SALE)
        tvInvoiceId.text = invoiceID
        this.invoiceId = invoiceID

    }

    private fun savePreOrderInformation(cashOrLoanOrBank: String){

        saleCheckoutViewModel.updateDepartureTimeForSaleManRoute( salePersonId!!, customer!!.id.toString())
        saleCheckoutViewModel.updateSaleVisitRecord(customer!!.id)

        val customerId = customer!!.id
        val preOrderDate = Utils.getCurrentDate(true)
        val deliveryDate = selectedDeliveryDate.toString()
        val advancedPaymentAmount = if (tvPrepaidAmount.text.isNotBlank()) tvPrepaidAmount.text.toString().toDouble() else 0.0

        saleCheckoutViewModel.saveOrderData(
            invoiceId,
            customerId,
            salePersonId!!,
            Utils.getDeviceId(this),
            preOrderDate,
            deliveryDate,
            advancedPaymentAmount,
            totalAmount,
            routeID,
            salesmanDisAmount,
            salesmanDisPercent,
            taxAmt,
            checkout_remark_edit_text.text.toString(),
            edit_txt_branch_name.text.toString(),
            edit_txt_account_name.text.toString(),
            cashOrLoanOrBank,
            soldProductList,
            promotionList
        )

    }

    private fun sendSMSMessage(){

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){

            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)){

                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.SEND_SMS), RC_REQUEST_SEND_SMS)

            }

        } else showDialogForPhoneNumber()

    }

    private fun showDialogForPhoneNumber(){

        val layoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.dialog_box_sale_quantity, null)

        val availableQuantityLayout = view.findViewById(R.id.availableQuantityLayout) as LinearLayout
        val qtyTextView = view.findViewById(R.id.dialog_sale_qty_txtView) as TextView
        val phoneNoEditText = view.findViewById(R.id.quantity) as EditText
        val messageTextView = view.findViewById(R.id.message) as TextView

        availableQuantityLayout.visibility = View.GONE
        qtyTextView.visibility = View.GONE

        val alertDialog = AlertDialog.Builder(this)
            .setView(view)
            .setTitle("No internet access !\nPlease enter Phone Number to send message")
            .setPositiveButton("Confirm", null)
            .setNegativeButton("Cancel", null)
            .create()

        alertDialog.setOnShowListener {

            availableQuantityLayout.visibility = View.GONE

            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {

                if (phoneNoEditText.text.isBlank()){
                    messageTextView.text = "You must specify Phone Number."
                    return@setOnClickListener
                }

                val phoneNo = phoneNoEditText.text.toString()
                if (phoneNo.isNotBlank()){
                    saleCheckoutViewModel.getMessageInfo(phoneNo, invoiceId, promotionList, checkout_remark_edit_text.text.toString())
                    toPrintActivity("S")
                }

                alertDialog.dismiss()
            }

            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener {
                alertDialog.dismiss()
                toPrintActivity("S")
            }

        }

        alertDialog.show()

    }

    private fun sendSMS(phoneNumber: String, message: String){

        val sentPendingIntents = ArrayList<PendingIntent>()
        val deliveredPendingIntents = ArrayList<PendingIntent>()

        val smsSentReceiverIntent = Intent(this, SmsSentReceiver::class.java)
        val smsDeliveredReceiverIntent = Intent(this, SmsDeliveredReceiver::class.java)

        val sentPI = PendingIntent.getBroadcast(this, 0, smsSentReceiverIntent, 0)
        val deliveredPI = PendingIntent.getBroadcast(this, 0, smsDeliveredReceiverIntent, 0)

        try {
            val sms = SmsManager.getDefault()
            val mSMSMessage = sms.divideMessage(message)
            for (i in mSMSMessage.indices){
                sentPendingIntents.add(i, sentPI)
                deliveredPendingIntents.add(i, deliveredPI)
            }
            sms.sendMultipartTextMessage(phoneNumber, null, mSMSMessage, sentPendingIntents, deliveredPendingIntents)
            Log.d("Testing", "Send msg")
        } catch (e: Exception){
            e.printStackTrace()
            Toast.makeText(baseContext, "SMS sending failed...", Toast.LENGTH_SHORT).show()
        }

    }

    private fun insertSMSRecord(phoneNumber: String, message: String){

        val smsRecord = SMSRecord()

        smsRecord.send_date = Utils.getCurrentDate(true)
        smsRecord.message_body = message
        smsRecord.phone_no = phoneNumber

        saleCheckoutViewModel.saveSmsRecord(smsRecord)

    }

    private fun toPrintActivity(mode:String){
        if (mode == "S") {
            val intent = PrintInvoiceActivity.newIntentFromSaleOrderCheckout(this, invoice!!, soldProductList, promotionList)
            startActivityForResult(intent, Constant.RQC_BACK_TO_CUSTOMER)
        }else{
            insertDeliveryDataToDatabase()
            invoice!!.pay_amount = payAmount.text.toString()
            val intent = PrintInvoiceActivity.newIntent(this,soldProductList,"D",orderedInvoice!!,customer!!,invoice!!)
            startActivityForResult(intent, Constant.RQC_BACK_TO_CUSTOMER)
        }
    }

    private fun insertDeliveryDataToDatabase(){
        val saleDate = Utils.getCurrentDate(true)
        val invoiceId1 = saleCheckoutViewModel.getInvoiceNumber( saleManNo, locationCode, Constant.FOR_DELIVERY)
        val paidAmount = if (tvPrepaidAmount.text.isNotEmpty())
            java.lang.Double.parseDouble(tvPrepaidAmount.text.toString().replace(",", ""))
        else
            0
        val dueDate = Utils.getCurrentDate(true)
        val invoiceTime = Utils.getCurrentDate(true)
        val totalQuantity = deliveryCheckoutViewModel.insertDeliveryDataItemToDatabase(soldProductList, invoiceId1)
        totalDiscountAmount = orderedInvoice!!.discount
        totalVolumeDiscountPercent = orderedInvoice!!.discountPercent
        var caOrCr = "CR"
        if (java.lang.Double.parseDouble(tvNetAmount.text.toString()) > 0) {
            if (java.lang.Double.parseDouble(tvNetAmount.text.toString()) == paidAmount) {
                caOrCr = "CA"
            }
        }
        val invoice1 = Invoice()
        invoice1.invoice_id = invoiceId1
        invoice1.customer_id = customer!!.id.toString()
        invoice1.sale_date = saleDate
        invoice1.total_amount = totalAmount.toString()
        invoice1.total_quantity = totalQuantity.toDouble()
        invoice1.total_discount_amount = totalDiscountAmount
        invoice1.pay_amount = paidAmount.toString()
        invoice1.receipt_person_name = receiptPerson.text.toString()
        invoice1.location_code = sale_man_id
        invoice1.sale_person_id = saleManId
        invoice1.device_id = Utils.getDeviceId(this@SaleOrderCheckoutActivity)
        invoice1.invoice_time = invoiceTime
        invoice1.invoice_status = caOrCr
        invoice1.total_discount_percent = totalVolumeDiscountPercent.toString()
        invoice1.rate = "1"
        invoice1.tax_amount = taxAmt
        invoice1.due_date = dueDate
        invoice = invoice1
        deliveryCheckoutViewModel.saveInvoiceData(invoice1)

        val deliveryApi = DeliveryApi()
        deliveryApi.invoiceNo = invoiceId1
        deliveryApi.invoiceDate = saleDate
        deliveryApi.saleId = invoiceId1
        deliveryApi.remark = ""
        insertDeliveryUpload(deliveryApi)
    }

    private fun insertDeliveryUpload(deliveryApi:DeliveryApi){
        val cvDeliveryUpload = DeliveryUpload()
        cvDeliveryUpload.invoice_no =  deliveryApi.invoiceNo
        cvDeliveryUpload.invoice_date = deliveryApi.invoiceDate
        cvDeliveryUpload.customer_id = customer!!.id
        cvDeliveryUpload.sale_id =  deliveryApi.saleId
        cvDeliveryUpload.remark =  deliveryApi.remark
        deliveryCheckoutViewModel.saveDeliveryUpload(cvDeliveryUpload)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == Constant.RQC_BACK_TO_CUSTOMER)
            if (resultCode == Activity.RESULT_OK) {
                setResult(Activity.RESULT_OK)
                finish()
            }
    }

}
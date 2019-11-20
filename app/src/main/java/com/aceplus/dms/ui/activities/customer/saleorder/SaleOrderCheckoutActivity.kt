package com.aceplus.dms.ui.activities.customer.saleorder

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.content.ContentValues
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
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
import com.aceplus.domain.entity.invoice.Invoice
import com.aceplus.domain.entity.promotion.Promotion
import com.aceplus.domain.model.delivery.Deliver
import com.aceplus.domain.repo.CustomerVisitRepo
import com.aceplus.domain.repo.deliveryrepo.DeliveryRepo
import com.aceplus.domain.vo.SoldProductInfo
import com.aceplussolutions.rms.constants.AppUtils
import com.aceplussolutions.rms.ui.activities.BaseActivity
import kotlinx.android.synthetic.main.activity_sale_checkout.*
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


        private const val RC_REQUEST_SEND_SMS = 101

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

        fun getIntentForDeliveryFromSaleOrder(
            saleOrderActivity: SaleOrderActivity,
            isDelivery:Boolean,
            soldProductList: java.util.ArrayList<SoldProductInfo>,
            orderedInvoice:Deliver
        ): Intent? {
            val intent = Intent(saleOrderActivity, SaleOrderCheckoutActivity::class.java)
            intent.putExtra(IE_SOLD_PRODUCT_LIST,soldProductList)
            intent.putExtra(ORDERED_INVOICE_KEY,orderedInvoice)
            intent.putExtra(IE_IS_DELIVERY,isDelivery)
            return intent
        }

    }
    private val deliveryRepo: DeliveryRepo? = null
    private val saleCheckoutViewModel: SaleCheckoutViewModel by viewModel()
    private val deliveryCheckoutViewModel: DeliveryViewModel by viewModel()
    private val orderCheckoutListAdapter: OrderCheckoutListAdapter by lazy { OrderCheckoutListAdapter() }

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
    private var salePersonId: String? = null
    private var locationCode: Int = 0
    private var invoiceId: String = ""
    private var invoice: Invoice? = null
    private var saleManId = ""
    private var saleManNo = ""
    private var saleManPwd = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            saleManId = AppUtils.getStringFromShp(Constant.SALEMAN_ID, this)!!
            saleManNo = AppUtils.getStringFromShp(Constant.SALEMAN_NO, this)!!
            saleManPwd = AppUtils.getStringFromShp(Constant.SALEMAN_PWD, this)!!
        } catch (e: NullPointerException) {
            e.printStackTrace()
            Utils.backToLogin(this)
        }


        getIntentData()
        initializeData()
        setupUI()
        catchEvents()

        orderCheckoutListAdapter.setNewList(soldProductList)

    }

    private fun setupUI(){
//        edtVolumeDiscountPercent.setText(orderedInvoice!!.discountPercent.toString())
//        edtVolumeDiscountAmt.setText(orderedInvoice!!.amount.toString())
//        payAmount.setText(orderedInvoice!!.paidAmount.toString())
        if (isDelivery){
            deliveryHeaderLayout.tvTitle.text = "DELIVERY CHECKOUT"
            tvInvoiceId.text = orderedInvoice!!.invoiceNo
            tvTotalAmount.text = orderedInvoice!!.amount.toString()
            btn_disOk.visibility = View.GONE
            btn_amtOk.visibility = View.GONE
            paymentMethodLayout.visibility = View.GONE
            refundLayout.visibility = View.GONE
            llSaleStatus.visibility = View.GONE
            duedateLayout.visibility = View.GONE
            payAmountLayout.visibility = View.VISIBLE
            checkout_delivery_date_layout.visibility = View.GONE
            checkout_remark_layout.visibility = View.GONE
            layout_receipt_person.visibility = View.VISIBLE
            payAmountLayout.visibility = View.VISIBLE
            tableHeaderUM.visibility = View.GONE
            advancedPaidAmountLayout.visibility = View.GONE
            volDisForPreOrderLayout.visibility = View.GONE

        }
        else{
            saleDateTextView.text = Utils.getDate(false)
            tableHeaderDiscount.text = "Promotion Price"
            tax_layout.visibility = View.VISIBLE
            checkout_remark_layout.visibility = View.VISIBLE
            tableHeaderQty.text = getString(R.string.ordered_qty)
            tableHeaderUM.visibility = View.GONE
            advancedPaidAmountLayout.visibility = View.GONE
            payAmountLayout.visibility = View.GONE
            refundLayout.visibility = View.GONE
            layout_receipt_person.visibility = View.GONE
            volDisForPreOrderLayout.visibility = View.GONE
            checkout_delivery_date_layout.visibility = View.VISIBLE
            duedateLayout.visibility = View.GONE
        }
//        if (isDelivery) {
//            advancedPaidAmount.text = Utils.formatAmount(orderedInvoice!!.paidAmount)
//        }

        rvSoldProductList.adapter = orderCheckoutListAdapter
        rvSoldProductList.layoutManager = LinearLayoutManager(this)

    }

    private fun initializeData(){

        calculateTotalAmount()
        saleCheckoutViewModel.calculateFinalAmount(soldProductList, totalAmount) // Check - should do only for pre-order
        salePersonId = saleCheckoutViewModel.getSaleManID()
        locationCode = saleCheckoutViewModel.getRouteID() // Check point

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

        saleCheckoutViewModel.invoice.observe(this, android.arch.lifecycle.Observer {
            if (it != null){
                invoice = it
                saleCheckoutViewModel.invoice.value = null

                if (Utils.isInternetAccess(this)){
                    Utils.callDialog("Please wait...", this)
                    saleCheckoutViewModel.getPreOrderRequest()
                }
                else
                    sendSMSMessage()
            }
        })

        saleCheckoutViewModel.messageInfo.observe(this, android.arch.lifecycle.Observer {
            if (it != null){
                sendSMS(it.first, it.second)
                insertSMSRecord(it.first, it.second)
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

            var paymentMethod = when(activity_sale_checkout_radio_group.checkedRadioButtonId){
                R.id.activity_sale_checkout_radio_bank -> "B"
                R.id.activity_sale_checkout_radio_cash -> "CA"
                else -> ""
            }

            val paidAmount = if (tvPrepaidAmount.text.isNotBlank()) tvPrepaidAmount.text.toString().toDouble() else 0.0

            if (validationInput(paymentMethod == "B", paidAmount)){

                if (!isDelivery){

                    if (paidAmount < netAmount){
                        // ToDo - check insufficient amount
                        setInvoiceId()
                        savePreOrderInformation("CR")
                    } else{
                        setInvoiceId()
                        savePreOrderInformation("CA")
                    }

                } else{
                    if (this@SaleOrderCheckoutActivity.receiptPerson.text.isEmpty()) {
                        AlertDialog.Builder(this@SaleOrderCheckoutActivity)
                            .setTitle("Delivery")
                            .setMessage("You must provide 'Receipt Person'.")
                            .setPositiveButton(
                                "OK"
                            ) { arg0, arg1 -> receiptPerson.requestFocus() }
                            .show()

                        return
                    }
                    insertDeliveryDataToDatabase(orderedInvoice!!)
                }

            }

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
        val invoiceID = saleCheckoutViewModel.getInvoiceNumber( salePersonId!!, locationCode, Constant.FOR_SALE)
        tvInvoiceId.text = invoiceID
        this.invoiceId = invoiceID

    }

    private fun savePreOrderInformation(cashOrLoanOrBank: String){

        saleCheckoutViewModel.updateDepartureTimeForSaleManRoute( salePersonId!!, customer!!.id.toString())
        saleCheckoutViewModel.updateSaleVisitRecord(customer!!.id)

        val customerId = customer!!.id
        val preOrderDate = Utils.getCurrentDate(true) // To check format
        val deliveryDate = checkout_delivery_date_chooser_text.text.toString() // To check format
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
            locationCode,
            volDisAmount,
            volDisPercent,
            taxAmt,
            checkout_remark_edit_text.text.toString(),
            edit_txt_branch_name.text.toString(),
            edit_txt_account_name.text.toString(),
            cashOrLoanOrBank,
            soldProductList,
            promotionList
        )

    }

    private fun uploadPreOrderToServer(){

        // ToDo

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
                    toPrintActivity()
                }

                alertDialog.dismiss()
            }

            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener {
                alertDialog.dismiss()
                toPrintActivity()
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

    private fun toPrintActivity(){

        val intent = PrintInvoiceActivity.newIntentFromSaleOrderCheckout(
            this,
            invoice!!,
            soldProductList,
            promotionList
        )

        startActivityForResult(intent, Utils.RQ_BACK_TO_CUSTOMER)

    }

    private fun insertDeliveryDataToDatabase(deliver:Deliver){
        val saleDate = Utils.getCurrentDate(true)
        var invoiceId = ""
        var salePersonId = ""
        val customerVisitRepo: CustomerVisitRepo? = null

        try {
            invoiceId = Utils.getInvoiceNo(
                AppUtils.getStringFromShp(Constant.SALEMAN_NO, this)!!,
                locationCode.toString(),
                Constant.FOR_DELIVERY,
                customerVisitRepo!!.getLastCountForInvoiceNumber(Constant.FOR_DELIVERY)
            )
            salePersonId = AppUtils.getStringFromShp(Constant.SALEMAN_ID, this)!!
        } catch (e: NullPointerException) {
            e.printStackTrace()
            Utils.backToLogin(this)
        }
        val paidAmount = if (tvPrepaidAmount.text.isNotEmpty())
            java.lang.Double.parseDouble(tvPrepaidAmount.text.toString().replace(",", ""))
        else
            0
        val dueDate = Utils.getCurrentDate(true)
        val invoiceTime = Utils.getCurrentDate(true)
        val totalQuantity = deliveryCheckoutViewModel.insertDeliveryDataItemToDatabase(soldProductList, invoiceId)
        totalDiscountAmount = orderedInvoice!!.discount
        totalVolumeDiscountPercent = orderedInvoice!!.discountPercent
        var caOrCr = "CR"
        if (java.lang.Double.parseDouble(tvNetAmount.text.toString()) > 0) {
            if (java.lang.Double.parseDouble(tvNetAmount.text.toString()) == paidAmount) {
                caOrCr = "CA"
            }
        }

        deliveryCheckoutViewModel.routeDataList.observe(this,android.arch.lifecycle.Observer {
            val sale_man_id = it!!.sale_man_id
            val invoice = Invoice()
            invoice.invoice_id = invoiceId
//            invoice.customer_id = customer!!.id.toString()
            invoice.sale_date = saleDate
            invoice.total_amount = totalAmount.toString()
            invoice.total_quantity = totalQuantity.toDouble()
            invoice.total_discount_amount = totalDiscountAmount
            invoice.pay_amount = paidAmount.toString()
            invoice.receipt_person_name = receiptPerson.text.toString()
            invoice.sale_person_id = salePersonId
            invoice.location_code = sale_man_id
            invoice.device_id = Utils.getDeviceId(this@SaleOrderCheckoutActivity)
            invoice.invoice_time = invoiceTime
            invoice.invoice_status = caOrCr
            invoice.total_discount_percent = totalVolumeDiscountPercent.toString()
            invoice.rate = "1"
            invoice.tax_amount = taxAmt
            invoice.due_date = dueDate
            deliveryRepo!!.saveInvoiceData(invoice)
        })

        deliveryCheckoutViewModel.loadRouteId(saleManId)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == Utils.RQ_BACK_TO_CUSTOMER)
            if (resultCode == Activity.RESULT_OK) {
                setResult(Activity.RESULT_OK)
                finish()
            }
    }

    override fun onBackPressed() {
//        val intent = Intent(this@SaleOrderCheckoutActivity, DeliveryActivity::class.java)
//        startActivity(intent)
        finish()
    }

}
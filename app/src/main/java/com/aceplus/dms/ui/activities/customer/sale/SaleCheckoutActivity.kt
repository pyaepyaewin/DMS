package com.aceplus.dms.ui.activities.customer.sale

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import com.aceplus.data.utils.Constant
import com.aceplus.dms.R
import com.aceplus.dms.ui.activities.PrintInvoiceActivity
import com.aceplus.dms.ui.adapters.sale.CheckoutSoldProductListAdapter
import com.aceplus.dms.utils.Utils
import com.aceplus.dms.viewmodel.customer.sale.SaleCheckoutViewModel
import com.aceplus.domain.entity.customer.Customer
import com.aceplus.domain.entity.invoice.Invoice
import com.aceplus.domain.entity.promotion.Promotion
import com.aceplus.domain.vo.SoldProductInfo
import com.aceplussolutions.rms.constants.AppUtils
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
        private const val IE_RETURN_AMOUNT = "IE_RETURN_AMOUNT"
        private const val IE_SALE_RETURN_INVOICE_ID_KEY = "SALE_RETURN_INVOICE_ID_KEY"

        fun newIntentFromSale(context: Context, customerData: Customer, soldProductList: ArrayList<SoldProductInfo>, promotionList: ArrayList<Promotion>): Intent{
            val intent = Intent(context, SaleCheckoutActivity::class.java)
            intent.putExtra(IE_CUSTOMER_DATA, customerData)
            intent.putExtra(IE_SOLD_PRODUCT_LIST, soldProductList)
            intent.putExtra(IE_PROMOTION_LIST, promotionList)
            intent.putExtra(IE_SALE_EXCHANGE, false)
            return intent
        }

        fun newIntentFromSaleForSaleExchange(
            context: Context, customerData: Customer, soldProductList: ArrayList<SoldProductInfo>, promotionList: ArrayList<Promotion>, returnInvoiceNo: String, returnAmount: Double
        ): Intent{
            val intent = Intent(context, SaleCheckoutActivity::class.java)
            intent.putExtra(IE_CUSTOMER_DATA, customerData)
            intent.putExtra(IE_SOLD_PRODUCT_LIST, soldProductList)
            intent.putExtra(IE_PROMOTION_LIST, promotionList)
            intent.putExtra(IE_SALE_EXCHANGE, true)
            intent.putExtra(IE_SALE_RETURN_INVOICE_ID_KEY, returnInvoiceNo)
            intent.putExtra(IE_RETURN_AMOUNT, returnAmount)
            return intent
        }

    }

    private val saleCheckoutViewModel: SaleCheckoutViewModel by viewModel()
    private val checkoutSoldProductListAdapter: CheckoutSoldProductListAdapter by lazy { CheckoutSoldProductListAdapter() }

    private val df = DecimalFormat(".##")
    private var customer: Customer? = null
    private var soldProductList: ArrayList<SoldProductInfo> = ArrayList()
    private var promotionList: ArrayList<Promotion> = ArrayList()
    private var totalAmount: Double = 0.0
    private var netAmount: Double = 0.0
    private var refundAmount: Double = 0.0
    private var taxType: String = ""
    private var taxPercent: Int = 0
    private var invoiceId: String = ""
    private var taxAmt: Double = 0.0
    private var isSaleExchange: Boolean = false
    private var locationCode: Int = 0
    private var routeID: Int = 0
    private var salePersonId: String? = null
    private var invoice: Invoice? = null
    private var saleReturnAmount: Double = 0.0
    private var saleReturnInvoiceNo: String? = null
    private var selectedDueDate: String? = null

    private var totalVolumeDiscount: Double = 0.0           //Disc by date
    private var totalVolumeDiscountPercent: Double = 0.0    //Disc by date
    private var salesmanDisPercent: Double = 0.0            //Disc by salesman
    private var salesmanDisAmount:Double = 0.0              //Disc by salesman
    private var totalItemDiscountAmount: Double = 0.0       //Disc by amount
    private var totalItemDiscountPercent: Double = 0.0      //Disc by amount
    private var totalDiscountAmount:Double = 0.0            //Total of disc
    private var totalDiscountPercent:Double = 0.0           //Total of disc


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        getIntentData()
        setupUI()
        initializeData()
        catchEvents()

    }

    private fun initializeData(){

        calculateTotalAmount()
        saleCheckoutViewModel.calculateFinalAmount(soldProductList, totalAmount)
        salePersonId = saleCheckoutViewModel.getSaleManID()
        locationCode = saleCheckoutViewModel.getLocationCode() // Modified route id to location id - main thread
        routeID = saleCheckoutViewModel.getRouteID()

    }

    private fun setupUI(){

        saleDateTextView.text = Utils.getDate(false)

        advancedPaidAmountLayout.visibility = View.GONE
        totalInfoForPreOrder.visibility = View.GONE
        tax_layout.visibility = View.VISIBLE
        tableHeaderDiscount.text = "Promotion Price"

        saleExchangeLayout.visibility = if (isSaleExchange) View.VISIBLE else View.GONE
        payAmount.isEnabled = !isSaleExchange

        rvSoldProductList.adapter = checkoutSoldProductListAdapter
        rvSoldProductList.layoutManager = LinearLayoutManager(this)

        checkoutSoldProductListAdapter.setNewList(soldProductList)
        setPromotionProductList()

        if (isSaleExchange) tvSaleReturnAmount.text = Utils.formatAmount(saleReturnAmount)

    }

    private fun catchEvents(){

        back_img.setOnClickListener { onBackPressed() }
        btn_disOk.setOnClickListener { calculateDiscPercentToAmt() }
        btn_amtOk.setOnClickListener { calculateDiscAmtToPercent() }
        edt_dueDate.setOnClickListener { chooseDueDate() }

        confirmAndPrint_img.setOnClickListener {
            Utils.askConfirmationDialog("Save", "Do you want to confirm?", "save", this, this::onClickSaveButton)
        }

        payAmount.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(p0: Editable?) { "Nothing to do" }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { "Nothing to do" }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                calculateRefundAmount()
            }
        })

        activity_sale_checkout_radio_bank.setOnCheckedChangeListener { button, isChecked ->
            bank_branch_layout.visibility = if (isChecked) View.VISIBLE else View.GONE
            bank_account_layout.visibility = if (isChecked) View.VISIBLE else View.GONE
        }

        saleCheckoutViewModel.invoice.observe(this, android.arch.lifecycle.Observer {
            if (it != null){
                invoice = it
                saleOrExchange()
                saleCheckoutViewModel.invoice.value = null
            }
        })

        saleCheckoutViewModel.finalData.observe(this, android.arch.lifecycle.Observer {
            if (it != null){
                totalVolumeDiscount = it.totalVolumeDiscount
                totalVolumeDiscountPercent = it.totalVolumeDiscountPercent
                taxType = it.taxType
                taxPercent = it.taxPercent

                totalItemDiscountAmount = it.amountAndPercentage["Amount"] ?: 0.0
                totalItemDiscountPercent = it.amountAndPercentage["Percentage"] ?: 0.0
                displayFinalAmount()

            }
        })

    }

    private fun getIntentData(){

        isSaleExchange = intent.getBooleanExtra(IE_SALE_EXCHANGE, false)
        customer = intent.getParcelableExtra(IE_CUSTOMER_DATA)
        soldProductList = intent.getParcelableArrayListExtra(IE_SOLD_PRODUCT_LIST)
        promotionList = intent.getParcelableArrayListExtra(IE_PROMOTION_LIST)

        if (isSaleExchange){
            saleReturnAmount = intent.getDoubleExtra(IE_RETURN_AMOUNT, 0.0)
            saleReturnInvoiceNo = intent.getStringExtra(IE_SALE_RETURN_INVOICE_ID_KEY)
        }

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

        /*Filling discount in editText - can use again after fixing discount (Don't delete)
        if (salesmanDisPercent != 0.0) edtVolumeDiscountPercent.setText(salesmanDisPercent.toString())
        else edtVolumeDiscountPercent.setText(totalVolumeDiscountPercent.toString())

        if (salesmanDisAmount != 0.0) edtVolumeDiscountAmt.setText(salesmanDisAmount.toString())
        else edtVolumeDiscountAmt.setText(totalDiscountAmount.toString())*/

        tvNetAmount.text = Utils.formatAmount(netAmount)
        tax_txtView.text = df.format(taxAmt)

        if (isSaleExchange) calculateSaleExchangeData()

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
        tvNetAmount.text = Utils.formatAmount(total)
    }

    private fun calculateDiscPercentToAmt(){

        if (edtVolumeDiscountPercent.text.toString().isNotBlank() && edtVolumeDiscountPercent.text.toString() != "."){
            val discountPercent = edtVolumeDiscountPercent.text.toString().toDouble()
            var discountAmount = totalAmount * discountPercent / 100

            discountAmount = df.format(discountAmount).toDouble()
            edtVolumeDiscountAmt.setText(discountAmount.toString())

            //var netAmount = netAmount - discountAmount
            //netAmount = df.format(netAmount).toDouble()
            //tvNetAmount.text = netAmount.toString()
            //this.netAmount = netAmount

            this.salesmanDisAmount = discountAmount
            this.salesmanDisPercent = discountPercent

            displayFinalAmount()

            if (!isSaleExchange) calculateRefundAmount()
        }

    }

    private fun calculateDiscAmtToPercent(){

        if (edtVolumeDiscountAmt.text.toString().isNotBlank() && edtVolumeDiscountAmt.text.toString() != "."){
            val discountAmount = edtVolumeDiscountAmt.text.toString().toDouble()
            var discountPercent = 100 * (discountAmount / totalAmount)

            discountPercent = df.format(discountPercent).toDouble()
            edtVolumeDiscountPercent.setText(discountPercent.toString())

            //var netAmount = netAmount - discountAmount
            //netAmount = df.format(netAmount).toDouble()
            //tvNetAmount.text = netAmount.toString()
            //this.netAmount = netAmount

            this.salesmanDisAmount = discountAmount
            this.salesmanDisPercent = discountPercent

            displayFinalAmount()

            if (!isSaleExchange) calculateRefundAmount()
        }

    }

    private fun calculateRefundAmount(){

        if (payAmount.text.isNotBlank()){
            val payAmount = payAmount.text.toString().toDouble()
            var refundAmount = payAmount - netAmount

            if (refundAmount >= 0){
                refundAmount = df.format(refundAmount).toDouble()
                this.refundAmount = refundAmount
            }
            else this.refundAmount = 0.0

            refund.text = Utils.formatAmount(this.refundAmount)
        }

    }

    private fun setInvoiceId(){

        val invoiceCount = AppUtils.getIntFromShp(Constant.INVOICE_COUNT, this) ?: 0

        if (invoiceCount >= 0) AppUtils.saveIntToShp(Constant.INVOICE_COUNT, invoiceCount + 1, this)

        invoiceId = if (isSaleExchange)
            saleCheckoutViewModel.getInvoiceNumber( salePersonId!!, locationCode, Constant.FOR_SALE_EXCHANGE)
        else
            saleCheckoutViewModel.getInvoiceNumber( salePersonId!!, locationCode, Constant.FOR_SALE)

        tvInvoiceId.text = invoiceId

    }

    private fun calculateTax(): Double{

        var taxAmt = 0.0
        if (taxPercent != 0)
            taxAmt = (totalAmount - totalDiscountAmount) / 21

        this.taxAmt = taxAmt
        return taxAmt

    }

    private fun chooseDueDate(){

        val myCalendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyy/MM/dd")
        val sdf2 = SimpleDateFormat("yyyy-MM-dd")

        val dateDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            myCalendar.set(year, month, dayOfMonth)
            edt_dueDate.setText(sdf.format(myCalendar.time))
            selectedDueDate = sdf2.format(myCalendar.time)
        }, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH))

        dateDialog.show()

    }

    private fun calculateSaleExchangeData(){

        //var saleExchangeAmount: Double = 0.0
        //To check -> saleExchangeAmount or netAmount
        //val totalItemDiscountAmount = 0.0 //To Check - for what?
        //saleExchangeAmount = totalAmount - totalItemDiscountAmount - totalVolumeDiscount //should be (netAmt)

        if (netAmount > saleReturnAmount){

            val payAmountFromCustomer = netAmount - saleReturnAmount
            tvPayAmountFromCustomer.text = Utils.formatAmount(payAmountFromCustomer)
            tvRefundToCustomer.text = "0"

        } else{

            val refundAmountToCustomer = saleReturnAmount - netAmount
            tvRefundToCustomer.text = Utils.formatAmount(refundAmountToCustomer)
            tvPayAmountFromCustomer.text = "0"

        }

        payAmount.setText("${netAmount - saleReturnAmount}")

    }

    private fun onClickSaveButton(type: String){

        if (type == "save"){

            var paymentMethod = when(activity_sale_checkout_radio_group.checkedRadioButtonId){
                R.id.activity_sale_checkout_radio_bank -> "B"
                R.id.activity_sale_checkout_radio_cash -> "CA"
                else -> ""
            }

            if (validationInput(paymentMethod == "B")){

                val payAmt = if (payAmount.text.isNotBlank()) payAmount.text.toString().toDouble() else 0.0

                if (payAmt < netAmount){
                    if (paymentMethod == "B" && !isSaleExchange){
                        Utils.commonDialog("Insufficient Pay Amount!", this, 1)
                        return
                    }
                    setInvoiceId()
                    saveData("CR")
                } else{
                    setInvoiceId()
                    saveData("CA")
                }

            }

        }

    }

    private fun validationInput(withBankInfo: Boolean): Boolean{

        var dateAndPayment = false
        var name = false
        var bank = false
        var acc = false
        receiptPerson.error = null
        edit_txt_branch_name.error = null
        edit_txt_account_name.error = null

        if (edt_dueDate.text.isNotBlank() || payAmount.text.isNotBlank()) dateAndPayment = true
        else
            AlertDialog.Builder(this)
                .setTitle("Alert!")
                .setMessage("You must specify due date.")
                .setPositiveButton("OK", null)
                .show()

        if (receiptPerson.text.isNotBlank()) name = true
        else receiptPerson.error = "Please enter receipt person"

        return if (withBankInfo){
            if (edit_txt_branch_name.text.isNotBlank()) bank = true
            else edit_txt_branch_name.error = "Please enter bank name"

            if (edit_txt_account_name.text.isNotBlank()) acc = true
            else edit_txt_account_name.error = "Please enter bank account"

            dateAndPayment && name && bank && acc
        } else{
            dateAndPayment && name
        }

    }

    private fun saveData(cashOrLoanOrBank: String){

        val customerId = customer!!.id
        val saleDate = Utils.getCurrentDate(true)
        val payAmt = if (payAmount.text.isNotBlank()) payAmount.text.toString().toDouble() else 0.0
        val receiptPerson = receiptPerson.text.toString()
        val invoiceTime = Utils.getCurrentDate(true)
        val deviceId = Utils.getDeviceId(this)
        val bankBranchName = edit_txt_branch_name.text.toString()
        val bankAccountName = edit_txt_account_name.text.toString()
        var dueDate = saleDate

        if (selectedDueDate != null) dueDate = selectedDueDate!!

        saleCheckoutViewModel.saveCheckoutData(
            customerId,
            saleDate,
            invoiceId,
            payAmt,
            refundAmount,
            receiptPerson,
            salePersonId!!,
            invoiceTime,
            dueDate,
            deviceId,
            cashOrLoanOrBank,
            soldProductList,
            promotionList,
            totalAmount,
            taxAmt,
            bankBranchName,
            bankAccountName,
            totalDiscountAmount,
            totalDiscountPercent,
            saleReturnInvoiceNo,
            routeID
        )

    }

    private fun saleOrExchange(){

        if (isSaleExchange){
            val intent = SaleExchangeInfoActivity.getIntentFromSaleCheckout(this, customer!!, saleReturnInvoiceNo!!, invoice!!, soldProductList, promotionList)
            startActivityForResult(intent, Constant.RQC_BACK_TO_CUSTOMER)
        } else{
            saleCheckoutViewModel.updateDepartureTimeForSaleManRoute( salePersonId!!, customer!!.id.toString())
            saleCheckoutViewModel.updateSaleVisitRecord(customer!!.id)
            val intent = PrintInvoiceActivity.newIntentFromSaleCheckout(this, invoice!!, soldProductList, promotionList)
            startActivityForResult(intent, Constant.RQC_BACK_TO_CUSTOMER)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == Constant.RQC_BACK_TO_CUSTOMER)
            if (resultCode == Activity.RESULT_OK){
                setResult(Activity.RESULT_OK)
                finish()
            }
    }

}

package com.aceplus.dms.ui.activities.customer

import android.app.Activity
import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import com.aceplus.data.utils.Constant
import com.aceplus.dms.R
import com.aceplus.dms.ui.activities.customer.sale.SaleActivity
import com.aceplus.dms.ui.activities.customer.sale.SaleReturnActivity
import com.aceplus.dms.ui.activities.customer.saleorder.SaleOrderActivity
import com.aceplus.dms.ui.adapters.CustomerListAdapter
import com.aceplus.dms.utils.Utils
import com.aceplus.dms.viewmodel.customer.CustomerViewModel
import com.aceplus.domain.entity.customer.Customer
import com.aceplus.domain.entity.customer.CustomerFeedback
import com.aceplus.shared.utils.GPSTracker
import com.aceplussolutions.rms.constants.AppUtils
import com.aceplussolutions.rms.ui.activities.BaseActivity
import kotlinx.android.synthetic.main.activity_customer.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import java.text.DecimalFormat
import kotlin.collections.ArrayList

class CustomerActivity : BaseActivity(), KodeinAware {
    override val kodein: Kodein by kodein()

    override val layoutId: Int
        get() = R.layout.activity_customer

    companion object {

        private const val IE_SALE_EXCHANGE = "IE_SALE_EXCHANGE"
        private const val IE_CUSTOMER_DATA = "IE_CUSTOMER_DATA"

        fun newIntent(context: Context): Intent {
            return Intent(context, CustomerActivity::class.java)
        }

        fun newIntentForSaleExchange(context: Context, isSaleExchange: Boolean): Intent {
            val intent = Intent(context, CustomerActivity::class.java)
            intent.putExtra(IE_SALE_EXCHANGE, isSaleExchange)
            return intent
        }

    }

    private val customerViewModel: CustomerViewModel by viewModel()
    private val mCustomerListAdapter by lazy { CustomerListAdapter(::onClickCustomerListItem) }

    private var isSaleExchange: Boolean = false
    private var selectedCustomer: Customer? = null
    private var allCustomerDataList: ArrayList<Customer> = ArrayList()
    private val gspTracker by lazy{ GPSTracker(applicationContext) }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN) // Hide keyboard on startup.

        setupUI()
        catchEvents()

    }

    override fun onResume() {
        super.onResume()
        customerViewModel.loadCustomer()
    }

    private fun setupUI() {

        isSaleExchange = intent.getBooleanExtra(IE_SALE_EXCHANGE, false)

        rvCustomer.layoutManager = GridLayoutManager(applicationContext, 1)
        rvCustomer.adapter = mCustomerListAdapter

        if (isSaleExchange)
            llCustomerButtonGp.visibility = View.GONE
        else
            btnOk.visibility = View.GONE

        btnPosm.visibility = View.GONE

    }

    private fun didCustomerSelected(): Boolean {
        if (selectedCustomer == null) {
            AlertDialog.Builder(this)
                .setTitle("Customer is required")
                .setMessage("You need to select customer.")
                .setPositiveButton("OK", null)
                .show()
            return false
        }
        return true
    }

    private fun onClickCustomerListItem(customer: Customer) {

        this.selectedCustomer = customer

        tvCustomerNameCA.text = customer.customer_name
        tvTownship.text = customer.township_number
        tvCreditTerms.text = customer.credit_term
        tvCreditLimit.text = customer.credit_limit
        tvCreditAmount.text = if (customer.credit_amount.isNullOrBlank()) "0.0" else customer.credit_amount
        tvDueAmount.text = if (customer.due_amount.isNullOrBlank()) "0.0" else customer.credit_amount
        tvPrepaidAmount.text = if (customer.prepaid_amount.isNullOrBlank()) "0.0" else customer.credit_amount
        tvPaymentType.text = customer.payment_type
        val latitude = Utils.onDecimalFormat(customer.latitude?.toDouble() ?: 0.0)
        val longitude = Utils.onDecimalFormat(customer.longitude?.toDouble() ?: 0.0)
        val nf = DecimalFormat("#.##########")
        tvLatitude.text = nf.format(latitude)
        tvLongitude.text = nf.format(longitude)
        tvCustomerRemark.text = customer.fax
        btnLocation.isEnabled = customer.flag?.toInt() ?: 0 != 1

        tvPhone.apply {
            paintFlags = Paint.UNDERLINE_TEXT_FLAG
            text = customer.phone?.trim()
        }
        tvAddress.apply {
            paintFlags = Paint.UNDERLINE_TEXT_FLAG
            text = customer.address
        }

    }

    private fun catchEvents() {

        customerViewModel.customerDataList.observe(this, Observer {
            mCustomerListAdapter.setNewList(it as ArrayList<Customer>)
            allCustomerDataList = it
        })

        edtSearch.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(characterSequence: CharSequence, arg1: Int, arg2: Int, arg3: Int) {
                val newCustomerList = mutableListOf<Customer>()
                for (customer in allCustomerDataList) {
                    if (customer.customer_name!!.toLowerCase().contains(characterSequence.toString().toLowerCase())) {
                        newCustomerList.add(customer)
                    }
                }
                mCustomerListAdapter.setNewList(newCustomerList as ArrayList<Customer>)
            }
            override fun beforeTextChanged(arg0: CharSequence, arg1: Int, arg2: Int, arg3: Int) { "Nothing To Do" }
            override fun afterTextChanged(arg0: Editable) { "Nothing To Do" }

        })

        customerViewModel.dialogStatus.observe(this, Observer {
            if (it != null){
                Utils.commonDialog(it, this, 2)
                customerViewModel.dialogStatus.value = null
            }
        })

        ivCancel.setOnClickListener { onBackPressed() }
        tvAddress.setOnClickListener { onClickAddress() }
        tvPhone.setOnClickListener { onClickPhoneNo() }
        btnSale.setOnClickListener { onClickSaleButton() }
        btnSaleOrder.setOnClickListener { onClickSaleOrderButton() }
        btnUnsellReason.setOnClickListener { onClickUnSellReasonButton() }
        btnSaleReturn.setOnClickListener { onClickSaleReturnButton() }
        btnLocation.setOnClickListener { onClickBtnLocation() }
        btnOk.setOnClickListener { onClickOkButton() }

    }

    private fun onClickSaleButton() {

        if (didCustomerSelected()) {
            customerViewModel.insertDataForTempSaleManRouteAndSaleVisitRecord(selectedCustomer!!, Utils.getCurrentDate(true), gspTracker)
            val intent = SaleActivity.newIntentFromCustomer(applicationContext, selectedCustomer!!)
            startActivity(intent)
        }

    }

    private fun onClickSaleOrderButton() {

        if (didCustomerSelected()) {
            customerViewModel.insertDataForTempSaleManRouteAndSaleVisitRecord(selectedCustomer!!, Utils.getCurrentDate(true), gspTracker)
            val intent = SaleOrderActivity.newIntentFromCustomer(applicationContext, selectedCustomer!!)
            startActivity(intent)
        }

    }

    private fun onClickUnSellReasonButton() {

        if (didCustomerSelected()) {

            if (selectedCustomer!!.flag == "1"){
                AlertDialog.Builder(this)
                    .setTitle("No Authority")
                    .setMessage("You need to select old customer.")
                    .setPositiveButton("OK", null)
                    .show()
                return
            } //Do it first for old customer check

            customerViewModel.insertDataForTempSaleManRouteAndSaleVisitRecord(selectedCustomer!!, Utils.getCurrentDate(true), gspTracker)

            customerViewModel.loadDidCustomerFeedback(selectedCustomer!!,
                {
                    AlertDialog.Builder(this@CustomerActivity)
                        .setTitle("General Sale")
                        .setMessage("This customer already have customer feedback report. Please check!")
                        .setPositiveButton("OK", null)
                        .show()
                },
                { customerFeedbackList: List<CustomerFeedback>, makeFeedbackAction: (deviceId: String, descriptionPosition: Int, remark: String, gpsTracker: GPSTracker) -> Unit ->

                    val layoutInflater = this@CustomerActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                    val view = layoutInflater.inflate(R.layout.dialog_box_customer_feedback, null)

                    val descriptionsSpinner = view.findViewById(R.id.description) as Spinner
                    val remarkEditText = view.findViewById(R.id.remark) as EditText

                    if (customerFeedbackList.isEmpty()) {
                        AlertDialog.Builder(this@CustomerActivity)
                            .setTitle("No Feedback")
                            .setMessage("You need to download feedback data.")
                            .setPositiveButton("OK", null)
                            .show()
                        return@loadDidCustomerFeedback
                    }

                    val alertDialog = AlertDialog.Builder(this@CustomerActivity)
                        .setView(view)
                        .setTitle("Un-Sell Reason")
                        .setPositiveButton("OK") { arg0, arg1 ->
                            val deviceId = Utils.getDeviceId(this@CustomerActivity)
                            makeFeedbackAction(
                                deviceId,
                                descriptionsSpinner.selectedItemPosition,
                                remarkEditText.text.toString(),
                                gspTracker
                            )
                        }
                        .setNegativeButton("Cancel", null)
                        .create()

                    alertDialog.setOnShowListener {
                        val descriptions = ArrayList<String>()
                        for (customerFeedback in customerFeedbackList) {
                            descriptions.add(customerFeedback.remark.toString())
                        }
                        val descriptionsArrayAdapter =
                            ArrayAdapter(
                                this@CustomerActivity,
                                android.R.layout.simple_spinner_item,
                                descriptions
                            )
                        descriptionsArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        descriptionsSpinner.adapter = descriptionsArrayAdapter
                    }
                    alertDialog.show()
                })
        }

    }

    private fun onClickSaleReturnButton() {

        if (didCustomerSelected()) {
            customerViewModel.insertDataForTempSaleManRouteAndSaleVisitRecord(selectedCustomer!!, Utils.getCurrentDate(true), gspTracker)
            val intent = SaleReturnActivity.newIntentFromCustomer(applicationContext, false, selectedCustomer!!)
            startActivity(intent)
        }

    }

    private fun onClickPosmButton() {

        if (didCustomerSelected()) {
            customerViewModel.insertDataForTempSaleManRouteAndSaleVisitRecord(selectedCustomer!!, Utils.getCurrentDate(true), gspTracker)
            val intent = PosmActivity.newIntentFromCustomer(applicationContext, selectedCustomer!!)
            startActivity(intent)
        }

    }

    private fun onClickBtnLocation() {

        if (didCustomerSelected()) {
            customerViewModel.insertDataForTempSaleManRouteAndSaleVisitRecord(selectedCustomer!!, Utils.getCurrentDate(true), gspTracker)
            val intent = AddNewCustomerLocationActivity.newIntentFromCustomerActivity(
                applicationContext,
                salesmanId = AppUtils.getStringFromShp(Constant.SALEMAN_ID, applicationContext) ?: "",
                customer = selectedCustomer!!
            )
            startActivityForResult(intent, Constant.RQC_GET_LOCATION)
        }

    }

    private fun onClickAddress(){

        if (didCustomerSelected()) {
            val intent = CustomerLocationActivity.newIntentFromCustomer(this, selectedCustomer!!)
            startActivity(intent)
        }

    }

    private fun onClickPhoneNo(){

        if (didCustomerSelected()) {
            val phoneNo = tvPhone.text.toString()
            startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNo")))
        }

    }

    private fun onClickOkButton(){

        if (didCustomerSelected()) {
            customerViewModel.insertDataForTempSaleManRouteAndSaleVisitRecord(selectedCustomer!!, Utils.getCurrentDate(true), gspTracker)
            val intent = SaleReturnActivity.newIntentFromCustomer(applicationContext, true, selectedCustomer!!)
            startActivity(intent)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Constant.RQC_GET_LOCATION){
            if (resultCode == Activity.RESULT_OK){
                if (data != null){
                    val customer = data.getParcelableExtra<Customer>(IE_CUSTOMER_DATA)
                    selectedCustomer = customer
                    onClickCustomerListItem(customer)
                    customerViewModel.saveOrUpdateSaleManRoute(customer, gspTracker)
                }
            }
        }

    }

}

package com.aceplus.dms.ui.activities.customer

import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import com.aceplus.data.utils.Constant
import com.aceplus.dms.R
import com.aceplus.dms.R.drawable.customer
import com.aceplus.dms.ui.activities.LoginActivity
import com.aceplus.dms.ui.activities.customer.sale.SaleActivity
import com.aceplus.dms.ui.activities.customer.sale.SaleReturnActivity
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
import java.text.SimpleDateFormat
import java.util.*

class CustomerActivity : BaseActivity(), KodeinAware {
    override val kodein: Kodein by kodein()

    override val layoutId: Int
        get() = R.layout.activity_customer

    companion object {
        private const val IE_SALE_EXCHANGE = "IE_SALE_EXCHANGE"
        private const val IE_CUSTOMER_ID = "IE_CUSTOMER_ID"
        fun newIntent(context: Context): Intent {
            return Intent(context, CustomerActivity::class.java)
        }

        fun newIntentForSaleExchange(context: Context, isSaleExchange: String): Intent {
            val intent = Intent(context, CustomerActivity::class.java)
            intent.putExtra(IE_SALE_EXCHANGE, isSaleExchange)
            return intent
        }

        fun newIntentFromSaleExchange(context: Context, isSaleExchange: String, customerId: String): Intent {
            val intent = Intent(context, CustomerActivity::class.java)
            intent.putExtra(IE_SALE_EXCHANGE, isSaleExchange)
            intent.putExtra(IE_CUSTOMER_ID, customerId)
            return intent
        }
    }

    private val customerViewModel: CustomerViewModel by viewModel()
    private val mCustomerListAdapter by lazy {
        CustomerListAdapter(::onClickCustomerListItem)
    }
    private var selectedCustomer: Customer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Hide keyboard on startup.
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        setupUI()
        catchEvents()

        customerViewModel.customerDataList.observe(this, Observer { it ->
            it?.let { mCustomerListAdapter.setNewList(it as ArrayList<Customer>) }
                ?: Utils.commonDialog("No issued product", this, 2)
        })

        customerViewModel.loadCustomer()
    }

    private fun setupUI() {
        rvCustomer.layoutManager = GridLayoutManager(applicationContext, 1)
        rvCustomer.adapter = mCustomerListAdapter

        if (intent != null) {
            val check = intent.getStringExtra(IE_SALE_EXCHANGE)
            val customer_Id = intent.extras!!.getString("CUSTOMER_ID")

            if (check.equals("yes", ignoreCase = true)) {
                llCustomerButtonGp.visibility = View.GONE
            } else {
                btnOk.visibility = View.GONE
            }
        }
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
        tvCustomerName.text = customer.customer_name
        tvPhone.text = customer.phone
        tvAddress.text = customer.address
        tvTownship.text = customer.township
        tvCreditTerms.text = customer.credit_term
        tvCreditLimit.text = customer.credit_limit
        tvCreditAmount.text = customer.credit_amount
        tvDueAmount.text = customer.due_amount
        tvPrepaidAmount.text = customer.prepaid_amount
        tvPaymentType.text = customer.payment_type
        val latitude = Utils.onDecimalFormat(customer.latitude?.toDouble() ?: 0.0)
        val longitude = Utils.onDecimalFormat(customer.longitude?.toDouble() ?: 0.0)
        val nf = DecimalFormat("#.##########")
        tvLatitude.text = nf.format(latitude)
        tvLongitude.text = nf.format(longitude)
        tvCustomerRemark.text = customer.fax
        btnLocation.isEnabled = customer.flag?.toInt() ?: 0 != 1
    }

    private fun catchEvents() {
        edtSearch.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(characterSequence: CharSequence, arg1: Int, arg2: Int, arg3: Int) {

                val customerList = mCustomerListAdapter.getDataList()
                val newCustomerList = mutableListOf<Customer>()
                mCustomerListAdapter.setNewList(ArrayList())

                for (customer in customerList) {
                    if (customer.customer_name!!.toLowerCase()
                            .contains(characterSequence.toString().toLowerCase())
                    ) {
                        newCustomerList.add(customer)
                        mCustomerListAdapter.setNewList(newCustomerList as ArrayList<Customer>)
                    }
                }
            }

            override fun beforeTextChanged(
                arg0: CharSequence, arg1: Int, arg2: Int,
                arg3: Int
            ) {
            }

            override fun afterTextChanged(arg0: Editable) {
                mCustomerListAdapter.notifyDataSetChanged()
            }
        })
        tvAddress.setOnClickListener {
            if (didCustomerSelected()) {
                val intent = CustomerLocationActivity.newIntent(
                    applicationContext,
                    latitude = tvLatitude.text.toString(),
                    longitude = tvLongitude.text.toString(),
                    customerName = tvCustomerName.text.toString(),
                    address = tvAddress.text.toString(),
                    visitRecord = this.selectedCustomer!!.visit_record.toString()
                )
                startActivity(intent)
            }
        }
        tvPhone.setOnClickListener {
            if (didCustomerSelected()) {
                val phoneNo = tvPhone.text.toString()
                startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNo")))
            }
        }
        ivCancel.setOnClickListener { onBackPressed() }
        btnOk.setOnClickListener {
            if (didCustomerSelected()) {
                val intent = SaleReturnActivity.newIntentFromCustomer(applicationContext, "yes", selectedCustomer!!)
                startActivity(intent)
            }
        }

        btnSale.setOnClickListener { onClickSaleButton() }
        btnSaleOrder.setOnClickListener { onClickSaleOrderButton() }
        btnUnsellReason.setOnClickListener { onClickUnSellReasonButton() }
        btnSaleReturn.setOnClickListener { onClickSaleReturnButton() }
        btnPosm.setOnClickListener {
            onClickPosmButton()
        }
        btnLocation.setOnClickListener { onClickBtnLocation() }
    }

    private fun onClickSaleButton() {
        if (didCustomerSelected()) {
            val intent = SaleActivity.newIntentFromCustomer(applicationContext, "no", selectedCustomer!!)
            startActivity(intent)
        }
    }

    private fun onClickSaleOrderButton() {
        if (didCustomerSelected()) {
            val intent = SaleOrderActivity.newIntentFromCustomer(applicationContext, true, selectedCustomer!!)
            startActivity(intent)
        }
    }

    private fun onClickUnSellReasonButton() {
        if (didCustomerSelected()) {
            customerViewModel.loadDidCustomerFeedback(
                selectedCustomer!!,
                {
                    AlertDialog.Builder(this@CustomerActivity)
                        .setTitle("General sale")
                        .setMessage("This customer already have customer feedback report. Please check!")
                        .setPositiveButton("OK", null)
                        .show()
                },
                { customerFeedbackList: List<CustomerFeedback>, makeFeedbackAction: (deviceId: String, descriptionPosition: Int, remark: String, gpsTracker: GPSTracker) -> Unit ->
                    val layoutInflater =
                        this@CustomerActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                    val view = layoutInflater.inflate(R.layout.dialog_box_customer_feedback, null)

                    val descriptionsSpinner = view.findViewById(R.id.description) as Spinner
                    val remarkEditText = view.findViewById(R.id.remark) as EditText

                    if (customerFeedbackList.isEmpty()) {
                        AlertDialog.Builder(this@CustomerActivity)
                            .setTitle("No Feedbacks")
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
                                GPSTracker(applicationContext)
                            )
                        }
                        .setNegativeButton("Cancel", null)
                        .create()

                    alertDialog.setOnShowListener { _ ->
                        val descriptions = ArrayList<String>()
                        for (customerFeedback in customerFeedbackList) {
                            descriptions.add(customerFeedback.remark.toString())
                        }
                        val descriptionsArrayAdapter =
                            ArrayAdapter(this@CustomerActivity, android.R.layout.simple_spinner_item, descriptions)
                        descriptionsArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        descriptionsSpinner.adapter = descriptionsArrayAdapter
                    }
                    alertDialog.show()
                })
        }
    }

    private fun onClickSaleReturnButton() {
        if (didCustomerSelected()) {
            val intent = SaleReturnActivity.newIntentFromCustomer(applicationContext, "no", selectedCustomer!!)
            startActivity(intent)
        }
    }

    private fun onClickPosmButton() {
        if (didCustomerSelected()) {
            val intent = PosmActivity.newIntentFromCustomer(applicationContext, selectedCustomer!!)
            startActivity(intent)
        }
    }

    private fun onClickBtnLocation() {
        if (didCustomerSelected()) {
            val intent = AddNewCustomerLocationActivity.newIntentFromCustomerActivity(
                applicationContext,
                salemanId = AppUtils.getStringFromShp(Constant.SALEMAN_ID, applicationContext) ?: "",
                customer = selectedCustomer!!
            )
//            intent.putExtra("customerName", customer.getCustomerName())
//            intent.putExtra("phoneNumber", customer.getPhone())
//            intent.putExtra("customerAddress", customer.getAddress())
//            intent.putExtra("contactPerson", customer.getContact_person())
//            intent.putExtra("zonePosition", "")
//            intent.putExtra("townshipPosition", customer.getTownship())
//            //intent.putExtra("customerCategoryPosition", String.valueOf(customerCategorySpinner.getSelectedItemPosition()));
//            intent.putExtra("customerLat", customer.getLatitude())
//            intent.putExtra("customerLng", customer.getLongitude())
//            intent.putExtra("customerId", customer.getCustomerId())
//            intent.putExtra("cusId", customer.getId())
//            intent.putExtra("saleman_id", saleManId)

//            intent.putExtra("From", "customerActivity")
            startActivity(intent)
        }
    }
}

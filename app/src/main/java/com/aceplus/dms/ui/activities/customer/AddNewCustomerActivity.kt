package com.aceplus.dms.ui.activities.customer

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioButton
import com.aceplus.data.utils.Constant
import com.aceplus.dms.R
import com.aceplus.dms.utils.Utils
import com.aceplus.dms.viewmodel.customer.prospectcustomer.ProspectCustomerViewModel
import com.aceplus.dms.viewmodel.report.ReportViewModel
import com.aceplus.domain.entity.ShopType
import com.aceplus.domain.entity.customer.Customer
import com.aceplus.domain.entity.predefine.District
import com.aceplus.domain.entity.predefine.StateDivision
import com.aceplus.domain.entity.predefine.Street
import com.aceplus.domain.entity.predefine.Township
import com.aceplus.domain.model.customer.prospectcustomer.NewCustomer
import com.aceplussolutions.rms.constants.AppUtils
import com.aceplussolutions.rms.ui.activities.BaseActivity
import kotlinx.android.synthetic.main.activity_add_new_customer.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import java.text.DecimalFormat

class AddNewCustomerActivity : BaseActivity(), KodeinAware {
    override val kodein: Kodein by kodein()
    override val layoutId: Int
        get() = R.layout.activity_add_new_customer

    var customerLat: Double? = null
    var customerLng: Double? = null
    var putCustomerName: String? = null
    var putCustomerPhone: String? = null
    var putCustomerAddress: String? = null
    var putContactPerson: String? = null
    var putZonePosition: String? = null
    var putCustomerCategoryPosition: String? = null
    var putTownshipPosition: String? = null
    var customerId: String? = null
    var paymentType: String = ""

    private var userId = ""
    private var townshipName = ""
    private var townshipId = 0
    private var streetId = 0
    private var districtId = 0
    private var stateDivisionId = 0
    private var shopTypeId = 0

    private var streetIndex: Int? = null
    private var townshipIndex: Int? = null
    private var shopTypeIndex: Int? = null
    private var distinctIndex: Int? = null
    private var stateDivisionIndex: Int? = null
    private var streetIndex1 = 0
    private var townshipIndex1 = 0
    private var shopTypeIndex1 = 0
    private var distinctIndex1 = 0
    private var stateDivisionIndex1 = 0

    private var townshipList: List<Township>? = null
    private var streetList: List<Street>? = null
    private var customerList: List<Customer>? = null
    private var districtList: List<District>? = null
    private var stateDivisionList: List<StateDivision>? = null
    private var shopTypeList: List<ShopType>? = null
    private val addNewCustomerActivityViewModel: ProspectCustomerViewModel by viewModel()
    private val reportViewModel: ReportViewModel by viewModel()

    companion object {
        private const val IE_NAME = "name"
        private const val IE_PERSON = "person"
        private const val IE_PHONE = "phone"
        private const val IE_ADDRESS = "address"
        private const val IE_LATIDUTE = "customerLat"
        private const val IE_LONGITUDE = "customerLan"
        private const val IE_PAYMENT_TYPE = "paymentType"
        private const val IE_STREET_ID = "sid"
        private const val IE_TOWNSHIP_ID = "tid"
        private const val IE_SHOP_TYPE_ID = "stId"
        private const val IE_DISTINCT_ID = "did"
        private const val IE_STATE_DIVISION_ID = "sdId"
        fun newIntent(context: Context): Intent {
            return Intent(context, AddNewCustomerActivity::class.java)
        }

        fun newIntentFromAddNewCustomerLocation(
            context: Context,
            customerName: String,
            contactPerson: String,
            phone: String,
            address: String,
            latDouble: Double,
            lonDouble: Double,
            paymentType: String,
            streetId: Int,
            townshipId: Int,
            shopTypeId: Int,
            districtId: Int,
            stateDivisionId: Int
        ): Intent {
            val intent = Intent(context, AddNewCustomerActivity::class.java)
            intent.putExtra(IE_NAME, customerName)
            intent.putExtra(IE_PERSON, contactPerson)
            intent.putExtra(IE_PHONE, phone)
            intent.putExtra(IE_ADDRESS, address)
            intent.putExtra(IE_LATIDUTE, latDouble)
            intent.putExtra(IE_LONGITUDE, lonDouble)
            intent.putExtra(IE_PAYMENT_TYPE, paymentType)
            intent.putExtra(IE_STREET_ID, streetId)
            intent.putExtra(IE_TOWNSHIP_ID, townshipId)
            intent.putExtra(IE_SHOP_TYPE_ID, shopTypeId)
            intent.putExtra(IE_DISTINCT_ID, districtId)
            intent.putExtra(IE_STATE_DIVISION_ID, stateDivisionId)
            return intent
        }
    }

    private var streetNameList = mutableListOf<String>()
    var townshipNameList = mutableListOf<String>()
    var shopTypeNameList = mutableListOf<String>()
    var distinctNameList = mutableListOf<String>()
    var stateDivisionNameList = mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        reportViewModel.customerDataList.observe(this, Observer {
            customerList = it
        })

        addNewCustomerActivityViewModel.streetDataList.observe(this, Observer {
            streetList = it
            for (street in it!!) {
                streetNameList.add(street.street_name.toString())
            }
            //Add street name in spinner in this fragment
            val streetNameSpinnerAdapter =
                ArrayAdapter(this, android.R.layout.simple_spinner_item, streetNameList)
            streetNameSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            street.adapter = streetNameSpinnerAdapter
            street.setSelection(streetIndex!!)
            street.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        streetIndex1 = p2
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                }
        })
        addNewCustomerActivityViewModel.townshipDataList.observe(this, Observer {
            townshipList = it
            for (township in it!!) {
                townshipNameList.add(township.township_name.toString())
            }
            //Add township name in spinner in this fragment
            val townshipNameSpinnerAdapter =
                ArrayAdapter(this, android.R.layout.simple_spinner_item, townshipNameList)
            townshipNameSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            tvTownship.adapter = townshipNameSpinnerAdapter
            tvTownship.setSelection(townshipIndex!!)
            tvTownship.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        p0: AdapterView<*>?,
                        p1: View?,
                        p2: Int,
                        p3: Long
                    ) {
                        townshipName = townshipNameList[p2]
                        townshipIndex1 = p2
                        Log.d("Township Name", "No - $townshipIndex1, $townshipName")

                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                }
        })
        addNewCustomerActivityViewModel.shopTypeDataList.observe(this, Observer {
            shopTypeList = it
            for (shop in it!!) {
                shopTypeNameList.add(shop.shop_type_name.toString())
            }
            //Add shop type name in spinner in this fragment
            val shopTypeNameSpinnerAdapter =
                ArrayAdapter(this, android.R.layout.simple_spinner_item, shopTypeNameList)
            shopTypeNameSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            shoptypespn.adapter = shopTypeNameSpinnerAdapter
            shoptypespn.setSelection(shopTypeIndex!!)
            shoptypespn.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        p0: AdapterView<*>?,
                        p1: View?,
                        p2: Int,
                        p3: Long
                    ) {
                        shopTypeIndex1 = p2
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                }

        })
        addNewCustomerActivityViewModel.distinctDataList.observe(this, Observer {
            districtList = it
            for (distinct in it!!) {
                distinctNameList.add(distinct.name.toString())
            }
            //Add distinct name in spinner in this fragment
            val distinctNameSpinnerAdapter =
                ArrayAdapter(this, android.R.layout.simple_spinner_item, distinctNameList)
            distinctNameSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            districtlist.adapter = distinctNameSpinnerAdapter
            districtlist.setSelection(distinctIndex!!)
            districtlist.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        p0: AdapterView<*>?,
                        p1: View?,
                        p2: Int,
                        p3: Long
                    ) {
                        distinctIndex1 = p2
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                }
        })
        addNewCustomerActivityViewModel.stateDivisionDataList.observe(this, Observer
        {
            stateDivisionList = it
            for (division in it!!) {
                stateDivisionNameList.add(division.name.toString())
            }
            //Add stateDivision name in spinner in this fragment
            val stateDivisionNameSpinnerAdapter =
                ArrayAdapter(this, android.R.layout.simple_spinner_item, stateDivisionNameList)
            stateDivisionNameSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            statedivisionlist.adapter = stateDivisionNameSpinnerAdapter
            statedivisionlist.setSelection(stateDivisionIndex!!)
            statedivisionlist.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        p0: AdapterView<*>?,
                        p1: View?,
                        p2: Int,
                        p3: Long
                    ) {
                        stateDivisionIndex1 = p2
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                }
        })
        reportViewModel.loadCustomer()
        addNewCustomerActivityViewModel.loadStreetList()
        addNewCustomerActivityViewModel.loadTownshipList()
        addNewCustomerActivityViewModel.loadShopTypeList()
        addNewCustomerActivityViewModel.loadDistinctList()
        addNewCustomerActivityViewModel.loadStateDivisionList()
        getDataFromLocationActivity()
        registerIDs()
        catchEvents()

    }

    private fun getDataFromLocationActivity() {
        val name = intent.getStringExtra(IE_NAME)
        val person = intent.getStringExtra(IE_PERSON)
        val phone = intent.getStringExtra(IE_PHONE)
        val address = intent.getStringExtra(IE_ADDRESS)
        val type = intent.getStringExtra(IE_PAYMENT_TYPE)
        streetIndex = intent.getIntExtra(IE_STREET_ID, 0)
        townshipIndex = intent.getIntExtra(IE_TOWNSHIP_ID, 0)
        shopTypeIndex = intent.getIntExtra(IE_SHOP_TYPE_ID, 0)
        distinctIndex = intent.getIntExtra(IE_DISTINCT_ID, 0)
        stateDivisionIndex = intent.getIntExtra(IE_STATE_DIVISION_ID, 0)
        customerLat = intent.getDoubleExtra(IE_LATIDUTE, 0.0)
        customerLng = intent.getDoubleExtra(IE_LONGITUDE, 0.0)


        tvCustomerName.setText(name)
        contactPerson.setText(person)
        phoneNumber.setText(phone)
        tvAddress.setText(address)

        if (type == "Indirect Sales") {
            rbIndirectSale.isChecked = true
        } else {
            rbDirectSale.isClickable = true
        }

        putCustomerName = tvCustomerName.text.toString()
        putContactPerson = contactPerson.text.toString()
        putCustomerPhone = phoneNumber.text.toString()
        putCustomerAddress = tvAddress.text.toString()
        customer_location.text = "$customerLat,$customerLng"
    }

    private fun registerIDs() {
        val customerNameMaxLength = 50
        val customerNameFilterArray = arrayOfNulls<InputFilter>(1)
        customerNameFilterArray[0] = InputFilter.LengthFilter(customerNameMaxLength)
        tvCustomerName.filters = customerNameFilterArray

        val contactPersonMaxLength = 50
        val contactPersonFilterArray = arrayOfNulls<InputFilter>(1)
        contactPersonFilterArray[0] = InputFilter.LengthFilter(contactPersonMaxLength)
        contactPerson.filters = contactPersonFilterArray

        val addressMaxLength = 100
        val addressFilterArray = arrayOfNulls<InputFilter>(1)
        addressFilterArray[0] = InputFilter.LengthFilter(addressMaxLength)
        tvAddress.filters = addressFilterArray

        val phoneNumberMaxLength = 20
        val phoneNumberFilterArray = arrayOfNulls<InputFilter>(1)
        phoneNumberFilterArray[0] = InputFilter.LengthFilter(phoneNumberMaxLength)
        phoneNumber.filters = phoneNumberFilterArray
    }


    private fun catchEvents() {
        cancel_img.setOnClickListener {
            reset()
            this@AddNewCustomerActivity.onBackPressed()
        }

        reset_img.setOnClickListener {
            reset()
        }

        add_img.setOnClickListener {
            Utils.askConfirmationDialog(
                "Save",
                "Do you want to add?",
                "save",
                this@AddNewCustomerActivity,
                this::onClickSaveButton
            )
        }

        next_img.setOnClickListener {
            radioButtonClick()
            var isErrorFlag = false
            if (tvCustomerName.text.isEmpty()) {

                tvCustomerName.error = "Customer name is required."
                isErrorFlag = true
            }
            if (contactPerson.text.isEmpty()) {

                contactPerson.error = "Contact person is required."
                isErrorFlag = true
            }
            if (phoneNumber.text.isEmpty()) {

                phoneNumber.error = "Phone number is required."
                isErrorFlag = true
            }
            if (tvAddress.text.isEmpty()) {

                tvAddress.error = "Customer address is required."
                isErrorFlag = true
            }
            if (isErrorFlag) {

                return@setOnClickListener
            } else {
                val intent = AddNewCustomerLocationActivity.newIntentFromAddNewCustomerActivity(
                    applicationContext,
                    tvCustomerName.text.toString(),
                    contactPerson.text.toString(),
                    phoneNumber.text.toString(),
                    tvAddress.text.toString(),
                    paymentType,
                    streetIndex1,
                    townshipIndex1,
                    shopTypeIndex1,
                    distinctIndex1,
                    stateDivisionIndex1
                )
                startActivity(intent)
                finish()
            }

        }
    }

    private fun onClickSaveButton(type: String) {
        var isErrorFlag = false
        if (tvCustomerName.text.isEmpty()) {

            tvCustomerName.error = "Customer name is required."
            isErrorFlag = true
        }
        if (contactPerson.text.isEmpty()) {

            contactPerson.error = "Contact person is required."
            isErrorFlag = true
        }
        if (phoneNumber.text.isEmpty()) {

            phoneNumber.error = "Phone number is required."
            isErrorFlag = true
        }
        if (tvAddress.text.isEmpty()) {

            tvAddress.error = "Customer address is required."
            isErrorFlag = true
        }
        if (customer_location.text.isEmpty() || customer_location.text == "0.0,0.0") {
            customer_location.error = "Customer Location is required"
            isErrorFlag = true
        }
        if (isErrorFlag) {

            return
        }
        userId = AppUtils.getStringFromShp(Constant.SALEMAN_ID, this)!!
        customerId = userId + Utils.getCurrentDate(false) + DecimalFormat("00").format(
            AppUtils.getIntFromShp(
                Constant.ADDNEWCUSTOMERCOUNT,
                this
            )!! + 1.0
        )
        var customId = 0
        val tempCusId = (AppUtils.getIntFromShp(
            Constant.TABLET_KEY,
            this
        )!! + (AppUtils.getIntFromShp(Constant.MAX_KEY, this)!! + 1)).toString()
        if (tempCusId != null && tempCusId != "") {
            customId = Integer.parseInt(tempCusId)
        }
        var customer: NewCustomer? = null
        if (type == "save") {
            customer = NewCustomer(
                customId + 1,
                customerId!!,
                tvCustomerName.text.toString(),
                contactPerson.text.toString(),
                phoneNumber.text.toString(),
                tvAddress.text.toString(),
                customerLat!!,
                customerLng!!,
                streetList!![streetIndex1].id,
                townshipList!![townshipIndex1].id,
                townshipName,
                districtList!![distinctIndex1].id,
                stateDivisionList!![stateDivisionIndex1].id,
                shopTypeList!![shopTypeIndex1].id,
                userId,
                Utils.getCurrentDate(true),
                "1"
            )
        }
        if (customer != null) {
            addNewCustomerActivityViewModel.loadAddNewCustomerList(customer)
        }
        AppUtils.saveIntToShp(
            Constant.MAX_KEY,
            AppUtils.getIntFromShp(Constant.MAX_KEY, this)!! + 1, this
        )

        //backPress and clear
        reset()
        this@AddNewCustomerActivity.onBackPressed()

    }

    private fun radioButtonClick() {
        // Get the checked radio button id from radio group
        var id: Int = rgCustomerTye.checkedRadioButtonId
        if (id != -1) { // If any radio button checked from radio group
            // Get the instance of radio button using id
            val radio: RadioButton = findViewById(id)
            paymentType = "${radio.text}"
        } else {
            rgCustomerTye.isClickable = false
        }
    }

    private fun reset() {
        putCustomerName = null
        putCustomerPhone = null
        putCustomerAddress = null
        putContactPerson = null
        putZonePosition = null
        putCustomerCategoryPosition = null
        putTownshipPosition = null
        customerLat = null
        customerLng = null
        tvCustomerName.setText("")
        tvCustomerName.error = null
        contactPerson.setText("")
        contactPerson.error = null
        phoneNumber.setText("")
        phoneNumber.error = null
        tvAddress.setText("")
        tvAddress.error = null
        customer_location.text = ""
        customer_location.error = null
        tvCustomerName.requestFocus()
    }
}

package com.aceplus.dms.ui.activities.customer

import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.aceplus.dms.R
import com.aceplus.dms.ui.activities.CustomerVisitActivity
import com.aceplus.dms.utils.Utils
import com.aceplus.dms.viewmodel.customer.prospectcustomer.ProspectCustomerViewModel
import com.aceplus.domain.entity.ShopType
import com.aceplus.domain.entity.predefine.District
import com.aceplus.domain.entity.predefine.StateDivision
import com.aceplus.domain.entity.predefine.Street
import com.aceplus.domain.entity.predefine.Township
import com.aceplus.domain.model.customer.prospectcustomer.NewCustomer
import com.aceplussolutions.rms.ui.activities.BaseActivity
import kotlinx.android.synthetic.main.activity_add_new_customer.*
import org.json.JSONException
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein

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

    private var townshipId = 0
    private var streetId = 0
    private var districtId = 0
    private var stateDivisionId = 0
    private var shopTypeId = 0

    private var townshipList: List<Township>? = null
    private var streetList: List<Street>? = null
    private var districtList: List<District>? = null
    private var stateDivisionList: List<StateDivision>? = null
    private var shopTypeList: List<ShopType>? = null
    private val addNewCustomerActivityViewModel: ProspectCustomerViewModel by viewModel()

    companion object {
        private const val NAME = "name"
        private const val PERSON = "person"
        private const val PHONE = "phone"
        private const val ADDRESS = "address"
        private const val LATIDUTE = "customerLat"
        private const val LONGITUDE = "customerLan"
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
            lonDouble: Double
        ): Intent {
            val intent = Intent(context, AddNewCustomerActivity::class.java)
            intent.putExtra(NAME, customerName)
            intent.putExtra(PERSON, contactPerson)
            intent.putExtra(PHONE, phone)
            intent.putExtra(ADDRESS, address)
            intent.putExtra(LATIDUTE, latDouble)
            intent.putExtra(LONGITUDE, lonDouble)
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
        getDataFromLocationActivity()
        registerIDs()
        catchEvents()
        try {


            var noDataFlg = false

//            if (LoginActivity.mySharedPreference != null) {
            //               userId = LoginActivity.mySharedPreference.getString(Constant.SALEMAN_ID, "")
//            } else {
//                noDataFlg = true
//            }
            if (shopTypeList != null && shopTypeList!!.isNotEmpty()) {
                shopTypeId = shopTypeList!![shoptypespn.selectedItemPosition].id
            } else {
                noDataFlg = true

            }

            if (townshipList != null && townshipList!!.isNotEmpty()) {
                townshipId = townshipList!![tvTownship.selectedItemPosition].township_id!!.toInt()
            } else {
                noDataFlg = true
            }

            if (streetList != null && streetList!!.isNotEmpty()) {
                streetId =
                    streetList!![street.selectedItemPosition].street_id!!.toInt()
            } else {
                noDataFlg = true
            }

            if (districtList != null && districtList!!.isNotEmpty()) {
                districtId = districtList!![districtlist.selectedItemPosition].id
            } else {
                noDataFlg = true
            }

            if (stateDivisionList != null && stateDivisionList!!.isNotEmpty()) {
                stateDivisionId =
                    stateDivisionList!![statedivisionlist.selectedItemPosition].id
            } else {
                noDataFlg = true
            }

            if (noDataFlg) {
//                AlertDialog.Builder(this)
//                    .setTitle("")
//                    .setMessage("Need to download information")
//                    .setPositiveButton("Yes") { _, _ ->
//                        val intent = Intent(application, CustomerVisitActivity::class.java)
//                        startActivity(intent)
//                    }
//                    .show()
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        } catch (e: NullPointerException) {
            e.printStackTrace()
            Utils.backToLogin(this)
        }
        addNewCustomerActivityViewModel.loadStreetList()
        addNewCustomerActivityViewModel.loadTownshipList()
        addNewCustomerActivityViewModel.loadShopTypeList()
        addNewCustomerActivityViewModel.loadDistinctList()
        addNewCustomerActivityViewModel.loadStateDivisionList()
    }

    private fun getDataFromLocationActivity() {
        val name = intent.getStringExtra(NAME)
        val person = intent.getStringExtra(PERSON)
        val phone = intent.getStringExtra(PHONE)
        val address = intent.getStringExtra(ADDRESS)
        customerLat = intent.getDoubleExtra(LATIDUTE, 0.0)
        customerLng = intent.getDoubleExtra(LONGITUDE, 0.0)

        tvCustomerName.setText(name)
        contactPerson.setText(person)
        phoneNumber.setText(phone)
        tvAddress.setText(address)

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

    private fun getStreetData() {
        addNewCustomerActivityViewModel.streetDataList.observe(this, Observer {
            streetList = it
            for (street in it!!) {
                streetNameList.add(street.street_name.toString())
            }


            //Add customer name in spinner in this fragment
            val customerNameSpinnerAdapter =
                ArrayAdapter(this, android.R.layout.simple_spinner_item, streetNameList)
            customerNameSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            street.adapter = customerNameSpinnerAdapter
            street.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        val street = p2
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                }
        })
    }

    private fun catchEvents() {
        cancel_img.setOnClickListener {
            reset()
            this@AddNewCustomerActivity.onBackPressed()
        }
        getStreetData()

        addNewCustomerActivityViewModel.townshipDataList.observe(this, Observer {
            townshipList = it
            for (township in it!!) {
                townshipNameList.add(township.township_name.toString())
            }

            //Add customer name in spinner in this fragment
            val customerNameSpinnerAdapter =
                ArrayAdapter(this, android.R.layout.simple_spinner_item, townshipNameList)
            customerNameSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            tvTownship.adapter = customerNameSpinnerAdapter
            tvTownship.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        p0: AdapterView<*>?,
                        p1: View?,
                        p2: Int,
                        p3: Long
                    ) {
                        val township = p2
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

            //Add customer name in spinner in this fragment
            val customerNameSpinnerAdapter =
                ArrayAdapter(this, android.R.layout.simple_spinner_item, shopTypeNameList)
            customerNameSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            shoptypespn.adapter = customerNameSpinnerAdapter
            shoptypespn.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        p0: AdapterView<*>?,
                        p1: View?,
                        p2: Int,
                        p3: Long
                    ) {
                        val shopType = p2
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

            //Add customer name in spinner in this fragment
            val customerNameSpinnerAdapter =
                ArrayAdapter(this, android.R.layout.simple_spinner_item, distinctNameList)
            customerNameSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            districtlist.adapter = customerNameSpinnerAdapter
            districtlist.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        p0: AdapterView<*>?,
                        p1: View?,
                        p2: Int,
                        p3: Long
                    ) {
                        val distinct = p2
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                }
        })
        addNewCustomerActivityViewModel.stateDivisionDataList.observe(this, Observer {
            stateDivisionList = it
            for (division in it!!) {
                stateDivisionNameList.add(division.name.toString())
            }

            //Add customer name in spinner in this fragment
            val customerNameSpinnerAdapter =
                ArrayAdapter(this, android.R.layout.simple_spinner_item, stateDivisionNameList)
            customerNameSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            statedivisionlist.adapter = customerNameSpinnerAdapter
            statedivisionlist.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        p0: AdapterView<*>?,
                        p1: View?,
                        p2: Int,
                        p3: Long
                    ) {
                        val stateDivision = p2
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                }
        })

        reset_img.setOnClickListener {
            reset()
        }

        add_img.setOnClickListener {
            if (tvCustomerName.text.isNullOrEmpty() ||
                contactPerson.text.isNullOrEmpty() ||
                phoneNumber.text.isNullOrEmpty() ||
                tvAddress.text.isNullOrEmpty() ||
                customer_location.text.isNullOrEmpty()

            ) {
                AlertDialog.Builder(this)
                    .setTitle("")
                    .setMessage("Need to customer information")
                    .setPositiveButton("Yes") { dialog, _ ->
                       dialog.dismiss()
                    }
                    .show()
            } else {
                Utils.askConfirmationDialog(
                    "Save",
                    "Do you want to add?",
                    "save",
                    this@AddNewCustomerActivity,
                    this::onClickSaveButton
                )
            }

        }

        next_img.setOnClickListener {
            if (tvCustomerName.text.isNullOrEmpty() ||
                contactPerson.text.isNullOrEmpty() ||
                phoneNumber.text.isNullOrEmpty() ||
                tvAddress.text.isNullOrEmpty() ||
                customer_location.text.isNullOrEmpty()

            ) {
                AlertDialog.Builder(this)
                    .setTitle("")
                    .setMessage("Need to customer information")
                    .setPositiveButton("Yes") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            } else {
                val intent = AddNewCustomerLocationActivity.newIntentFromAddNewCustomerActivity(
                    applicationContext,
                    tvCustomerName.text.toString(),
                    contactPerson.text.toString(),
                    phoneNumber.text.toString(),
                    tvAddress.text.toString()
                )
                startActivity(intent)
                finish()
            }
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

    private fun onClickSaveButton(type: String) {
        var customer: NewCustomer? = null
        if (type == "save") {
            customer = NewCustomer(
                putCustomerName!!, putContactPerson!!, putCustomerPhone!!,
                putCustomerAddress!!, customerLat!!, customerLng!!,
                townshipId.toInt(), districtId.toInt(), stateDivisionId.toInt(), shopTypeId.toInt()
            )
        }
        if (customer != null) {
            addNewCustomerActivityViewModel.loadAddNewCustomerList(customer)
        }
        //backPress and clear
        reset()
        this@AddNewCustomerActivity.onBackPressed()

    }
}

package com.aceplus.dms.ui.activities.customer

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.aceplus.dms.R
import com.aceplus.dms.viewmodel.customer.prospectcustomer.ProspectCustomerViewModel
import com.aceplus.domain.entity.customer.Customer
import com.aceplussolutions.rms.ui.activities.BaseActivity
import kotlinx.android.synthetic.main.activity_add_new_customer.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein

class AddNewCustomerActivity : BaseActivity(), KodeinAware {
    override val kodein: Kodein by kodein()
    override val layoutId: Int
        get() = R.layout.activity_add_new_customer

    private val addNewCustomerActivityViewModel: ProspectCustomerViewModel by viewModel()

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, AddNewCustomerActivity::class.java)
        }
    }

    private var streetNameList = mutableListOf<String>()
    var townshipNameList = mutableListOf<String>()
    var shopTypeNameList = mutableListOf<String>()
    var distinctNameList = mutableListOf<String>()
    var stateDivisionNameList = mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var customerNew: Customer
        addNewCustomerActivityViewModel.streetDataList.observe(this, Observer {
            if (it != null) {
                for (street in it) {
                    streetNameList.add(street.street_name.toString())
                }

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
        addNewCustomerActivityViewModel.townshipDataList.observe(this, Observer {
            if (it != null) {
                for (township in it) {
                    townshipNameList.add(township.township_name.toString())
                }

            }
            //Add customer name in spinner in this fragment
            val customerNameSpinnerAdapter =
                ArrayAdapter(this, android.R.layout.simple_spinner_item, townshipNameList)
            customerNameSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            tvTownship.adapter = customerNameSpinnerAdapter
            tvTownship.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        val township = p2
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                }
        })
        addNewCustomerActivityViewModel.shopTypeDataList.observe(this, Observer {
            if (it != null) {
                for (shop in it) {
                    shopTypeNameList.add(shop.shop_type_name.toString())
                }

            }
            //Add customer name in spinner in this fragment
            val customerNameSpinnerAdapter =
                ArrayAdapter(this, android.R.layout.simple_spinner_item, shopTypeNameList)
            customerNameSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            shoptypespn.adapter = customerNameSpinnerAdapter
            shoptypespn.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        val shopType = p2
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                }
        })
        addNewCustomerActivityViewModel.distinctDataList.observe(this, Observer {
            if (it != null) {
                for (distinct in it) {
                    distinctNameList.add(distinct.name.toString())
                }

            }
            //Add customer name in spinner in this fragment
            val customerNameSpinnerAdapter =
                ArrayAdapter(this, android.R.layout.simple_spinner_item, distinctNameList)
            customerNameSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            districtlist.adapter = customerNameSpinnerAdapter
            districtlist.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        val distinct = p2
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                }
        })
        addNewCustomerActivityViewModel.stateDivisionDataList.observe(this, Observer {
            if (it != null) {
                for (division in it) {
                    stateDivisionNameList.add(division.name.toString())
                }

            }
            //Add customer name in spinner in this fragment
            val customerNameSpinnerAdapter =
                ArrayAdapter(this, android.R.layout.simple_spinner_item, stateDivisionNameList)
            customerNameSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            statedivisionlist.adapter = customerNameSpinnerAdapter
            statedivisionlist.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        val stateDivision = p2
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                }
        })

        next_img.setOnClickListener {
            Toast.makeText(this@AddNewCustomerActivity,"Coming Soon",Toast.LENGTH_SHORT).show()
        }
        addNewCustomerActivityViewModel.loadStreetList()
        addNewCustomerActivityViewModel.loadTownshipList()
        addNewCustomerActivityViewModel.loadShopTypeList()
        addNewCustomerActivityViewModel.loadDistinctList()
        addNewCustomerActivityViewModel.loadStateDivisionList()
    }
}

package com.example.dms.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dms.R
import com.example.dms.di.injection
import com.example.dms.network.request.customerRequest
import com.example.dms.network.response.Customer
import com.example.dms.ui.adapters.CustomerAdapter
import com.example.dms.viewmodels.Factory.customer.CustomerMainViewModel
import com.example.dms.viewmodels.Factory.customer.CustomerMainViewModelFactory
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private val customerAdapter: CustomerAdapter by lazy { CustomerAdapter(this::onClickNoticeListItem) }

    private val customerViewModel: CustomerMainViewModel by lazy {
        ViewModelProvider(
            this,
            CustomerMainViewModelFactory(
                injection.provideCustomerRepository(
                    applicationContext
                )
            )
        )
            .get(CustomerMainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvCustomer.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = customerAdapter
        }
        btnSale.setOnClickListener {
            val intent = Intent(this, SaleActivity::class.java)
            startActivity(intent)
        }

        customerViewModel.successState.observe(this, Observer {
            this.customerAdapter.setNewList(it)
        })
        customerViewModel.errorState.observe(this, Observer {
            Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
        })

        var list: List<String> = emptyList()

        var cus: customerRequest = customerRequest(
            "1234567",
            "1234567",
            "T1",
            "YWNlcGx1cw==",
            4,
            "da8d5b4598982da7",
            "2019-08-25",
            list
        )

        var gson: Gson = Gson()

        var param: String = gson.toJson(cus)

        customerViewModel.loadCustomerList(param)


    }



    private fun onClickNoticeListItem(data: Customer) {
        Toast.makeText(
            applicationContext,
            "You clicked at ${data.CUSTOMER_NAME}",
            Toast.LENGTH_SHORT
        ).show()

        name.text = data.CUSTOMER_NAME
        address.text=data.ADDRESS
        phone.text=data.PH
        township.text=data.township_number.toString()
        credit.text=data.CREDIT_TERM.toString()
        limit.text=data.CREDIT_LIMIT.toString()
        creditamount.text=data.CREDIT_AMT
        dueAmount.text=data.DUE_AMT
        prepaidAmount.text=data.PREPAID_AMT
        paymentType.text=data.PAYMENT_TYPE
        latitude.text=data.LATITUDE.toString()
        longitude.text=data.LONGITUDE.toString()
        remark.text=data.route_schedule_status.toString()

    }

    }

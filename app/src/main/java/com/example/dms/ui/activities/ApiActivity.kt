package com.example.dms.ui.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dms.R
import com.example.dms.di.injection
import com.example.dms.network.request.customerRequest
import com.example.dms.network.request.saleRequest
import com.example.dms.ui.adapters.ApiCustomerAdapter
import com.example.dms.ui.adapters.ApiSaleAdapter
import com.example.dms.ui.adapters.CustomerAdapter
import com.example.dms.viewmodels.Factory.api.ApiMainViewModel
import com.example.dms.viewmodels.Factory.api.ApiMainViewModelFactory
import com.example.dms.viewmodels.Factory.customer.CustomerMainViewModel
import com.example.dms.viewmodels.Factory.customer.CustomerMainViewModelFactory
import com.google.gson.Gson
import io.reactivex.Observer
import kotlinx.android.synthetic.main.activity_api.*
import kotlinx.android.synthetic.main.activity_api.btnCheck
import kotlinx.android.synthetic.main.activity_api.rvCustomer
import kotlinx.android.synthetic.main.activity_api.rvSale
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_sale.*

class ApiActivity: AppCompatActivity() {

//      private val apiCustomerAdapter: ApiCustomerAdapter by lazy { ApiCustomerAdapter() }
//      private val apiSaleAdapter: ApiSaleAdapter by lazy { ApiSaleAdapter() }
//
//    private val apiViewModel: ApiMainViewModel by lazy {
//        ViewModelProvider(
//            this,
//            ApiMainViewModelFactory(
//                injection.provideApiRepository(
//                    this
//                )
//            )
//        )
//            .get(ApiMainViewModel::class.java)
//    }
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_api)
//        btnCheck.setOnClickListener {
//            val apiIntent= Intent(this,MainActivity::class.java)
//            startActivity(apiIntent)
//        }
//        rvCustomer.apply {
//            layoutManager = LinearLayoutManager(applicationContext)
//            adapter = apiCustomerAdapter
//        }
//        rvSale.adapter = apiSaleAdapter
//        rvSale.layoutManager = LinearLayoutManager(this)
//
//        apiViewModel.successCustomerState.observe(this,Observer{
//            this.apiCustomerAdapter.setNewList(it)
//        })
//        apiViewModel.errorState.observe(this,Observer {
//            Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
//        })
//
//        var list: List<String> = emptyList()
//
//        var cus: customerRequest = customerRequest(
//            "1234567",
//            "1234567",
//            "T1",
//            "YWNlcGx1cw==",
//            4,
//            "da8d5b4598982da7",
//            "2019-08-25",
//            list
//        )
//
//        var gson: Gson = Gson()
//
//        var param: String = gson.toJson(cus)
//
//        apiViewModel.loadCustomerList(param)
//        apiViewModel.successSaleState.observe(this, androidx.lifecycle.Observer {
//            apiSaleAdapter.setData(it)
//        })
//
//        apiViewModel.errorState.observe(this, androidx.lifecycle.Observer {
//            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
//
//        })
//        var saleList: List<String> = emptyList()
//
//        var sale: saleRequest = saleRequest(
//            "1234567",
//            "1234567",
//            "T1",
//            "YWNlcGx1cw==",
//            500,
//            "2019-08-25",
//            saleList,
//            "download"
//        )
//
//
//
//        var paramSale: String = gson.toJson(sale)
//
//        apiViewModel.loadSaleList(paramSale)
//
//
//    }

}


package com.example.dms.ui.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dms.R
import com.example.dms.di.injection
import com.example.dms.network.request.saleInvoice
import com.example.dms.network.request.saleRequest
import com.example.dms.network.response.Customer
import com.example.dms.network.response.Sale.Product
import com.example.dms.ui.adapters.SaleAdapter
import com.example.dms.ui.adapters.SaleInvoiceAdapter
import com.example.dms.viewmodels.Factory.sale.SaleMainViewModelFactory
import com.example.dms.viewmodels.Factory.sale.SaleMainViewModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_sale.*
import kotlinx.android.synthetic.main.sales.*

class SaleActivity : AppCompatActivity() {
    private val saleAdapter: SaleAdapter by lazy { SaleAdapter(this::onClickProduct) }

    private val saleViewModel: SaleMainViewModel by lazy {
        ViewModelProvider(this, SaleMainViewModelFactory(injection.provideSaleRepository(this), this))
            .get(SaleMainViewModel::class.java)
    }

    companion object{
        fun getIntent(context: Context, customer: Customer): Intent{
            val intent = Intent(context, SaleActivity::class.java)
            intent.putExtra("Customer", customer)
            return intent
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sale)


        btnclose.setOnClickListener { finish() }

        btnCheck.setOnClickListener {
            val filteredList = saleViewModel.nullCheckingSalesItem()
            if (filteredList.isNotEmpty()){
                startActivity(CheckOutActivity.getIntent(
                    this,
                    filteredList))
            } else{
                Toast.makeText(this, "Empty", Toast.LENGTH_LONG).show()
            }
        }

        rvSale.adapter = saleAdapter
        rvSale.layoutManager = LinearLayoutManager(this)

        val saleItemAdapter = saleViewModel.saleItemAdpter
        rvSaleInvoice.adapter = saleItemAdapter
        rvSaleInvoice.layoutManager = LinearLayoutManager(this)

        saleViewModel.successState.observe(this, Observer {
            saleAdapter.setData(it)
        })

        saleViewModel.errorState.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()

        })

        saleViewModel.netAmount.observe(this, Observer {
            amount.text = it.toString()
        })
        var list: List<String> = emptyList()

        var sale: saleRequest = saleRequest(
            "1234567",
            "1234567",
            "T1",
            "YWNlcGx1cw==",
            500,
            "2019-08-25",
            list,
            "download"
        )

        var gson: Gson = Gson()

        var param: String = gson.toJson(sale)

        saleViewModel.loadSaleList(param)

    }

    private fun onClickProduct(product: Product){
        saleViewModel.addItem(product)
    }

}







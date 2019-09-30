package com.example.dms.ui.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.Toast
import androidx.lifecycle.Observer

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dms.R
import com.example.dms.di.injection
import com.example.dms.ui.adapters.SaleItemReportAdapter
import com.example.dms.viewmodels.Factory.ItemReport.ItemReportMainViewModel
import com.example.dms.viewmodels.Factory.ItemReport.ItemReportViewModelFactory


import kotlinx.android.synthetic.main.activity_item_report.*

class ItemReportActivity : AppCompatActivity() {
    private val saleItemAdapter: SaleItemReportAdapter by lazy { SaleItemReportAdapter() }

    private val itemReportViewModel: ItemReportMainViewModel by lazy {
        ViewModelProvider(
            this,
            ItemReportViewModelFactory(injection.provideItemReportRepository(this))
        )
            .get(ItemReportMainViewModel::class.java)
    }

    companion object {
        fun getIntent(context: Context, invoiceID: String): Intent {
            val intent = Intent(context, ItemReportActivity::class.java)
            intent.putExtra("invoiceID", invoiceID)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_report)
        btnClose.setOnClickListener { finish() }

        itemReportViewModel.successState.observe(this, androidx.lifecycle.Observer
        { saleItemAdapter.setData(it) } )


        itemReportViewModel.errorState.observe(this, Observer
        {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })

        rvItemReportList.adapter = saleItemAdapter
        rvItemReportList.layoutManager = LinearLayoutManager(this)

        itemReportViewModel.getInvoiceItemReport(intent.getStringExtra("invoiceID"))


    }
}

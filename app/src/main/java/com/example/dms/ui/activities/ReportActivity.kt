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
import com.example.dms.data.database.table.InvoiceReport
import com.example.dms.di.injection
import com.example.dms.ui.adapters.ReportAdapter
import com.example.dms.viewmodels.Factory.report.ReportMainViewModel
import com.example.dms.viewmodels.Factory.report.ReportMainViewModelFactory
import kotlinx.android.synthetic.main.activity_item_report.*
import kotlinx.android.synthetic.main.activity_report.*

class ReportActivity : AppCompatActivity() {

    private val reportAdapter: ReportAdapter by lazy {
        ReportAdapter(this::onClickInvoiceReport)
    }
    private val reportViewModel: ReportMainViewModel by lazy {
        ViewModelProvider(this, ReportMainViewModelFactory(injection.provideReportRepository(this),this))
            .get(ReportMainViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)


        reportViewModel.successState.observe(this, Observer {
            reportAdapter.setNewList(it)
        })

        reportViewModel.errorState.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })


        rvInvoiceReport.adapter = reportAdapter
        rvInvoiceReport.layoutManager = LinearLayoutManager(this)

        reportViewModel.getInvoiceReport()

        imgbtnCancelSR.setOnClickListener { finish() }
    }

    private fun onClickInvoiceReport(invoiceID: String) {
        startActivity(ItemReportActivity.getIntent(this,invoiceID))

    }

}


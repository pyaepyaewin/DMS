package com.aceplus.dms.ui.activities.report

import android.app.Dialog
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import com.aceplus.dms.R
import com.aceplus.dms.ui.adapters.report.SaleInvoiceDetailReportAdapter
import com.aceplus.dms.ui.adapters.report.SaleInvoiceReportAdapter
import com.aceplus.dms.viewmodel.report.SaleInvoiceReportViewModel
import com.aceplus.domain.model.report.SaleInvoiceDetailReport
import com.aceplus.domain.model.report.SaleInvoiceReport
import com.aceplus.shared.ui.activities.BaseFragment
import kotlinx.android.synthetic.main.dialog_box_sale_invoice_report.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein

class SaleExchangeTab2:BaseFragment(),KodeinAware {
    override val kodein: Kodein by kodein()
    lateinit var saleInvoiceDialog: RecyclerView
    private val saleInvoiceReportAdapter: SaleInvoiceReportAdapter by lazy {
        SaleInvoiceReportAdapter(
            this::onClickItem
        )
    }
    private val saleInvoiceDetailReportAdapter: SaleInvoiceDetailReportAdapter by lazy { SaleInvoiceDetailReportAdapter() }
    private val saleInvoiceReportViewModel: SaleInvoiceReportViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view: View = inflater.inflate(R.layout.fragment_sale_invoice_report, container, false)

        val customerSpinner = view.findViewById(R.id.customer_spinner_fragment_invoice_report) as Spinner
        val fromDateEditTxt = view.findViewById(R.id.edit_text_sale_report_from_date) as EditText
        val toDateEditTxt = view.findViewById(R.id.edit_text_sale_report_to_date) as EditText
        val searchBtn = view.findViewById(R.id.btn_sale_report_search) as Button
        val clearBtn = view.findViewById(R.id.btn_sale_report_clear) as Button
        val fromDateTxtView = view.findViewById(R.id.txt_view_from_date) as TextView
        val toDateTxtView = view.findViewById(R.id.txt_view_to_date) as TextView
        val tbNet = view.findViewById(R.id.table_row_advance_amt) as TableRow
        val tbTotalAmt = view.findViewById(R.id.tb_1) as TableRow
        val tbDiscount = view.findViewById(R.id.tb_2) as TableRow
        val tbNetAmt = view.findViewById(R.id.tb_3) as TableRow

        tbNet.setVisibility(View.GONE)
        tbDiscount.setVisibility(View.GONE)
        tbTotalAmt.setVisibility(View.GONE)
        tbNetAmt.setVisibility(View.GONE)


        customerSpinner.setVisibility(View.GONE)
        fromDateEditTxt.setVisibility(View.GONE)
        toDateEditTxt.setVisibility(View.GONE)
        fromDateTxtView.setVisibility(View.GONE)
        toDateTxtView.setVisibility(View.GONE)
        searchBtn.setVisibility(View.GONE)
        clearBtn.setVisibility(View.GONE)

        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var saleInvoiceReports = view.findViewById(R.id.saleInvoceReports) as RecyclerView

        saleInvoiceReportViewModel.saleInvoiceReportSuccessState.observe(this, Observer {
            saleInvoiceReportAdapter.setNewList(it as ArrayList<SaleInvoiceReport>)
        })

        saleInvoiceReportViewModel.saleInvoiceReportErrorState.observe(this, Observer {
            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
        })

        saleInvoiceReports.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = saleInvoiceReportAdapter
        }
        saleInvoiceReportViewModel.saleInvoiceReport()
    }
    private fun onClickItem(invoiceId: String) {
        saleInvoiceDialog = view!!.findViewById(R.id.saleInvoiceDialog)
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setContentView(R.layout.dialog_box_sale_invoice_report)

        //Dialog sale history report of invoice detail recycler view
        saleInvoiceReportViewModel.saleInvoiceDetailReportSuccessState.observe(this, Observer {
            saleInvoiceDetailReportAdapter.setNewList(it as java.util.ArrayList<SaleInvoiceDetailReport>)
        })

        saleInvoiceReportViewModel.saleInvoiceReportErrorState.observe(this, Observer {
            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
        })
        saleInvoiceDialog.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = saleInvoiceDetailReportAdapter
        }
        saleInvoiceReportViewModel.saleInvoiceDetailReport(invoiceId = invoiceId)

        //Action of dialog button
        dialog.findViewById<Button>(R.id.btn_print).setOnClickListener {
            dialog.dismiss()
        }
        dialog.findViewById<Button>(R.id.btn_ok).setOnClickListener {
            dialog.dismiss()
        }

    }
}
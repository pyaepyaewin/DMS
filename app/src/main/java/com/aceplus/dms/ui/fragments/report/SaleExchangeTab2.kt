package com.aceplus.dms.ui.fragments.report

import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.aceplus.dms.R
import com.aceplus.dms.ui.adapters.report.SaleInvoiceDetailReportAdapter
import com.aceplus.dms.ui.adapters.report.SaleInvoiceReportAdapter
import com.aceplus.dms.viewmodel.report.ReportViewModel
import com.aceplus.domain.vo.report.SaleInvoiceDetailReport
import com.aceplus.domain.vo.report.SaleInvoiceReport
import com.aceplus.shared.ui.activities.BaseFragment
import kotlinx.android.synthetic.main.dialog_box_sale_invoice_report.*
import kotlinx.android.synthetic.main.dialog_box_sale_invoice_report.view.*
import kotlinx.android.synthetic.main.fragment_sale_invoice_report.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein

class SaleExchangeTab2 : BaseFragment(), KodeinAware {
    override val kodein: Kodein by kodein()
    private val saleInvoiceReportAdapter: SaleInvoiceReportAdapter by lazy {
        SaleInvoiceReportAdapter(
            this::onClickItem
        )
    }
    private val saleInvoiceDetailReportAdapter: SaleInvoiceDetailReportAdapter by lazy { SaleInvoiceDetailReportAdapter() }
    private val saleInvoiceReportViewModel: ReportViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view: View = inflater.inflate(R.layout.fragment_sale_invoice_report, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        table_row_advance_amt.visibility = View.GONE
        tb_2.visibility = View.GONE
        tb_1.visibility = View.GONE
        tb_3.visibility = View.GONE
        customer_spinner_fragment_invoice_report.visibility = View.GONE
        edit_text_sale_report_from_date.visibility = View.GONE
        edit_text_sale_report_to_date.visibility = View.GONE
        txt_view_from_date.visibility = View.GONE
        txt_view_to_date.visibility = View.GONE
        btn_sale_report_search.visibility = View.GONE
        btn_sale_report_clear.visibility = View.GONE
        // //sale exchange tab2 report list
        saleInvoiceReportViewModel.saleInvoiceReportList.observe(this, Observer {
            saleInvoiceReportAdapter.setNewList(it as ArrayList<SaleInvoiceReport>)
        })

        saleInvoceReports.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = saleInvoiceReportAdapter
        }
        saleInvoiceReportViewModel.loadSaleExchangeTab2List()

        //sale exchange tab2 report detail list
        saleInvoiceReportViewModel.saleInvoiceDetailReportSuccessState.observe(this, Observer {
            saleInvoiceDetailReportAdapter.setNewList(it as ArrayList<SaleInvoiceDetailReport>)
        })
    }

    private fun onClickItem(invoiceId: String) {
        //layout inflate for sale exchange tab2 report detail
        val dialogBoxView = activity!!.layoutInflater.inflate(R.layout.dialog_box_sale_invoice_report, null)
        dialogBoxView.rl_reprint.visibility = View.GONE
        val builder = AlertDialog.Builder(activity)
        builder.setView(dialogBoxView)
        builder.setTitle("Sales Product")
        builder.setPositiveButton("OK", null)
        builder.setCancelable(false)
        val dialog = builder.create()

        dialogBoxView.saleInvoiceDialog.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = saleInvoiceDetailReportAdapter
        }
        saleInvoiceReportViewModel.loadSaleInvoiceDetailReport(invoiceId = invoiceId)
        dialog.show()

    }
}
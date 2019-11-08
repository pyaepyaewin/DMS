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
import com.aceplus.dms.ui.activities.PrintInvoiceActivity
import com.aceplus.dms.ui.adapters.report.SalesReturnDetailReportAdapter
import com.aceplus.dms.ui.adapters.report.SalesReturnReportAdapter
import com.aceplus.dms.viewmodel.report.ReportViewModel
import com.aceplus.domain.entity.invoice.Invoice
import com.aceplus.domain.vo.report.SaleInvoiceDetailReport
import com.aceplus.domain.vo.report.SalesReturnDetailReport
import com.aceplus.domain.vo.report.SalesReturnReport
import com.aceplus.shared.ui.activities.BaseFragment
import kotlinx.android.synthetic.main.dialog_box_sale_invoice_report.view.*
import kotlinx.android.synthetic.main.dialog_box_sale_return_detail.view.*
import kotlinx.android.synthetic.main.fragment_sale_return_report.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein
import java.util.*

class SalesReturnReportFragment : BaseFragment(), KodeinAware {
    override val kodein: Kodein by kodein()
    private var invoice: Invoice? = null
    var saleReturnDetailList: List<SalesReturnDetailReport> = listOf()
    private val salesReturnReportAdapter: SalesReturnReportAdapter by lazy {
        SalesReturnReportAdapter(
            this::onClickItem
        )
    }
    private val salesReturnDetailReportAdapter: SalesReturnDetailReportAdapter by lazy { SalesReturnDetailReportAdapter() }

    private val salesReturnReportViewModel: ReportViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sale_return_report, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //sale return report list
        salesReturnReportViewModel.salesReturnReportSuccessState.observe(this, Observer {
            salesReturnReportAdapter.setNewList(it as ArrayList<SalesReturnReport>)
        })

        salesReturnReportViewModel.reportErrorState.observe(this, Observer {
            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
        })
        saleReturnReports.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = salesReturnReportAdapter
        }
        salesReturnReportViewModel.loadSalesReturnReport()

        //sale return detail report list
        salesReturnReportViewModel.salesReturnDetailReportSuccessState.observe(this, Observer {
            salesReturnDetailReportAdapter.setNewList(it as ArrayList<SalesReturnDetailReport>)
            saleReturnDetailList = it
        })
        salesReturnReportViewModel.saleHistoryForPrintData.observe(this, Observer {
            invoice = it
        })
    }

    private fun onClickItem(invoiceId: String) {
        val dialogBoxView =
            activity!!.layoutInflater.inflate(
                R.layout.dialog_box_sale_return_detail,
                null
            )
        val builder = AlertDialog.Builder(activity)
        builder.setView(dialogBoxView)
        builder.setCancelable(false)
        val dialog = builder.create()

        dialogBoxView.saleReturnReportDetail.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = salesReturnDetailReportAdapter
        }
        salesReturnReportViewModel.loadSalesReturnDetailReport(invoiceId = invoiceId)

        //Action of dialog button
        dialogBoxView.btn_print.setOnClickListener {
            val intent = PrintInvoiceActivity.newIntentFromSaleHistoryActivity(
                context!!,
                invoice,
                saleReturnDetailList as List<SaleInvoiceDetailReport>
            )
            startActivity(intent)
            Toast.makeText(activity, "Continue to add", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        dialogBoxView.btn_ok.setOnClickListener {
            dialog.dismiss()
        }


    }
}
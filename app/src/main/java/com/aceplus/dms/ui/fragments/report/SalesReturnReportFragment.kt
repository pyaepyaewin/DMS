package com.aceplus.dms.ui.fragments.report

import android.app.Dialog
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.Toast
import com.aceplus.dms.R
import com.aceplus.dms.ui.adapters.report.SalesReturnDetailReportAdapter
import com.aceplus.dms.ui.adapters.report.SalesReturnReportAdapter
import com.aceplus.dms.viewmodel.report.ReportViewModel
import com.aceplus.domain.vo.report.SalesReturnDetailReport
import com.aceplus.domain.vo.report.SalesReturnReport
import com.aceplus.shared.ui.activities.BaseFragment
import kotlinx.android.synthetic.main.dialog_box_sale_return_detail.*
import kotlinx.android.synthetic.main.fragment_sale_return_report.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein
import java.util.*

class SalesReturnReportFragment : BaseFragment(), KodeinAware {
    override val kodein: Kodein by kodein()
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
    }

    private fun onClickItem(invoiceId: String) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setContentView(R.layout.dialog_box_sale_return_detail)

        salesReturnReportViewModel.salesReturnDetailReportSuccessState.observe(this, Observer {
            salesReturnDetailReportAdapter.setNewList(it as ArrayList<SalesReturnDetailReport>)
        })

        salesReturnReportViewModel.reportErrorState.observe(this, Observer {
            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
        })
        saleReturnReportDetail.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = salesReturnDetailReportAdapter
        }
        salesReturnReportViewModel.loadSalesReturnDetailReport(invoiceId = invoiceId)

        //Action of dialog button
        dialog.findViewById<Button>(R.id.btn_print).setOnClickListener {
            dialog.dismiss()
        }
        dialog.findViewById<Button>(R.id.btn_ok).setOnClickListener {
            dialog.dismiss()
        }


    }
}
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
import com.aceplus.dms.ui.adapters.report.PreOrderDetailReportAdapter
import com.aceplus.dms.ui.adapters.report.PreOrderReportAdapter
import com.aceplus.dms.viewmodel.report.ReportViewModel
import com.aceplus.domain.vo.report.PreOrderDetailReport
import com.aceplus.domain.vo.report.PreOrderReport
import com.aceplus.shared.ui.activities.BaseFragment
import kotlinx.android.synthetic.main.fragment_pre_order_report.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein
import java.util.*

class PreOrderReportFragment : BaseFragment(), KodeinAware {
    override val kodein: Kodein by kodein()
    private val preOrderReportAdapter: PreOrderReportAdapter by lazy { PreOrderReportAdapter(this::onClickItem) }
    private val preOrderDetailReportAdapter: PreOrderDetailReportAdapter by lazy { PreOrderDetailReportAdapter() }

    private val preOrderReportViewModel: ReportViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pre_order_report, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
           preOrderReportViewModel.preOrderReportSuccessState.observe(this, Observer {
            preOrderReportAdapter.setNewList(it as ArrayList<PreOrderReport>)
        })

        preOrderReportViewModel.reportErrorState.observe(this, Observer {
            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
        })
        preOrderReports.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = preOrderReportAdapter
        }
        preOrderReportViewModel.loadPreOrderReport()
    }

    private fun onClickItem(invoiceId: String) {
        val preOrderProducts = view!!.findViewById(R.id.preOrderProducts) as RecyclerView
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setContentView(R.layout.dialog_box_pre_order_products)
        preOrderReportViewModel.preOrderDetailReportSuccessState.observe(this, Observer {
            preOrderDetailReportAdapter.setNewList(it as ArrayList<PreOrderDetailReport>)
        })

        preOrderReportViewModel.reportErrorState.observe(this, Observer {
            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
        })
        preOrderProducts.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = preOrderDetailReportAdapter
        }
        preOrderReportViewModel.loadPreOrderDetailReport(invoiceId = invoiceId)

        //Action of dialog button
        dialog.findViewById<Button>(R.id.btn_print).setOnClickListener {
            dialog.dismiss()
        }
        dialog.findViewById<Button>(R.id.btn_ok).setOnClickListener {
            dialog.dismiss()
        }

    }

}
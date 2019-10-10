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
import com.aceplus.dms.ui.adapters.report.DeliverDetailReportAdapter
import com.aceplus.dms.ui.adapters.report.DeliveryReportAdapter
import com.aceplus.dms.viewmodel.report.ReportViewModel
import com.aceplus.domain.vo.report.DeliverDetailReport
import com.aceplus.domain.vo.report.DeliverReport
import com.aceplus.shared.ui.activities.BaseFragment
import kotlinx.android.synthetic.main.dialog_box_deliveryinvoice_report.*
import kotlinx.android.synthetic.main.fragment_deliveryinvoice_report.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein
import java.util.*

class DeliverReportFragment : BaseFragment(), KodeinAware {
    override val kodein: Kodein by kodein()
    private val deliveryReportAdapter: DeliveryReportAdapter by lazy { DeliveryReportAdapter(this::onClickItem) }
    private val deliverDetailReportAdapter: DeliverDetailReportAdapter by lazy { DeliverDetailReportAdapter() }

    private val deliverReportViewModel: ReportViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_deliveryinvoice_report, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        deliverReportViewModel.deliverReportSuccessState.observe(this, Observer {
            deliveryReportAdapter.setNewList(it as ArrayList<DeliverReport>)
        })

        deliverReportViewModel.reportErrorState.observe(this, Observer {
            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
        })
        deliveryReportRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = deliveryReportAdapter
        }
        deliverReportViewModel.loadDeliverReport()
    }

    private fun onClickItem(invoiceId: String) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setContentView(R.layout.dialog_box_deliveryinvoice_report)

        deliverReportViewModel.deliverDetailReportSuccessState.observe(this, Observer {
            deliverDetailReportAdapter.setNewList(it as ArrayList<DeliverDetailReport>)
        })

        deliverReportViewModel.reportErrorState.observe(this, Observer {
            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
        })
        deliveryInvoiceDetailRecycleView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = deliverDetailReportAdapter
        }
        deliverReportViewModel.loadDeliverDetailReport(invoiceId = invoiceId)

        //Action of dialog button
        dialog.findViewById<Button>(R.id.btn_print).setOnClickListener {
            dialog.dismiss()
        }
        dialog.findViewById<Button>(R.id.btn_ok).setOnClickListener {
            dialog.dismiss()
        }

    }


}
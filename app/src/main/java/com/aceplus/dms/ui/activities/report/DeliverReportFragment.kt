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
import android.widget.Button
import android.widget.Toast
import com.aceplus.dms.R
import com.aceplus.dms.ui.adapters.report.DeliverDetailReportAdapter
import com.aceplus.dms.ui.adapters.report.DeliveryReportAdapter
import com.aceplus.dms.viewmodel.report.DeliverReportViewModel
import com.aceplus.domain.model.report.DeliverDetailReport
import com.aceplus.domain.model.report.DeliverReport
import com.aceplus.shared.ui.activities.BaseFragment
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein
import java.util.*

class DeliverReportFragment : BaseFragment(), KodeinAware {
    override val kodein: Kodein by kodein()
    lateinit var deliveryReportRecyclerView: RecyclerView
    lateinit var deliveryInvoiceDetailRecycleView: RecyclerView
    private val deliveryReportAdapter: DeliveryReportAdapter by lazy { DeliveryReportAdapter(this::onClickItem) }
    private val deliverDetailReportAdapter: DeliverDetailReportAdapter by lazy { DeliverDetailReportAdapter() }

    private val deliverReportViewModel: DeliverReportViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_deliveryinvoice_report, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        deliveryReportRecyclerView =
            view.findViewById(R.id.deliveryReportRecyclerView) as RecyclerView

        deliverReportViewModel.deliverReportSuccessState.observe(this, Observer {
            deliveryReportAdapter.setNewList(it as ArrayList<DeliverReport>)
        })

        deliverReportViewModel.deliverReportErrorState.observe(this, Observer {
            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
        })
        deliveryReportRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = deliveryReportAdapter
        }
        deliverReportViewModel.deliverReport()
    }

    private fun onClickItem(invoiceId: String) {
        deliveryInvoiceDetailRecycleView =
            view!!.findViewById(R.id.deliveryInvoiceDetailRecycleView)
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setContentView(R.layout.dialog_box_deliveryinvoice_report)

        deliverReportViewModel.deliverDetailReportSuccessState.observe(this, Observer {
            deliverDetailReportAdapter.setNewList(it as ArrayList<DeliverDetailReport>)
        })

        deliverReportViewModel.deliverReportErrorState.observe(this, Observer {
            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
        })
        deliveryInvoiceDetailRecycleView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = deliverDetailReportAdapter
        }
        deliverReportViewModel.deliverDetailReport(invoiceId = invoiceId)

        //Action of dialog button
        dialog.findViewById<Button>(R.id.btn_print).setOnClickListener {
            dialog.dismiss()
        }
        dialog.findViewById<Button>(R.id.btn_ok).setOnClickListener {
            dialog.dismiss()
        }

    }


}
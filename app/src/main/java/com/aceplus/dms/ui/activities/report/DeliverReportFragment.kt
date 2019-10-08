package com.aceplus.dms.ui.activities.report

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.aceplus.dms.R
import com.aceplus.dms.ui.adapters.report.DeliveryReportAdapter
import com.aceplus.dms.viewmodel.report.DeliverReportViewModel
import com.aceplus.domain.model.report.DeliverReport
import com.aceplus.shared.ui.activities.BaseFragment
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein
import java.util.ArrayList

class DeliverReportFragment : BaseFragment(),KodeinAware{
    override val kodein: Kodein by kodein()
    lateinit var deliveryReportRecyclerView: RecyclerView
    private val deliveryReportAdapter: DeliveryReportAdapter by lazy { DeliveryReportAdapter() }
    private val deliverReportViewModel: DeliverReportViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_deliveryinvoice_report, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        deliveryReportRecyclerView = view.findViewById(R.id.deliveryReportRecyclerView) as RecyclerView

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
}
package com.aceplus.dms.ui.activities.report

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.aceplus.dms.R
import com.aceplus.dms.ui.adapters.report.UnsellReasonReportAdapter
import com.aceplus.dms.viewmodel.report.UnsellReasonReportViewModel
import com.aceplus.domain.model.report.UnsellReasonReport
import com.aceplus.shared.ui.activities.BaseFragment
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.android.support.kodein
import java.util.ArrayList

class UnsellReasonReportFragment : BaseFragment(),KodeinAware {
    override val kodein: Kodein by kodein()
    lateinit var customerFeedBacks: RecyclerView
    private val unSellReasonReportAdapter: UnsellReasonReportAdapter by lazy { UnsellReasonReportAdapter() }

    private val unSellReasonReportViewModel:UnsellReasonReportViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_customer_feedback_report, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        customerFeedBacks = view!!.findViewById(R.id.customerFeedBacks) as RecyclerView
        unSellReasonReportViewModel.unSellReasonReportSuccessState.observe(this,android.arch.lifecycle.Observer {
            unSellReasonReportAdapter.setNewList(it as ArrayList<UnsellReasonReport>)
        })
        unSellReasonReportViewModel.unSellReasonReportErrorState.observe(this,android.arch.lifecycle.Observer {
            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
        })

        customerFeedBacks.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = unSellReasonReportAdapter
        }
        unSellReasonReportViewModel.unSellReasonReport()
    }
}
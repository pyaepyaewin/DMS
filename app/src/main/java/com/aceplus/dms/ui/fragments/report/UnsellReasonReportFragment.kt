package com.aceplus.dms.ui.fragments.report

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.aceplus.dms.R
import com.aceplus.dms.ui.adapters.report.UnsellReasonReportAdapter
import com.aceplus.dms.viewmodel.report.ReportViewModel
import com.aceplus.domain.vo.report.UnsellReasonReport
import com.aceplus.shared.ui.activities.BaseFragment
import kotlinx.android.synthetic.main.fragment_customer_feedback_report.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein
import java.util.*

class UnsellReasonReportFragment : BaseFragment(), KodeinAware {
    override val kodein: Kodein by kodein()
    private val unSellReasonReportAdapter: UnsellReasonReportAdapter by lazy { UnsellReasonReportAdapter() }

    private val unSellReasonReportViewModel: ReportViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_customer_feedback_report, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //unSell reason report list
        unSellReasonReportViewModel.unSellReasonReportSuccessState.observe(
            this,
            android.arch.lifecycle.Observer {
                unSellReasonReportAdapter.setNewList(it as ArrayList<UnsellReasonReport>)
            })
        unSellReasonReportViewModel.reportErrorState.observe(this, android.arch.lifecycle.Observer {
            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
        })

        customerFeedBacks.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = unSellReasonReportAdapter
        }
        unSellReasonReportViewModel.loadUnSellReasonReport()
    }
}
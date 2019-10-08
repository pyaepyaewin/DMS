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
import com.aceplus.dms.ui.adapters.report.PreOrderReportAdapter
import com.aceplus.dms.viewmodel.report.PreOrderReportViewModel
import com.aceplus.domain.model.report.PreOrderReport
import com.aceplus.shared.ui.activities.BaseFragment
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein
import java.util.ArrayList

class PreOrderReportFragment : BaseFragment(),KodeinAware {
    override val kodein: Kodein by kodein()
    lateinit var preOrderReports: RecyclerView
    private val preOrderReportAdapter: PreOrderReportAdapter by lazy { PreOrderReportAdapter() }
    private val preOrderReportViewModel: PreOrderReportViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pre_order_report, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        preOrderReports = view.findViewById(R.id.preOrderReports) as RecyclerView

        preOrderReportViewModel.preOrderReportSuccessState.observe(this, Observer {
            preOrderReportAdapter.setNewList(it as ArrayList<PreOrderReport>)
        })

        preOrderReportViewModel.preOrderReportErrorState.observe(this, Observer {
            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
        })
        preOrderReports.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = preOrderReportAdapter
        }
        preOrderReportViewModel.preOrderReport()
    }

}
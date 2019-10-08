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
import com.aceplus.dms.ui.adapters.report.SalesReturnReportAdapter
import com.aceplus.dms.viewmodel.report.SalesReturnReportViewModel
import com.aceplus.domain.model.report.SalesReturnReport
import com.aceplus.shared.ui.activities.BaseFragment
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.android.support.kodein
import java.util.ArrayList
class SalesReturnReportFragment : BaseFragment(),KodeinAware {
    override val kodein: Kodein by kodein()

    lateinit var saleReturnReports: RecyclerView
    private val salesReturnReportAdapter: SalesReturnReportAdapter by lazy { SalesReturnReportAdapter() }
    private val salesReturnReportViewModel: SalesReturnReportViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sale_return_report, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        saleReturnReports = view.findViewById(R.id.saleReturnReports) as RecyclerView

        salesReturnReportViewModel.salesReturnReportSuccessState.observe(this, Observer {
            salesReturnReportAdapter.setNewList(it as ArrayList<SalesReturnReport>)
        })

        salesReturnReportViewModel.salesReturnReportErrorState.observe(this, Observer {
            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
        })
        saleReturnReports.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = salesReturnReportAdapter
        }
        salesReturnReportViewModel.salesReturnReport()
    }
}
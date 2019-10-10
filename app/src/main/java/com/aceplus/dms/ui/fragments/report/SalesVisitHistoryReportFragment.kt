package com.aceplus.dms.ui.fragments.report

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.aceplus.dms.R
import com.aceplus.dms.ui.adapters.report.SalesVisitHistoryReportAdapter
import com.aceplus.dms.viewmodel.report.ReportViewModel
import com.aceplus.domain.vo.report.SalesVisitHistoryReport
import com.aceplus.shared.ui.activities.BaseFragment
import kotlinx.android.synthetic.main.fragment_sale_visit_report.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein
import java.util.*

class SalesVisitHistoryReportFragment : BaseFragment(), KodeinAware {
    override val kodein: Kodein by kodein()
    var customerNameList = mutableListOf<String>()
    private val salesVisitHistoryReportAdapter: SalesVisitHistoryReportAdapter by lazy { SalesVisitHistoryReportAdapter() }
    private val salesVisitHistoryReportViewModel: ReportViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sale_visit_report, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        salesVisitHistoryReportViewModel.salesVisitHistoryReportSuccessState.observe(
            this,
            Observer {
                salesVisitHistoryReportAdapter.setNewList(it as ArrayList<SalesVisitHistoryReport>)
            })

        salesVisitHistoryReportViewModel.reportErrorState.observe(this, Observer {
            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
        })

        //select customer name list in db
        salesVisitHistoryReportViewModel.customerDataList.observe(this, Observer {
            if (it != null) {
                for (customer in it) {
                    customerNameList.add(customer.customer_name.toString())
                }

            }
            //Add customer name in spinner in this fragment
             val customerNameSpinnerAdapter =
                ArrayAdapter(context, android.R.layout.simple_spinner_item, customerNameList)
            customerNameSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            fragment_sale_visit_spinner_from_customer.adapter = customerNameSpinnerAdapter
            fragment_sale_visit_spinner_to_customer.adapter = customerNameSpinnerAdapter

        })
        saleVisitList.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = salesVisitHistoryReportAdapter
        }
        salesVisitHistoryReportViewModel.loadSalesVisitHistoryReport()
        salesVisitHistoryReportViewModel.loadCustomer()
    }

}
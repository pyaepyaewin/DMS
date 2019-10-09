package com.aceplus.dms.ui.activities.report

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
import com.aceplus.dms.viewmodel.report.SaleInvoiceReportViewModel
import com.aceplus.dms.viewmodel.report.SalesVisitHistoryReportViewModel
import com.aceplus.domain.model.report.SalesVisitHistoryReport
import com.aceplus.shared.ui.activities.BaseFragment
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein
import java.util.*

class SalesVisitHistoryReportFragment : BaseFragment(), KodeinAware {
    override val kodein: Kodein by kodein()
    lateinit var saleVisitList: RecyclerView
    var customerNameList = mutableListOf<String>()
    private val salesVisitHistoryReportAdapter: SalesVisitHistoryReportAdapter by lazy { SalesVisitHistoryReportAdapter() }
    private val salesVisitHistoryReportViewModel: SalesVisitHistoryReportViewModel by viewModel()
    private val saleInvoiceReportViewModel: SaleInvoiceReportViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sale_visit_report, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        saleVisitList = view.findViewById(R.id.saleVisitList) as RecyclerView

        salesVisitHistoryReportViewModel.salesVisitHistoryReportSuccessState.observe(
            this,
            Observer {
                salesVisitHistoryReportAdapter.setNewList(it as ArrayList<SalesVisitHistoryReport>)
            })

        salesVisitHistoryReportViewModel.salesVisitHistoryReportErrorState.observe(this, Observer {
            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
        })

        //select customer name list in db
        saleInvoiceReportViewModel.customerDataList.observe(this, Observer {
            if (it != null) {
                for (customer in it) {
                    customerNameList.add(customer.customer_name.toString())
                }

            }
            //Add customer name in spinner in this fragment
            val fSpinner =
                view.findViewById<Spinner>(R.id.fragment_sale_visit_spinner_from_customer)
            val tSpinner = view.findViewById<Spinner>(R.id.fragment_sale_visit_spinner_to_customer)
            val customerNameSpinnerAdapter =
                ArrayAdapter(context, android.R.layout.simple_spinner_item, customerNameList)
            customerNameSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            fSpinner.adapter = customerNameSpinnerAdapter
            tSpinner.adapter = customerNameSpinnerAdapter

        })
        saleVisitList.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = salesVisitHistoryReportAdapter
        }
        salesVisitHistoryReportViewModel.salesVisitHistoryReport()
        saleInvoiceReportViewModel.loadCustomer()
    }

}
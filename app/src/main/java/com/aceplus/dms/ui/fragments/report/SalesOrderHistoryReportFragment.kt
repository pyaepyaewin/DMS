package com.aceplus.dms.ui.fragments.report

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.aceplus.dms.R
import com.aceplus.dms.ui.adapters.report.SalesOrderHistoryReportAdapter
import com.aceplus.dms.viewmodel.report.ReportViewModel
import com.aceplus.domain.vo.report.SalesOrderHistoryReport
import com.aceplus.shared.ui.activities.BaseFragment
import kotlinx.android.synthetic.main.fragment_sale_invoice_report.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein
import java.util.*

class SalesOrderHistoryReportFragment : BaseFragment(), KodeinAware {
    override val kodein: Kodein by kodein()
    var totalAmount = 0.0
    var discount = 0
    var netAmount = 0.0
    var advancePaymentAmount = 0.0
    var customerNameList = mutableListOf<String>()
    var allItems: MutableList<SalesOrderHistoryReport> = mutableListOf()
    private val salesOrderHistoryReportAdapter: SalesOrderHistoryReportAdapter by lazy { SalesOrderHistoryReportAdapter() }
    private val salesOrderHistoryReportViewModel: ReportViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.fragment_sale_invoice_report, container, false)
        table_row_advance_amt.visibility = View.VISIBLE
        sale_order_report_advanced_amount_label.setVisibility(View.VISIBLE)
        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var saleInvoiceReports = view.findViewById(R.id.saleInvoceReports) as RecyclerView
        salesOrderHistoryReportViewModel.salesOrderHistoryReportSuccessState.observe(
            this,
            Observer {
                salesOrderHistoryReportAdapter.setNewList(it as ArrayList<SalesOrderHistoryReport>)
                allItems = it
            })

        salesOrderHistoryReportViewModel.reportErrorState.observe(this, Observer {
            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
        })

        salesOrderHistoryReportViewModel.customerDataList.observe(this, Observer {
            if (it != null) {
                customerNameList.add("All")
                for (customer in it) {
                    customerNameList.add(customer.customer_name.toString())
                }
            }
            //Bind customer name in fragment spinner
            val customerNameSpinnerAdapter =
                ArrayAdapter(context, android.R.layout.simple_spinner_item, customerNameList)
            customerNameSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            customer_spinner_fragment_invoice_report.adapter = customerNameSpinnerAdapter
            customer_spinner_fragment_invoice_report.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        val filterList: ArrayList<SalesOrderHistoryReport> = ArrayList()
                        for (c in allItems) {
                            if (c.customerName == customerNameList[p2]) {
                                filterList.add(c)
                            }
                            if (c.customerName == "All") {
                                filterList.add(c)
                            }
                        }
                        salesOrderHistoryReportAdapter.setNewList(filterList)
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {

                    }

                }

        })

        //Calculate total,discount and net amounts
        for (i in allItems) {
            totalAmount += i.totalAmount.toDouble()
            discount += i.discount.toInt()
            netAmount += i.netAmount.toDouble()
            advancePaymentAmount += i.advancePaymentAmount.toDouble()
        }
        sale_report_total_amt.text = totalAmount.toString()
        sale_report_discount.text = discount.toString()
        sale_report_net_amt.text = netAmount.toString()

        saleInvoiceReports.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = salesOrderHistoryReportAdapter
        }
        salesOrderHistoryReportViewModel.loadSalesOrderHistoryReport()
        salesOrderHistoryReportViewModel.loadCustomer()
    }

}
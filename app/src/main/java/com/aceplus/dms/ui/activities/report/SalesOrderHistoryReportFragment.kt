package com.aceplus.dms.ui.activities.report

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.aceplus.dms.R
import com.aceplus.dms.ui.adapters.report.SalesOrderHistoryReportAdapter
import com.aceplus.dms.viewmodel.report.SaleInvoiceReportViewModel
import com.aceplus.dms.viewmodel.report.SalesOrderHistoryReportViewModel
import com.aceplus.domain.model.report.SalesOrderHistoryReport
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
    lateinit var saleInvoceReports: RecyclerView
    var customerNameList = mutableListOf<String>()
    var allItems: MutableList<SalesOrderHistoryReport> = mutableListOf()
    private val salesOrderHistoryReportAdapter: SalesOrderHistoryReportAdapter by lazy { SalesOrderHistoryReportAdapter() }
    private val saleInvoiceReportViewModel: SaleInvoiceReportViewModel by viewModel()
    private val salesOrderHistoryReportViewModel: SalesOrderHistoryReportViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.fragment_sale_invoice_report, container, false)

        saleInvoceReports = view.findViewById(R.id.saleInvoceReports) as RecyclerView
        val customerSpinner =
            view.findViewById(R.id.customer_spinner_fragment_invoice_report) as Spinner
        val fromDateEditTxt = view.findViewById(R.id.edit_text_sale_report_from_date) as EditText
        val toDateEditTxt = view.findViewById(R.id.edit_text_sale_report_to_date) as EditText
        val searchBtn = view.findViewById(R.id.btn_sale_report_search) as Button
        val clearBtn = view.findViewById(R.id.btn_sale_report_clear) as Button
        val sale_order_history_report_total =
            view.findViewById(R.id.sale_report_total_amt) as TextView
        val sale_order_history_report_discount =
            view.findViewById(R.id.sale_report_discount) as TextView
        val sale_order_history_report_net = view.findViewById(R.id.sale_report_net_amt) as TextView
        val sale_report_discount_advance_amt_label =
            view.findViewById(R.id.sale_order_report_advanced_amount_label) as TextView
        val table_row_advance_amt = view.findViewById(R.id.table_row_advance_amt) as TableRow
        val sale_order_report_advance_amt =
            view.findViewById(R.id.sale_order_report_advanced_amt) as TextView

        table_row_advance_amt.visibility = View.VISIBLE
        sale_report_discount_advance_amt_label.setVisibility(View.VISIBLE)
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

        salesOrderHistoryReportViewModel.salesOrderHistoryReportErrorState.observe(this, Observer {
            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
        })

        saleInvoiceReportViewModel.customerDataList.observe(this, Observer {
            if (it != null) {
                customerNameList.add("All")
                for (customer in it) {
                    customerNameList.add(customer.customer_name.toString())
                }

            }
            //Bind customer name in fragment spinner
            val spinner = view.findViewById<Spinner>(R.id.customer_spinner_fragment_invoice_report)
            val customerNameSpinnerAdapter =
                ArrayAdapter(context, android.R.layout.simple_spinner_item, customerNameList)
            customerNameSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = customerNameSpinnerAdapter
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    val filterList: ArrayList<SalesOrderHistoryReport> = ArrayList()
                    for (c in allItems) {
                        if (c.customer_name == customerNameList[p2]) {
                            filterList.add(c)
                        }
                        if (c.customer_name == "All") {
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
            totalAmount += i.total_amount.toDouble()
            discount += i.discount.toInt()
            netAmount += i.net_amount.toDouble()
        }
        sale_report_total_amt.text = totalAmount.toString()
        sale_report_discount.text = discount.toString()
        sale_report_net_amt.text = netAmount.toString()

        saleInvoiceReports.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = salesOrderHistoryReportAdapter
        }
        salesOrderHistoryReportViewModel.salesOrderHistoryReport()
        saleInvoiceReportViewModel.loadCustomer()
    }

}
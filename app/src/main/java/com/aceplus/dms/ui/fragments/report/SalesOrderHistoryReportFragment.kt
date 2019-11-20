package com.aceplus.dms.ui.fragments.report

import android.app.DatePickerDialog
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
import com.aceplus.domain.vo.report.SalesOrderHistoryFullDataReport
import com.aceplus.domain.vo.report.SalesOrderHistoryReport
import com.aceplus.shared.ui.activities.BaseFragment
import kotlinx.android.synthetic.main.fragment_sale_invoice_report.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein
import java.text.SimpleDateFormat
import java.util.*

class SalesOrderHistoryReportFragment : BaseFragment(), KodeinAware {
    override val kodein: Kodein by kodein()
    private var myCalendar = Calendar.getInstance()
    private lateinit var fromDate: Date
    private lateinit var toDate: Date
    private val salesOrderHistoryReportAdapter: SalesOrderHistoryReportAdapter by lazy { SalesOrderHistoryReportAdapter() }
    private val salesOrderHistoryReportViewModel: ReportViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_sale_invoice_report, container, false)
        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var customerNameList = mutableListOf<String>()
        edit_text_sale_report_from_date.setOnClickListener {
            chooseDob(1)
        }
        edit_text_sale_report_to_date.setOnClickListener {
            chooseDob(2)
        }
        btn_sale_report_search.setOnClickListener { }
        btn_sale_report_clear.setOnClickListener {
            edit_text_sale_report_from_date.setText("")
            edit_text_sale_report_from_date.error = null
            edit_text_sale_report_to_date.setText("")
            edit_text_sale_report_to_date.error = null
        }
        table_row_advance_amt.visibility = View.VISIBLE
        sale_order_report_advanced_amount_label.visibility = View.VISIBLE
        var saleInvoiceReports = view.findViewById(R.id.saleInvoceReports) as RecyclerView
        //sale order history list and customer list
        salesOrderHistoryReportViewModel.salesOrderHistoryReportSuccessState.observe(
            this,
            Observer {
                val saleOrderHistoryReportList = arrayListOf<SalesOrderHistoryReport>()
                for (i in it!!.first){
                    val netAmunt = i.netAmount.toDouble() - i.advancePaymentAmount.toDouble() - i.discount.toDouble()
                    val saleOrderHistoryReport = SalesOrderHistoryReport(i.invoiceId,i.customerName,i.address,i.netAmount,i.advancePaymentAmount,i.discount,netAmunt.toString())
                    saleOrderHistoryReportList.add(saleOrderHistoryReport)
                }
                salesOrderHistoryReportAdapter.setNewList(saleOrderHistoryReportList)

                //select customer name list in db
                if (it!!.second != null) {
                    customerNameList.add("All")
                    for (customer in it!!.second) {
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
                        override fun onItemSelected(
                            p0: AdapterView<*>?,
                            p1: View?,
                            p2: Int,
                            p3: Long
                        ) {
                            val filterList: ArrayList<SalesOrderHistoryReport> = ArrayList()
                            for (c in saleOrderHistoryReportList) {
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
                //Calculate and setText for total,discount and net amounts
                calculateAmount(it!!.first)

            })

        salesOrderHistoryReportViewModel.reportErrorState.observe(this, Observer {
            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
        })

        saleInvoiceReports.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = salesOrderHistoryReportAdapter
        }
        salesOrderHistoryReportViewModel.loadSalesOrderHistoryReport()
    }

    private fun calculateAmount(allItems: List<SalesOrderHistoryFullDataReport>) {
        var totalAmount = 0.0
        var discount = 0
        var netAmount = 0.0
        var advancePaymentAmount = 0.0
        //Calculate total,discount and net amounts
        for (i in allItems) {
            totalAmount += i.netAmount.toDouble()
            discount += i.discount.toInt()
            advancePaymentAmount += i.advancePaymentAmount.toDouble()
            val netAddAmount = totalAmount - advancePaymentAmount - discount
            netAmount += netAddAmount
        }
        sale_report_total_amt.text = totalAmount.toString()
        sale_report_discount.text = discount.toString()
        sale_report_net_amt.text = netAmount.toString()
    }

    private fun chooseDob(choice: Int) {
        val sdf = SimpleDateFormat("yyyy/MM/dd")
        val datePicker =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, monthOfYear)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                if (choice == 1) {
                    edit_text_sale_report_from_date.setText(sdf.format(myCalendar.time))
                    fromDate = myCalendar.time
                } else if (choice == 2) {
                    edit_text_sale_report_to_date.setText(sdf.format(myCalendar.time))
                    toDate = myCalendar.time
                }
            }
        val dateDialog = DatePickerDialog(
            this.context!!,
            datePicker,
            myCalendar.get(Calendar.YEAR),
            myCalendar.get(Calendar.MONTH),
            myCalendar.get(Calendar.DAY_OF_MONTH)
        )
        dateDialog.show()
    }

}
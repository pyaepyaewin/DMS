package com.aceplus.dms.ui.fragments.report

import android.app.AlertDialog
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
import com.aceplus.dms.R
import com.aceplus.dms.ui.adapters.report.PreOrderDetailReportAdapter
import com.aceplus.dms.ui.adapters.report.SalesOrderHistoryReportAdapter
import com.aceplus.dms.viewmodel.report.ReportViewModel
import com.aceplus.domain.vo.report.PreOrderDetailReport
import com.aceplus.domain.vo.report.SalesOrderHistoryReport
import com.aceplus.shared.ui.activities.BaseFragment
import kotlinx.android.synthetic.main.dialog_box_pre_order_products.view.*
import kotlinx.android.synthetic.main.fragment_sale_invoice_report.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class SalesOrderHistoryReportFragment : BaseFragment(), KodeinAware {
    override val kodein: Kodein by kodein()
    private var myCalendar = Calendar.getInstance()
    private var fromDate: String? = null
    private var toDate: String? = null
    var saleOrderHistoryDataList: List<SalesOrderHistoryReport> = listOf()
    private val salesOrderHistoryReportAdapter: SalesOrderHistoryReportAdapter by lazy { SalesOrderHistoryReportAdapter(this::onClickItem) }
    private val preOrderDetailReportAdapter: PreOrderDetailReportAdapter by lazy { PreOrderDetailReportAdapter() }
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
        btn_sale_report_search.setOnClickListener {
            when {
                edit_text_sale_report_from_date.text.isEmpty() -> edit_text_sale_report_from_date.error = ""
                edit_text_sale_report_to_date.text.isEmpty() -> edit_text_sale_report_to_date.error = ""
                else -> {
                    salesOrderHistoryReportViewModel.saleOrderHistoryDateFilterList.observe(this, Observer {
                        val saleOrderHistoryReportList = arrayListOf<SalesOrderHistoryReport>()
                        for (i in it!!) {
                            val netAmount = i.netAmount.toDouble() - i.advancePaymentAmount.toDouble() - i.discount.toDouble()
                            val saleOrderHistoryReport = SalesOrderHistoryReport(i.invoiceId, i.customerName, i.address, i.netAmount, i.discount, i.advancePaymentAmount, netAmount.toString())
                            saleOrderHistoryReportList.add(saleOrderHistoryReport)
                        }
                        salesOrderHistoryReportAdapter.setNewList(saleOrderHistoryReportList)
                        //Calculate and setText for total,discount and net amounts
                        calculateAmount(saleOrderHistoryDataList)
                    })
                    salesOrderHistoryReportViewModel.loadSaleOrderHistoryDateFilterList(fromDate!!, toDate!!)

                }
            }
        }
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
        salesOrderHistoryReportViewModel.salesOrderHistoryReportSuccessState.observe(this, Observer {
                val saleOrderHistoryReportList = arrayListOf<SalesOrderHistoryReport>()
                for (i in it!!) {
                    val netAmount = i.netAmount.toDouble() - i.advancePaymentAmount.toDouble() - i.discount.toDouble()
                    val saleOrderHistoryReport = SalesOrderHistoryReport(i.invoiceId, i.customerName, i.address, i.netAmount, i.discount, i.advancePaymentAmount, netAmount.toString())
                    saleOrderHistoryReportList.add(saleOrderHistoryReport)
                }
                salesOrderHistoryReportAdapter.setNewList(saleOrderHistoryReportList)
            //Calculate and setText for total,discount and net amounts
                calculateAmount(saleOrderHistoryReportList)
        })
        salesOrderHistoryReportViewModel.customerDataList.observe(this, Observer {
            //select customer name list in db
            if (it!! != null) {
                customerNameList.add("All")
                for (customer in it!!) {
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
                        if (customerNameList[p2] != "All") {
                            for (c in saleOrderHistoryDataList) {
                                if (c.customerName == customerNameList[p2]) {
                                    filterList.add(c)
                                }
                            }
                            salesOrderHistoryReportAdapter.setNewList(filterList)
                            calculateAmount(filterList)
                        } else {
                            for (c in saleOrderHistoryDataList) {
                                filterList.add(c)
                            }
                        }
                        salesOrderHistoryReportAdapter.setNewList(filterList)
                        calculateAmount(filterList)
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {

                    }

                }
        })

        saleInvoiceReports.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = salesOrderHistoryReportAdapter
        }
        salesOrderHistoryReportViewModel.loadSalesOrderHistoryReport()
        salesOrderHistoryReportViewModel.loadCustomer()

        //sale order history report detail list
        salesOrderHistoryReportViewModel.preOrderDetailReportSuccessState.observe(this, Observer {
            preOrderDetailReportAdapter.setNewList(it as ArrayList<PreOrderDetailReport>)
        })
    }

    private fun onClickItem(invoiceId: String) {
        //layout inflate for pre order report detail
        val dialogBoxView = activity!!.layoutInflater.inflate(R.layout.dialog_box_pre_order_products, null)
        val builder = AlertDialog.Builder(activity)
        builder.setView(dialogBoxView)
        builder.setTitle("SALE ORDER HISTORY DETAIL")
        builder.setPositiveButton("OK", null)
        builder.setCancelable(false)
        val dialog = builder.create()

        dialogBoxView.preOrderProducts.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = preOrderDetailReportAdapter
        }
        salesOrderHistoryReportViewModel.loadPreOrderDetailReport(invoiceId = invoiceId)
        dialog.show()

    }

    private fun calculateAmount(allItems: List<SalesOrderHistoryReport>) {
        var totalAmount = 0.0
        var discount = 0
        var netAmount = 0.0
        var advancePaymentAmount = 0.0
        //Calculate total,discount and net amounts
        for (i in allItems) {
            totalAmount += i.netAmount.toDouble()
            discount += i.discount.toDouble().roundToInt()
            advancePaymentAmount += i.advancePaymentAmount.toDouble()
            val netAddAmount = totalAmount - advancePaymentAmount - discount
            netAmount += netAddAmount
        }
        sale_report_total_amt.text = totalAmount.toString()
        sale_report_discount.text = discount.toString()
        sale_report_net_amt.text = netAmount.toString()
    }

    private fun chooseDob(choice: Int) {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val datePicker =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, monthOfYear)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                if (choice == 1) {
                    edit_text_sale_report_from_date.setText(sdf.format(myCalendar.time))
                    fromDate = sdf.format(myCalendar.time)
                } else if (choice == 2) {
                    edit_text_sale_report_to_date.setText(sdf.format(myCalendar.time))
                    toDate = sdf.format(myCalendar.time)
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
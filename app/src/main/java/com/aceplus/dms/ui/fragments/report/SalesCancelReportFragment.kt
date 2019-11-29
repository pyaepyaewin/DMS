package com.aceplus.dms.ui.fragments.report

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.aceplus.dms.R
import com.aceplus.dms.ui.activities.PrintInvoiceActivity
import com.aceplus.dms.ui.adapters.report.SaleCancelDetailReportAdapter
import com.aceplus.dms.ui.adapters.report.SalesCancelReportAdapter
import com.aceplus.dms.viewmodel.report.ReportViewModel
import com.aceplus.domain.entity.invoice.Invoice
import com.aceplus.domain.vo.report.SaleCancelInvoiceDetailReport
import com.aceplus.domain.vo.report.SaleInvoiceDetailReport
import com.aceplus.domain.vo.report.SalesCancelReport
import com.aceplus.shared.ui.activities.BaseFragment
import kotlinx.android.synthetic.main.dialog_box_sale_cancel_report.view.*
import kotlinx.android.synthetic.main.fragment_sale_cancel_report.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SalesCancelReportFragment : BaseFragment(), KodeinAware {
    private var myCalendar = Calendar.getInstance()
    private lateinit var fromDate: Date
    private lateinit var toDate: Date
    override val kodein: Kodein by kodein()
    private val salesCancelReportAdapter: SalesCancelReportAdapter by lazy {
        SalesCancelReportAdapter(
            this::onClickItem
        )
    }
    var saleCancelDataList: List<SalesCancelReport> = listOf()
    var saleCancelInvoiceDetailList: List<SaleCancelInvoiceDetailReport> = listOf()
    private var invoice: Invoice? = null
    private val salesCancelReportViewModel: ReportViewModel by viewModel()
    private val saleCancelDetailReportAdapter: SaleCancelDetailReportAdapter by lazy { SaleCancelDetailReportAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sale_cancel_report, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val customerNameList = mutableListOf<String>()
        edit_text_sale_cancel_report_from_date.setOnClickListener {
            chooseDob(1)
        }
        edit_text_sale_cancel_report_to_date.setOnClickListener {
            chooseDob(2)
        }
        btn_sale_cancel_report_search.setOnClickListener {
            //            salesCancelReportViewModel.saleCancelInvoiceReportForDateList.observe(this,android.arch.lifecycle.Observer {
//                salesCancelReportAdapter.setNewList(it as ArrayList<SalesCancelReport>)
//                saleCancelDataList = it
//                calculateAmount(it)
//            })
        }
        btn_sale_cancel_report_clear.setOnClickListener {
            edit_text_sale_cancel_report_from_date.setText("")
            edit_text_sale_cancel_report_from_date.error = null
            edit_text_sale_cancel_report_to_date.setText("")
            edit_text_sale_cancel_report_to_date.error = null
        }
        //sale cancel report list and customer list
        salesCancelReportViewModel.salesCancelReport.observe(this, android.arch.lifecycle.Observer {
            salesCancelReportAdapter.setNewList(it as ArrayList<SalesCancelReport>)
            saleCancelDataList = it
            //Calculate and setText for total,discount and net amounts
            calculateAmount(it)
        })

        salesCancelReportViewModel.customerDataList.observe(this, android.arch.lifecycle.Observer {
            //select customer name list in db
            if (it != null) {
                customerNameList.add("All")
                for (customer in it) {
                    customerNameList.add(customer.customer_name.toString())
                }
            }
            //Bind customer name in spinner
            val customerNameSpinnerAdapter =
                ArrayAdapter(context, android.R.layout.simple_spinner_item, customerNameList)
            customerNameSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            customer_spinner_fragment_invoice_cancel_report.adapter = customerNameSpinnerAdapter
            customer_spinner_fragment_invoice_cancel_report.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        val filterList: ArrayList<SalesCancelReport> = ArrayList()
                        if (customerNameList[p2] != "All") {
                            for (c in saleCancelDataList) {
                                if (c.customerName == customerNameList[p2]) {
                                    filterList.add(c)
                                }
                            }
                            salesCancelReportAdapter.setNewList(filterList)
                            calculateAmount(filterList)
                        } else {
                            for (c in saleCancelDataList) {
                                filterList.add(c)
                            }
                        }
                        salesCancelReportAdapter.setNewList(filterList)
                        calculateAmount(filterList)
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {

                    }

                }
        })

        recyclerSaleCancel.apply {
            layoutManager = LinearLayoutManager(activity!!)
            adapter = salesCancelReportAdapter
        }
        salesCancelReportViewModel.loadSalesCancelReport()
        salesCancelReportViewModel.loadCustomer()
        //salesCancelReportViewModel.loadSaleCancelInvoiceForDateList("$fromDate","$toDate")

        //sale cancel report detail list
        salesCancelReportViewModel.saleCancelDetailReportSuccessState.observe(
            this,
            android.arch.lifecycle.Observer {
                saleCancelDetailReportAdapter.setNewList(it as ArrayList<SaleCancelInvoiceDetailReport>)
                saleCancelInvoiceDetailList = it
            })
    }

    private fun onClickItem(invoiceId: String) {
        val dialogBoxView = activity!!.layoutInflater.inflate(R.layout.dialog_box_sale_cancel_report, null)
         val builder = AlertDialog.Builder(activity)
        builder.setView(dialogBoxView)
        builder.setCancelable(false)
        val dialog = builder.create()

        //Dialog sale history report of invoice detail recycler view........
        dialogBoxView.saleCancelDialog.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = saleCancelDetailReportAdapter
        }
        salesCancelReportViewModel.loadSaleCancelDetailReport(invoiceId = invoiceId)
        //Action of dialog button............
        dialogBoxView.btn_print.visibility = View.GONE
        dialogBoxView.btn_ok.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()

    }

    private fun calculateAmount(allItems: List<SalesCancelReport>) {
        var totalAmount = 0.0
        var discount = 0
        var netAmount = 0.0
        //Calculate total,discount and net amounts
        for (i in allItems) {
            totalAmount += i.totalAmount
            discount += i.totalDiscountAmount.toInt()
            netAmount += ((i.totalAmount) - i.totalDiscountAmount)
        }
        sale_cancel_report_total_amt.text = totalAmount.toString()
        sale_cancel_report_discount.text = discount.toString()
        sale_cancel_report_net_amt.text = netAmount.toString()
    }

    private fun chooseDob(choice: Int) {
        val sdf = SimpleDateFormat("yyyy/MM/dd")
        val datePicker =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, monthOfYear)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                if (choice == 1) {
                    edit_text_sale_cancel_report_from_date.setText(sdf.format(myCalendar.time))
                    fromDate = myCalendar.time
                } else if (choice == 2) {
                    edit_text_sale_cancel_report_to_date.setText(sdf.format(myCalendar.time))
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
package com.aceplus.dms.ui.fragments.report

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.arch.lifecycle.Observer
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
import com.aceplus.dms.ui.adapters.report.SaleInvoiceDetailReportAdapter
import com.aceplus.dms.ui.adapters.report.SaleInvoiceReportAdapter
import com.aceplus.dms.viewmodel.report.ReportViewModel
import com.aceplus.domain.entity.invoice.Invoice
import com.aceplus.domain.vo.SoldProductInfo
import com.aceplus.domain.vo.report.SaleInvoiceDetailReport
import com.aceplus.domain.vo.report.SaleInvoiceReport
import com.aceplus.shared.ui.activities.BaseFragment
import kotlinx.android.synthetic.main.dialog_box_sale_invoice_report.view.*
import kotlinx.android.synthetic.main.fragment_sale_invoice_report.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SaleInvoiceReportFragment : BaseFragment(), KodeinAware {
    private var myCalendar = Calendar.getInstance()
    private var fromDate: String? = null
    private var toDate: String? = null
    override val kodein: Kodein by kodein()
    private var invoice: Invoice? = null
    var saleInvoiceDataList: List<SaleInvoiceReport> = listOf()
    private var saleInvoiceDetailList: List<SaleInvoiceDetailReport> = listOf()
    private val saleInvoiceReportAdapter: SaleInvoiceReportAdapter by lazy {
        SaleInvoiceReportAdapter(
            this::onClickItem
        )
    }
    private val saleInvoiceDetailReportAdapter: SaleInvoiceDetailReportAdapter by lazy { SaleInvoiceDetailReportAdapter() }
    private val saleInvoiceReportViewModel: ReportViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sale_invoice_report, container, false)
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
                    saleInvoiceReportViewModel.saleInvoiceReportForDateList.observe(this, Observer {
                        saleInvoiceReportAdapter.setNewList(it as ArrayList<SaleInvoiceReport>)
                        saleInvoiceDataList = it!!
                        //Calculate and setText for total,discount and net amounts
                        calculateAmount(it)
                    })
                    saleInvoiceReportViewModel.loadHistoryInvoiceForDateList("$fromDate", "$toDate")

                }
            }
        }
        btn_sale_report_clear.setOnClickListener {
            edit_text_sale_report_from_date.setText("")
            edit_text_sale_report_from_date.error = null
            edit_text_sale_report_to_date.setText("")
            edit_text_sale_report_to_date.error = null
        }
        saleInvoiceReportViewModel.saleInvoiceReportList.observe(this, Observer {
            saleInvoiceReportAdapter.setNewList(it as ArrayList<SaleInvoiceReport>)
            saleInvoiceDataList = it
            //Calculate and setText for total,discount and net amounts
            calculateAmount(it)
        })
        saleInvoiceReportViewModel.customerDataList.observe(this, Observer {
            //select customer name list in db
            if (it != null) {
                customerNameList.add("All")
                for (customer in it) {
                    customerNameList.add(customer.customer_name.toString())
                }
            }
            //Add customer name in spinner in this fragment
            val customerNameSpinnerAdapter =
                ArrayAdapter(context, android.R.layout.simple_spinner_item, customerNameList)
            customerNameSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            customer_spinner_fragment_invoice_report.adapter = customerNameSpinnerAdapter
            customer_spinner_fragment_invoice_report.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        val filterList: ArrayList<SaleInvoiceReport> = ArrayList()

                        if (customerNameList[p2] != "All") {
                            for (c in saleInvoiceDataList) {
                                if (c.customerName == customerNameList[p2]) {
                                    filterList.add(c)
                                }
                            }
                            saleInvoiceReportAdapter.setNewList(filterList)
                            calculateAmount(filterList)
                        } else {
                            for (c in saleInvoiceDataList) {
                                filterList.add(c)
                            }
                            saleInvoiceReportAdapter.setNewList(filterList)
                            calculateAmount(filterList)
                        }
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {

                    }

                }
        })

        saleInvoiceReportViewModel.reportErrorState.observe(this, Observer {
            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
        })


        saleInvoceReports.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = saleInvoiceReportAdapter
        }
        saleInvoiceReportViewModel.loadSaleInvoiceList()
        saleInvoiceReportViewModel.loadCustomer()


        //Dialog sale history report of invoice detail recycler view
        saleInvoiceReportViewModel.saleInvoiceDetailReportSuccessState.observe(this, Observer {
            it?.let { detailList ->
                saleInvoiceDetailReportAdapter.setNewList(detailList as ArrayList<SaleInvoiceDetailReport>)
                saleInvoiceDetailList = it
            }
        })
        saleInvoiceReportViewModel.saleHistoryForPrintData.observe(this, Observer {
            invoice = it
        })

    }

    private fun onClickItem(invoiceId: String) {
        val dialogBoxView = activity!!.layoutInflater.inflate(R.layout.dialog_box_sale_invoice_report, null)
        val builder = AlertDialog.Builder(activity)
        builder.setView(dialogBoxView)
        builder.setCancelable(false)
        val dialog = builder.create()

        dialogBoxView.saleInvoiceDialog.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = saleInvoiceDetailReportAdapter
        }
        saleInvoiceReportViewModel.loadSaleInvoiceDetailReport(invoiceId = invoiceId)
        saleInvoiceReportViewModel.loadSaleInvoiceDetailPrint(invoiceId = invoiceId)


        //Action of dialog button
        dialogBoxView.btn_print.setOnClickListener {
            val intent = PrintInvoiceActivity.newIntentFromSaleHistoryActivity(context!!, invoice, saleInvoiceDetailList)
            startActivity(intent)
            dialog.dismiss()

        }
        dialogBoxView.btn_ok.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()

    }

    private fun calculateAmount(allItems: List<SaleInvoiceReport>) {
        var totalAmount = 0.0
        var discount = 0
        var netAmount = 0.0
        for (i in allItems) {
            totalAmount += i.totalAmount.toDouble()
            discount += i.totalDiscountAmount.toInt()
            netAmount += ((i.totalAmount).toDouble() - i.totalDiscountAmount)
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
        val dateDialog = DatePickerDialog(this.context!!, datePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH))
        dateDialog.show()
    }


}
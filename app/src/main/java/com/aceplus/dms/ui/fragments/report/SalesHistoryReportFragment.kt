package com.aceplus.dms.ui.fragments.report

import android.app.Dialog
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import com.aceplus.dms.R
import com.aceplus.dms.ui.adapters.report.SaleInvoiceDetailReportAdapter
import com.aceplus.dms.ui.adapters.report.SaleInvoiceReportAdapter
import com.aceplus.dms.viewmodel.report.ReportViewModel
import com.aceplus.domain.vo.report.SaleInvoiceDetailReport
import com.aceplus.domain.vo.report.SaleInvoiceReport
import com.aceplus.shared.ui.activities.BaseFragment
import kotlinx.android.synthetic.main.dialog_box_sale_invoice_report.*
import kotlinx.android.synthetic.main.fragment_sale_invoice_report.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein
import java.util.*

class SalesHistoryReportFragment : BaseFragment(), KodeinAware {
    override val kodein: Kodein by kodein()
    var totalAmount = 0.0
    var discount = 0
    var netAmount = 0.0
    var allItems: MutableList<SaleInvoiceReport> = mutableListOf()
    var customerNameList = mutableListOf<String>()
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
        saleInvoiceReportViewModel.saleInvoiceReportSuccessState.observe(this, Observer {
            saleInvoiceReportAdapter.setNewList(it as ArrayList<SaleInvoiceReport>)
        })

        saleInvoiceReportViewModel.reportErrorState.observe(this, Observer {
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
            val customerNameSpinnerAdapter =
                ArrayAdapter(context, android.R.layout.simple_spinner_item, customerNameList)
            customerNameSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            customer_spinner_fragment_invoice_report.adapter = customerNameSpinnerAdapter
            customer_spinner_fragment_invoice_report.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        val filterList: ArrayList<SaleInvoiceReport> = ArrayList()
                        for (c in allItems) {
                            if (c.customerName == customerNameList[p2]) {
                                filterList.add(c)
                            }
                            if (c.customerName == "All") {
                                filterList.add(c)
                            }
                        }
                        saleInvoiceReportAdapter.setNewList(filterList)
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                    }
                }
        })
        for (i in allItems) {
            totalAmount += i.totalAmount.toDouble()
            discount += i.totalDiscountAmount.toInt()
            netAmount += ((i.totalAmount).toDouble() - i.totalDiscountAmount)
        }
        sale_report_total_amt.text = totalAmount.toString()
        sale_report_discount.text = discount.toString()
        sale_report_net_amt.text = netAmount.toString()
        saleInvoceReports.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = saleInvoiceReportAdapter
        }
        saleInvoiceReportViewModel.loadSaleInvoiceReport()
        saleInvoiceReportViewModel.loadCustomer()
    }

    private fun onClickItem(invoiceId: String) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setContentView(R.layout.dialog_box_sale_invoice_report)

        //Dialog sale history report of invoice detail recycler view
        saleInvoiceReportViewModel.saleInvoiceDetailReportSuccessState.observe(this, Observer {
            saleInvoiceDetailReportAdapter.setNewList(it as ArrayList<SaleInvoiceDetailReport>)
        })

        saleInvoiceReportViewModel.reportErrorState.observe(this, Observer {
            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
        })
        saleInvoiceDialog.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = saleInvoiceDetailReportAdapter
        }
        saleInvoiceReportViewModel.loadSaleInvoiceDetailReport(invoiceId = invoiceId)

        //Action of dialog button
        dialog.findViewById<Button>(R.id.btn_print).setOnClickListener {
            dialog.dismiss()
        }
        dialog.findViewById<Button>(R.id.btn_ok).setOnClickListener {
            dialog.dismiss()
        }

    }
}
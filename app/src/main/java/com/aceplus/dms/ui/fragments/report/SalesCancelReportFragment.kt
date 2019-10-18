package com.aceplus.dms.ui.fragments.report

import android.app.Dialog
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
import com.aceplus.dms.ui.adapters.report.SaleCancelDetailReportAdapter
import com.aceplus.dms.ui.adapters.report.SalesCancelReportAdapter
import com.aceplus.dms.viewmodel.report.ReportViewModel
import com.aceplus.domain.vo.report.SaleCancelInvoiceDetailReport
import com.aceplus.domain.vo.report.SalesCancelReport
import com.aceplus.shared.ui.activities.BaseFragment
import kotlinx.android.synthetic.main.dialog_box_sale_cancel_report.*
import kotlinx.android.synthetic.main.fragment_sale_cancel_report.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein

class SalesCancelReportFragment : BaseFragment(), KodeinAware {
    override val kodein: Kodein by kodein()
    var totalAmount = 0.0
    var discount = 0
    var netAmount = 0.0
    var customerNameList = mutableListOf<String>()
    var allItems: MutableList<SalesCancelReport> = mutableListOf()
    private val salesCancelReportAdapter: SalesCancelReportAdapter by lazy {
        SalesCancelReportAdapter(
            this::onClickItem
        )
    }
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
        salesCancelReportViewModel.salesCancelReportSuccessState.observe(
            this,
            android.arch.lifecycle.Observer {
                salesCancelReportAdapter.setNewList(it as ArrayList<SalesCancelReport>)
                allItems = it
            })
        salesCancelReportViewModel.reportErrorState.observe(
            this,
            android.arch.lifecycle.Observer {
                Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
            })

        salesCancelReportViewModel.customerDataList.observe(this, android.arch.lifecycle.Observer {
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
            customer_spinner_fragment_invoice_cancel_report.adapter = customerNameSpinnerAdapter
            customer_spinner_fragment_invoice_cancel_report.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        val filterList: ArrayList<SalesCancelReport> = ArrayList()
                        for (c in allItems) {
                            if (c.customerName == customerNameList[p2]) {
                                filterList.add(c)
                            }
                            if (c.customerName == "All") {
                                filterList.add(c)
                            }
                        }
                        salesCancelReportAdapter.setNewList(filterList)
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {

                    }

                }

        })

        //Calculate total,discount and net amounts
        for (i in allItems) {
            totalAmount += i.totalAmount.toDouble()
            discount += i.totalDiscountAmount.toInt()
            netAmount += ((i.totalAmount).toDouble() - i.totalDiscountAmount)
        }
        sale_cancel_report_total_amt.text = totalAmount.toString()
        sale_cancel_report_discount.text = discount.toString()
        sale_cancel_report_net_amt.text = netAmount.toString()

        recyclerSaleCancel.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = salesCancelReportAdapter
        }
        salesCancelReportViewModel.loadSalesCancelReport()
        salesCancelReportViewModel.loadCustomer()
    }

    private fun onClickItem(invoiceId: String) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setContentView(R.layout.dialog_box_sale_cancel_report)

        //Dialog sale history report of invoice detail recycler view
        salesCancelReportViewModel.saleCancelDetailReportSuccessState.observe(
            this,
            android.arch.lifecycle.Observer {
                saleCancelDetailReportAdapter.setNewList(it as ArrayList<SaleCancelInvoiceDetailReport>)
            })

        salesCancelReportViewModel.reportErrorState.observe(
            this,
            android.arch.lifecycle.Observer {
                Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
            })
        saleCancelDialog.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = saleCancelDetailReportAdapter
        }
        salesCancelReportViewModel.loadSaleCancelDetailReport(invoiceId = invoiceId)

        //Action of dialog button
        dialog.findViewById<Button>(R.id.btn_print).setOnClickListener {
            dialog.dismiss()
        }
        dialog.findViewById<Button>(R.id.btn_ok).setOnClickListener {
            dialog.dismiss()
        }

    }


}
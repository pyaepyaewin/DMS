package com.aceplus.dms.ui.activities.report

import android.app.Dialog
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import com.aceplus.dms.R
import com.aceplus.dms.ui.adapters.report.SaleInvoiceDetailReportAdapter
import com.aceplus.dms.ui.adapters.report.SaleInvoiceReportAdapter
import com.aceplus.dms.viewmodel.report.SaleInvoiceReportViewModel
import com.aceplus.domain.model.report.SaleInvoiceDetailReport
import com.aceplus.domain.model.report.SaleInvoiceReport
import com.aceplus.shared.ui.activities.BaseFragment
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
    lateinit var saleInvoiceReports: RecyclerView
    lateinit var saleInvoiceDialog: RecyclerView
    private val saleInvoiceReportAdapter: SaleInvoiceReportAdapter by lazy {
        SaleInvoiceReportAdapter(
            this::onClickItem
        )
    }
    private val saleInvoiceDetailReportAdapter: SaleInvoiceDetailReportAdapter by lazy { SaleInvoiceDetailReportAdapter() }
    private val saleInvoiceReportViewModel: SaleInvoiceReportViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sale_invoice_report, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        saleInvoiceReports = view.findViewById(R.id.saleInvoceReports)
        saleInvoiceReportViewModel.saleInvoiceReportSuccessState.observe(this, Observer {
            saleInvoiceReportAdapter.setNewList(it as ArrayList<SaleInvoiceReport>)
        })

        saleInvoiceReportViewModel.saleInvoiceReportErrorState.observe(this, Observer {
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
                    val filterList: ArrayList<SaleInvoiceReport> = ArrayList()
                    for (c in allItems) {
                        if (c.customer_name == customerNameList[p2]) {
                            filterList.add(c)
                        }
                        if (c.customer_name == "All"){
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
            totalAmount += i.total_amount.toDouble()
            discount += i.total_discount_amount.toInt()
            netAmount += ((i.total_amount).toDouble() - i.total_discount_amount)
        }
        sale_report_total_amt.text = totalAmount.toString()
        sale_report_discount.text = discount.toString()
        sale_report_net_amt.text = netAmount.toString()
        saleInvoiceReports.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = saleInvoiceReportAdapter
        }
        saleInvoiceReportViewModel.saleInvoiceReport()
        saleInvoiceReportViewModel.loadCustomer()
    }

    private fun onClickItem(invoiceId: String) {
        saleInvoiceDialog = view!!.findViewById(R.id.saleInvoiceDialog)
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setContentView(R.layout.dialog_box_sale_invoice_report)

        //Dialog sale history report of invoice detail recycler view
        saleInvoiceReportViewModel.saleInvoiceDetailReportSuccessState.observe(this, Observer {
            saleInvoiceDetailReportAdapter.setNewList(it as ArrayList<SaleInvoiceDetailReport>)
        })

        saleInvoiceReportViewModel.saleInvoiceReportErrorState.observe(this, Observer {
            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
        })
        saleInvoiceDialog.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = saleInvoiceDetailReportAdapter
        }
        saleInvoiceReportViewModel.saleInvoiceDetailReport(invoiceId = invoiceId)

        //Action of dialog button
        dialog.findViewById<Button>(R.id.btn_print).setOnClickListener {
            dialog.dismiss()
        }
        dialog.findViewById<Button>(R.id.btn_ok).setOnClickListener {
            dialog.dismiss()
        }

    }
}
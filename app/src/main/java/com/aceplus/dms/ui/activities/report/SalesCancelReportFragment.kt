package com.aceplus.dms.ui.activities.report

import android.app.Dialog
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import com.aceplus.dms.R
import com.aceplus.dms.ui.adapters.report.SaleCancelDetailReportAdapter
import com.aceplus.dms.ui.adapters.report.SalesCancelReportAdapter
import com.aceplus.dms.viewmodel.report.SaleInvoiceReportViewModel
import com.aceplus.dms.viewmodel.report.SalesCancelReportViewModel
import com.aceplus.domain.model.report.SaleInvoiceDetailReport
import com.aceplus.domain.model.report.SalesCancelReport
import com.aceplus.shared.ui.activities.BaseFragment
import kotlinx.android.synthetic.main.fragment_sale_cancel_report.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein
import java.util.*

class SalesCancelReportFragment : BaseFragment(), KodeinAware {
    override val kodein: Kodein by kodein()
    var totalAmount = 0.0
    var discount = 0
    var netAmount = 0.0
    lateinit var recyclerSaleCancel: RecyclerView
    lateinit var saleCancelDialog: RecyclerView
    var customerNameList = mutableListOf<String>()
    var allItems: MutableList<SalesCancelReport> = mutableListOf()
    private val salesCancelReportAdapter: SalesCancelReportAdapter by lazy {
        SalesCancelReportAdapter(
            this::onClickItem
        )
    }
    private val saleInvoiceReportViewModel: SaleInvoiceReportViewModel by viewModel()
    private val salesCancelReportViewModel: SalesCancelReportViewModel by viewModel()
    private val saleCancelDetailReportAdapter: SaleCancelDetailReportAdapter by lazy { SaleCancelDetailReportAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sale_cancel_report, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerSaleCancel = view!!.findViewById(R.id.recyclerSaleCancel) as RecyclerView

        salesCancelReportViewModel.salesCancelReportSuccessState.observe(
            this,
            android.arch.lifecycle.Observer {
                salesCancelReportAdapter.setNewList(it as ArrayList<SalesCancelReport>)
                allItems = it
            })
        salesCancelReportViewModel.salesCancelReportErrorState.observe(
            this,
            android.arch.lifecycle.Observer {
                Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
            })

        saleInvoiceReportViewModel.customerDataList.observe(this, android.arch.lifecycle.Observer {
            if (it != null) {
                customerNameList.add("All")
                for (customer in it) {
                    customerNameList.add(customer.customer_name.toString())
                }

            }
            //Bind customer name in fragment spinner
            val spinner =
                view.findViewById<Spinner>(R.id.customer_spinner_fragment_invoice_cancel_report)
            val customerNameSpinnerAdapter =
                ArrayAdapter(context, android.R.layout.simple_spinner_item, customerNameList)
            customerNameSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = customerNameSpinnerAdapter
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    val filterList: ArrayList<SalesCancelReport> = ArrayList()
                    for (c in allItems) {
                        if (c.customer_name == customerNameList[p2]) {
                            filterList.add(c)
                        }
                        if (c.customer_name == "All") {
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
            totalAmount += i.total_amount.toDouble()
            discount += i.total_discount_amount.toInt()
            netAmount += ((i.total_amount).toDouble() - i.total_discount_amount)
        }
        sale_cancel_report_total_amt.text = totalAmount.toString()
        sale_cancel_report_discount.text = discount.toString()
        sale_cancel_report_net_amt.text = netAmount.toString()

        recyclerSaleCancel.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = salesCancelReportAdapter
        }
        salesCancelReportViewModel.salesCancelReport()
        saleInvoiceReportViewModel.loadCustomer()
    }

    private fun onClickItem(invoiceId: String) {
        saleCancelDialog = view!!.findViewById(R.id.saleCancelDialog)
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setContentView(R.layout.dialog_box_sale_cancel_report)

        //Dialog sale history report of invoice detail recycler view
        salesCancelReportViewModel.saleCancelDetailReportSuccessState.observe(
            this,
            android.arch.lifecycle.Observer {
                saleCancelDetailReportAdapter.setNewList(it as ArrayList<SaleInvoiceDetailReport>)
            })

        salesCancelReportViewModel.salesCancelReportErrorState.observe(
            this,
            android.arch.lifecycle.Observer {
                Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
            })
        saleCancelDialog.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = saleCancelDetailReportAdapter
        }
        salesCancelReportViewModel.saleCancelDetailReport(invoiceId = invoiceId)

        //Action of dialog button
        dialog.findViewById<Button>(R.id.btn_print).setOnClickListener {
            dialog.dismiss()
        }
        dialog.findViewById<Button>(R.id.btn_ok).setOnClickListener {
            dialog.dismiss()
        }

    }


}
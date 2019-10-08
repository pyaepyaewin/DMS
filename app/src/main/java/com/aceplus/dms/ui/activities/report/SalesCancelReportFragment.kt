package com.aceplus.dms.ui.activities.report

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.aceplus.dms.R
import com.aceplus.dms.ui.adapters.report.SalesCancelReportAdapter
import com.aceplus.dms.viewmodel.report.SaleInvoiceReportViewModel
import com.aceplus.dms.viewmodel.report.SalesCancelReportViewModel
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
    var customerNameList = mutableListOf<String>()
    var allItems: MutableList<SalesCancelReport> = mutableListOf()
    private val salesCancelReportAdapter: SalesCancelReportAdapter by lazy { SalesCancelReportAdapter() }
    private val saleInvoiceReportViewModel: SaleInvoiceReportViewModel by viewModel()

    private val salesCancelReportViewModel: SalesCancelReportViewModel by viewModel()
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
}
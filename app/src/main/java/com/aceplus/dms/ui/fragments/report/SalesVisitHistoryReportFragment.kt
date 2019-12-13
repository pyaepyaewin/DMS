package com.aceplus.dms.ui.fragments.report

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.aceplus.dms.R
import com.aceplus.dms.ui.adapters.report.SalesVisitHistoryReportAdapter
import com.aceplus.dms.utils.Utils
import com.aceplus.dms.viewmodel.report.ReportViewModel
import com.aceplus.domain.model.sale.SaleVisitForUI
import com.aceplus.domain.vo.report.SalesVisitHistoryReport
import com.aceplus.shared.ui.activities.BaseFragment
import kotlinx.android.synthetic.main.fragment_sale_visit_report.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein
import java.text.SimpleDateFormat
import java.util.*

class SalesVisitHistoryReportFragment : BaseFragment(), KodeinAware {
    override val kodein: Kodein by kodein()
    private var customerNameList = mutableListOf<String>()
    var customerIdList = mutableListOf<Int>()
    var customerNextIdList = mutableListOf<String>()
    var customerAddressList = mutableListOf<String>()
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    private var fromId = 0
    private var toId = 0
    private val salesVisitHistoryReportAdapter: SalesVisitHistoryReportAdapter by lazy { SalesVisitHistoryReportAdapter() }
    private val salesVisitHistoryReportViewModel: ReportViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sale_visit_report, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        getCustomersFromDb()
        catchEvents()
        salesVisitHistoryReportViewModel.loadCustomer()
    }

    private fun getCustomersFromDb() {
        //select customer name list in db
        salesVisitHistoryReportViewModel.customerDataList.observe(this, Observer {
            if (it != null) {
                customerNameList.clear()
                customerIdList.clear()
                for (customer in it) {
                    customerIdList.add(customer.id)
                    customerNextIdList.add(customer.customer_id.toString())
                    customerAddressList.add(customer.address.toString())
                    customerNameList.add(customer.customer_name.toString())
                }

            }
            //Add customer name in spinner in this fragment
            val customerNameSpinnerAdapter =
                ArrayAdapter(context, android.R.layout.simple_spinner_item, customerNameList)
            customerNameSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            fragment_sale_visit_spinner_from_customer.adapter = customerNameSpinnerAdapter
            fragment_sale_visit_spinner_to_customer.adapter = customerNameSpinnerAdapter
            fragment_sale_visit_spinner_from_customer.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                       fromId = p2
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                    }
                }

            fragment_sale_visit_spinner_to_customer.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        toId = p2
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                    }
                }

        })
    }

    private fun catchEvents() {
        val visitedCustomerMap = HashMap<Int, SaleVisitForUI>()
        val saleVisitDataList = ArrayList<SaleVisitForUI>()
        salesVisitHistoryReportViewModel.customerIdCollection.observe(this, Observer {
            visitedCustomerMap.clear()
            saleVisitDataList.clear()
            for (i in it!!.invoiceCustomerId){
                for (data in customerIdList.indices){
                    if (!visitedCustomerMap.containsKey(customerIdList[data])){
                        if (customerIdList[data] == i ) {
                            val saleVisitForUI = SaleVisitForUI()
                            saleVisitForUI.customerId = customerIdList[data]
                            saleVisitForUI.invoiceDate = sdf.format(Date())
                            saleVisitForUI.status = "Visited"
                            saleVisitForUI.task = "Sale"
                            visitedCustomerMap[i] = saleVisitForUI
                        }
                    }
                    else {
                        var saleStatus = ""
                        if (visitedCustomerMap[customerIdList[data]]!!.task != "Sale" && customerIdList[data] == i) {
                            saleStatus = "Sale, " + visitedCustomerMap[customerIdList[data]]!!.task
                            visitedCustomerMap[customerIdList[data]]!!.task = saleStatus
                        }
                    }
                }

            }
            for (i in it!!.preOrderCustomerId){
                for (data in customerIdList.indices) {
                    if (!visitedCustomerMap.containsKey(customerIdList[data])) {
                        if (customerIdList[data] == i) {
                            val saleVisitForUI = SaleVisitForUI()
                            saleVisitForUI.customerId = customerIdList.get(i)
                            saleVisitForUI.invoiceDate = sdf.format(Date())
                            saleVisitForUI.status = "Visited"
                            saleVisitForUI.task = "Order"
                            visitedCustomerMap[i] = saleVisitForUI
                        }
                    } else {
                        var saleStatus = ""
                        if (visitedCustomerMap[customerIdList[data]]!!.task != "Order" && customerIdList[data] == i) {
                            saleStatus = visitedCustomerMap[customerIdList[data]]!!.task + ", Order"
                            visitedCustomerMap[customerIdList[data]]!!.task = saleStatus
                        }
                    }
                }
            }
            for (i in it!!.deliveryUploadCustomerId){
                for (data in customerIdList.indices) {
                    if (!visitedCustomerMap.containsKey(customerIdList[data])) {
                        if (customerIdList[data] == i) {
                            val saleVisitForUI = SaleVisitForUI()
                            saleVisitForUI.customerId = customerIdList[data]
                            saleVisitForUI.invoiceDate = sdf.format(Date())
                            saleVisitForUI.status = "Visited"
                            saleVisitForUI.task = "Delivery"
                            visitedCustomerMap[i] = saleVisitForUI
                        }
                    } else {
                        var saleStatus = ""
                        if (visitedCustomerMap[customerIdList[data]]!!.task != "Delivery" && customerIdList[data] == i) {
                            saleStatus = visitedCustomerMap[customerIdList[data]]!!.task + ", Delivery"
                            visitedCustomerMap[customerIdList[data]]!!.task = saleStatus
                        }
                    }
                }
            }
            for ( i in it!!.saleReturnCustomerId){
                for (data in customerIdList.indices) {
                    if (!visitedCustomerMap.containsKey(customerIdList[data])) {
                        if (customerIdList[data] == i) {
                            val saleVisitForUI = SaleVisitForUI()
                            saleVisitForUI.customerId = customerIdList[data]
                            saleVisitForUI.invoiceDate = sdf.format(Date())
                            saleVisitForUI.status = "Visited"
                            saleVisitForUI.task = "Return"
                            visitedCustomerMap[i] = saleVisitForUI
                        }
                    } else {
                        var saleStatus = ""
                        if (visitedCustomerMap[customerIdList[data]]!!.task != "Return" && customerIdList[data] == i) {
                            saleStatus = visitedCustomerMap[customerIdList[data]]!!.task + ", Return"
                            visitedCustomerMap[customerIdList[data]]!!.task = saleStatus
                        }
                    }
                }
            }

            for (i in it!!.didCustomerFeedBackCustomerId){
                for (data in customerIdList.indices) {
                    if (!visitedCustomerMap.containsKey(customerIdList[data])) {
                        if (customerIdList[i] == i) {
                            val saleVisitForUI = SaleVisitForUI()
                            saleVisitForUI.customerId = customerIdList[data]
                            saleVisitForUI.invoiceDate = sdf.format(Date())
                            saleVisitForUI.status = "Visited"
                            saleVisitForUI.task = "Unsell"
                            visitedCustomerMap[i] = saleVisitForUI
                        }
                    } else {
                        var saleStatus = ""
                        if (visitedCustomerMap[customerIdList[data]]!!.task != "Unsell" && customerIdList[data] == i) {
                            saleStatus = visitedCustomerMap[customerIdList[data]]!!.task + ", Unsell"
                            visitedCustomerMap[customerIdList[data]]!!.task = saleStatus
                        }
                    }
                }
            }
            for (value in visitedCustomerMap.values) {
                saleVisitDataList.add(value)
            }
            for (ip in saleVisitDataList) {
                var index = -1
                for (i in customerIdList.indices) {
                    if (ip.customerId == customerIdList[i]) {
                        index = i
                    }
                }

                ip.customerName = customerNameList[index]
                ip.customerNo = customerNextIdList[index]
                ip.address = customerAddressList[index]
            }
            salesVisitHistoryReportAdapter.setNewList(saleVisitDataList)
        })
        btn_sale_visit_search.setOnClickListener { setupAdapter() }
    }

    private fun setupAdapter() {
        var fromCusNo = 0
        var toCusNo = 0
        fromCusNo = customerIdList[fromId]
        toCusNo = customerIdList[toId]

        if (fragment_sale_visit_spinner_from_customer.selectedItemPosition > fragment_sale_visit_spinner_to_customer.selectedItemPosition) {
            fromCusNo = customerIdList[fragment_sale_visit_spinner_to_customer.selectedItemPosition]
            toCusNo = customerIdList[fragment_sale_visit_spinner_from_customer.selectedItemPosition]
        }

        Log.d("List","$fromCusNo.......$toCusNo")

        salesVisitHistoryReportViewModel.checkFromAndToCustomer(fromCusNo,toCusNo,sdf.format(Date()))

        saleVisitList.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = salesVisitHistoryReportAdapter
        }
    }


}
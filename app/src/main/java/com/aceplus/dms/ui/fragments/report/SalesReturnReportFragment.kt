package com.aceplus.dms.ui.fragments.report

import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aceplus.dms.R
import com.aceplus.dms.ui.adapters.report.SalesReturnDetailReportAdapter
import com.aceplus.dms.ui.adapters.report.SalesReturnReportAdapter
import com.aceplus.dms.viewmodel.report.ReportViewModel
import com.aceplus.domain.vo.report.SalesReturnDetailReport
import com.aceplus.domain.vo.report.SalesReturnReport
import com.aceplus.shared.ui.activities.BaseFragment
import kotlinx.android.synthetic.main.dialog_box_sale_invoice_report.view.*
import kotlinx.android.synthetic.main.dialog_box_sale_return_detail.view.*
import kotlinx.android.synthetic.main.fragment_sale_return_report.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein
import java.util.*
class SalesReturnReportFragment : BaseFragment(), KodeinAware {
    override val kodein: Kodein by kodein()
    var saleReturnDetailList: List<SalesReturnDetailReport> = listOf()
    private val salesReturnReportAdapter: SalesReturnReportAdapter by lazy {
        SalesReturnReportAdapter(this::onClickItem)
    }
    private val salesReturnDetailReportAdapter: SalesReturnDetailReportAdapter by lazy { SalesReturnDetailReportAdapter() }

    private val salesReturnReportViewModel: ReportViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sale_return_report, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //sale return report list
        salesReturnReportViewModel.salesReturnReportSuccessState.observe(this, Observer {
            if (it!!.first != null) {
                Log.d("List is Sizee", "${it.first.size}")
                val qtySaleReturnList = arrayListOf<SalesReturnReport>()
                for (i in it.first) {
                    var qty = 0
                    for (data in it.second) {
                        if (i.saleReturnId == data.sale_return_id) {
                            qty += data.quantity
                        }
                    }
                    val returnQtyReport = SalesReturnReport(i.saleReturnId, i.customerName, i.address, i.returnDate, qty, i.totalAmount)
                    qtySaleReturnList.add(returnQtyReport)
                }
                salesReturnReportAdapter.setNewList(qtySaleReturnList)
            }
        })

        saleReturnReports.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = salesReturnReportAdapter
        }
        salesReturnReportViewModel.loadSalesReturnReport()

        //sale return detail report list
        salesReturnReportViewModel.salesReturnDetailReportSuccessState.observe(this, Observer {
            salesReturnDetailReportAdapter.setNewList(it as ArrayList<SalesReturnDetailReport>)
            saleReturnDetailList = it
        })
    }

    private fun onClickItem(invoiceId: String) {
        val dialogBoxView = activity!!.layoutInflater.inflate(R.layout.dialog_box_sale_return_detail, null)
        val builder = AlertDialog.Builder(activity)
        builder.setView(dialogBoxView)
        builder.setTitle("SALE RETURN DETAIL")
        builder.setPositiveButton("OK", null)
        builder.setCancelable(false)
        val dialog = builder.create()

        dialogBoxView.saleReturnReportDetail.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = salesReturnDetailReportAdapter
        }
        salesReturnReportViewModel.loadSalesReturnDetailReport(invoiceId = invoiceId)
        dialog.show()

    }
}
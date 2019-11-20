package com.aceplus.dms.ui.fragments.report

import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.aceplus.dms.R
import com.aceplus.dms.ui.adapters.report.PreOrderDetailReportAdapter
import com.aceplus.dms.ui.adapters.report.PreOrderReportAdapter
import com.aceplus.dms.viewmodel.report.ReportViewModel
import com.aceplus.domain.vo.report.PreOrderDetailReport
import com.aceplus.domain.vo.report.PreOrderQtyReport
import com.aceplus.domain.vo.report.PreOrderReport
import com.aceplus.shared.ui.activities.BaseFragment
import kotlinx.android.synthetic.main.dialog_box_pre_order_products.view.*
import kotlinx.android.synthetic.main.dialog_box_sale_invoice_report.view.*
import kotlinx.android.synthetic.main.fragment_pre_order_report.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein
import java.util.*

class PreOrderReportFragment : BaseFragment(), KodeinAware {
    override val kodein: Kodein by kodein()
    private val preOrderReportAdapter: PreOrderReportAdapter by lazy { PreOrderReportAdapter(this::onClickItem) }
    private val preOrderDetailReportAdapter: PreOrderDetailReportAdapter by lazy { PreOrderDetailReportAdapter() }

    private val preOrderReportViewModel: ReportViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pre_order_report, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //pre order report list
        preOrderReportViewModel.preOrderReportSuccessState.observe(this, Observer {
            val qtyPreOrderList = arrayListOf<PreOrderQtyReport>()
            for (i in it!!.first){
                var qty = 0.0
                for (product in it.second){
                    if (i.invoiceId == product.sale_order_id) {
                        qty += product.order_quantity!!.toDouble()
                    }
                }
                val preOrderQtyReport = PreOrderQtyReport(i.invoiceId,i.customerName,qty,i.prepaidAmount,i.totalAmount)
                qtyPreOrderList.add(preOrderQtyReport)
            }
            preOrderReportAdapter.setNewList(qtyPreOrderList)
        })

        preOrderReportViewModel.reportErrorState.observe(this, Observer {
            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
        })
        preOrderReports.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = preOrderReportAdapter
        }
        preOrderReportViewModel.loadPreOrderReport()

        //pre order report detail list
        preOrderReportViewModel.preOrderDetailReportSuccessState.observe(this, Observer {
            preOrderDetailReportAdapter.setNewList(it as ArrayList<PreOrderDetailReport>)
        })

    }

    private fun onClickItem(invoiceId: String) {
        //layout inflate for pre order report detail
        val dialogBoxView = activity!!.layoutInflater.inflate(R.layout.dialog_box_pre_order_products, null)
        val builder = AlertDialog.Builder(activity)
        builder.setView(dialogBoxView)
        builder.setCancelable(true)
        val dialog = builder.create()

        dialogBoxView.preOrderProducts.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = preOrderDetailReportAdapter
        }
        preOrderReportViewModel.loadPreOrderDetailReport(invoiceId = invoiceId)
        dialog.show()

    }

}
package com.aceplus.dms.ui.fragments.report

import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.aceplus.dms.R
import com.aceplus.dms.ui.adapters.report.DeliverDetailReportAdapter
import com.aceplus.dms.ui.adapters.report.DeliveryReportAdapter
import com.aceplus.dms.viewmodel.report.ReportViewModel
import com.aceplus.domain.vo.report.DeliverDetailReport
import com.aceplus.domain.vo.report.DeliverReport
import com.aceplus.shared.ui.activities.BaseFragment
import kotlinx.android.synthetic.main.dialog_box_deliveryinvoice_report.view.*
import kotlinx.android.synthetic.main.dialog_box_sale_invoice_report.view.*
import kotlinx.android.synthetic.main.fragment_deliveryinvoice_report.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein
import java.util.*

class DeliverReportFragment : BaseFragment(), KodeinAware {
    override val kodein: Kodein by kodein()
    private val deliveryReportAdapter: DeliveryReportAdapter by lazy { DeliveryReportAdapter(this::onClickItem) }
    private val deliverDetailReportAdapter: DeliverDetailReportAdapter by lazy { DeliverDetailReportAdapter() }

    private val deliverReportViewModel: ReportViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_deliveryinvoice_report, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //delivery report list
        deliverReportViewModel.deliverReportSuccessState.observe(this, Observer {
           val completeDeliveryItemList = arrayListOf<DeliverReport>()
            for (i in it!!.first){
                var qty = 0
                var amount = 0.0
                for (data in it.second){
                    if (i.invoiceId == data.delivery_id){
                        qty += data.quantity!!.toInt()
                    }
                }
                for (item in it.third) {
                    if (i.invoiceId == item.deliveryId) {
                        amount += item.quantity.toDouble() * item.sellingPrice.toDouble()
                    }
                }
                val completeDeliveryItem = DeliverReport(i.invoiceId,i.customerName,i.address,qty.toDouble(),amount.toString())
                completeDeliveryItemList.add(completeDeliveryItem)
            }
            deliveryReportAdapter.setNewList(completeDeliveryItemList)
        })

        deliveryReportRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = deliveryReportAdapter
        }
        deliverReportViewModel.loadDeliverReport()

        //delivery report detail list
        deliverReportViewModel.deliverDetailReportSuccessState.observe(this, Observer {
            deliverDetailReportAdapter.setNewList(it as ArrayList<DeliverDetailReport>)
        })

    }

    private fun onClickItem(invoiceId: String) {
        //layout inflate for delivery report detail
        val dialogBoxView = activity!!.layoutInflater.inflate(R.layout.dialog_box_deliveryinvoice_report, null)
        val builder = AlertDialog.Builder(activity)
        builder.setView(dialogBoxView)
        builder.setTitle("Delivery Report")
        builder.setPositiveButton("OK", null)
        builder.setCancelable(false)
        val dialog = builder.create()

        dialogBoxView.deliveryInvoiceDetailRecycleView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = deliverDetailReportAdapter
        }
        deliverReportViewModel.loadDeliverDetailReport(invoiceId = invoiceId)
        dialog.show()

    }
}
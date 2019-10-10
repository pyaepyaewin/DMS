package com.aceplus.dms.ui.viewholders.report

import android.view.View
import com.aceplus.domain.vo.report.DeliverReport
import com.aceplussolutions.rms.ui.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.list_row_deliveryinvoice_report.view.*

class DeliveryReportViewHolder(
    private val view: View,
    private val onClick: (invoiceId: String) -> Unit
) : BaseViewHolder<DeliverReport>(view) {
    override fun setData(data: DeliverReport) {
        view.apply {
            invoiceNo.text = data.invoiceId
            tvCustomerName.text = data.customerName
            tvAddress.text = data.address
            delivery_report_total_qty.text = data.totalQuantity.toString()
            delivery_report_total_amt.text = data.totalAmount
            setOnClickListener { onClick(data.invoiceId) }
        }
    }
}
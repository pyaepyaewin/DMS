package com.aceplus.dms.ui.viewholders.report

import android.view.View
import com.aceplus.domain.model.report.DeliverReport
import com.aceplussolutions.rms.ui.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.list_row_deliveryinvoice_report.view.*

class DeliveryReportViewHolder(
    private val view: View,
    private val onClick: (invoiceId: String) -> Unit
) : BaseViewHolder<DeliverReport>(view) {
    override fun setData(data: DeliverReport) {
        view.apply {
            invoiceNo.text = data.invoice_id
            tvCustomerName.text = data.customer_name
            tvAddress.text = data.address
            delivery_report_total_qty.text = data.total_quantity.toString()
            delivery_report_total_amt.text = data.total_amount
            setOnClickListener { onClick(data.invoice_id) }
        }
    }
}
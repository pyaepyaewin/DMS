package com.aceplus.dms.ui.viewholders.report

import android.view.View
import com.aceplus.domain.model.report.PreOrderReport
import com.aceplussolutions.rms.ui.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.list_row_pre_order_report.view.*

class PreOrderReportViewHolder(
    private val view: View,
    private val onClick: (invoiceId: String) -> Unit
) : BaseViewHolder<PreOrderReport>(view) {
    override fun setData(data: PreOrderReport) {
        view.apply {
            pre_order_invoice.text = data.invoice_id
            tvCustomerName.text = data.customer_name
            pre_order_total_qty.text = data.total_quantity.toString()
            tvPrepaidAmount.text = data.prepaid_amount
            totalAmount.text = data.total_amount
            setOnClickListener { onClick(data.invoice_id) }
        }
    }
}
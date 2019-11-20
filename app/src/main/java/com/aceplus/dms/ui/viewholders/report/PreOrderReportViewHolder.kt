package com.aceplus.dms.ui.viewholders.report

import android.view.View
import com.aceplus.domain.vo.report.PreOrderQtyReport
import com.aceplus.domain.vo.report.PreOrderReport
import com.aceplussolutions.rms.ui.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.list_row_pre_order_report.view.*

class PreOrderReportViewHolder(
    private val view: View,
    private val onClick: (invoiceId: String) -> Unit
) : BaseViewHolder<PreOrderQtyReport>(view) {
    override fun setData(data: PreOrderQtyReport) {
        view.apply {
            pre_order_invoice.text = data.invoiceId
            tvCustomerName.text = data.customerName
            pre_order_total_qty.text = data.totalQuantity.toString()
            tvPrepaidAmount.text = data.prepaidAmount
            totalAmount.text = data.totalAmount
            setOnClickListener { onClick(data.invoiceId) }
        }
    }
}
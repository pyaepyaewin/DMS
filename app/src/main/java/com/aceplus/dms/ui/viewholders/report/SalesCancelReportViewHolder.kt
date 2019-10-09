package com.aceplus.dms.ui.viewholders.report

import android.view.View
import com.aceplus.domain.model.report.SalesCancelReport
import com.aceplussolutions.rms.ui.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.list_row_sale_cancel_report.view.*

class SalesCancelReportViewHolder(private val view: View,private val onClick: (invoiceId: String) -> Unit):BaseViewHolder<SalesCancelReport>(view) {
    override fun setData(data: SalesCancelReport) {
        view.apply {
            invoiceId.text = data.invoice_id.toString()
            tvCustomerName.text = data.customer_name
            val amount = data.total_amount
            totalAmount.text = amount.toString()
            val discountAmount = data.total_discount_amount
            discount.text = discountAmount.toString()
            netAmount.text = (amount - discountAmount).toString()
            setOnClickListener { onClick(data.invoice_id.toString()) }
        }
    }
}
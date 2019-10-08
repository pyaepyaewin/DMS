package com.aceplus.dms.ui.viewholders.report

import android.view.View
import com.aceplus.domain.model.report.SaleInvoiceReport
import com.aceplussolutions.rms.ui.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.list_row_sale_invoice_report.view.*

class SaleInvoiceReportViewHolder(
    private val view: View,
    private val onClick: (invoiceId: String) -> Unit
) : BaseViewHolder<SaleInvoiceReport>(view) {
    override fun setData(data: SaleInvoiceReport) {
        view.apply {
            invoiceId.text = data.invoice_id
            tvCustomerName.text = data.customer_name
            tvAddress.text = data.address
            val amount = data.total_amount.toDouble()
            totalAmount.text = amount.toString()
            val discountAmount = data.total_discount_amount
            discount.text = discountAmount.toString()
            netAmount.text = (amount - discountAmount).toString()
            view.setOnClickListener { onClick(data.invoice_id) }
        }
    }
}
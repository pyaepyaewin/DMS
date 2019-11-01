package com.aceplus.dms.ui.viewholders.report

import android.view.View
import com.aceplus.domain.vo.report.SaleInvoiceReport
import com.aceplussolutions.rms.ui.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.list_row_sale_invoice_report.view.*

class SaleInvoiceReportViewHolder(
    private val view: View,
    private val onClick: (invoiceId: String) -> Unit
) : BaseViewHolder<SaleInvoiceReport>(view) {
    override fun setData(data: SaleInvoiceReport) {
        view.apply {
            invoiceId.text = data.invoiceId
            tvCustomerName.text = data.customerName
            tvAddress.text = data.address
            val amount = data.totalAmount.toDouble()
            totalAmount.text = amount.toString()
            val discountAmount = data.totalDiscountAmount
            discount.text = discountAmount.toString()
            netAmount.text = (amount - discountAmount).toString()
            setOnClickListener { onClick(data.invoiceId) }
        }
    }
}
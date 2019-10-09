package com.aceplus.dms.ui.viewholders.report

import android.view.View
import com.aceplus.domain.model.report.SalesReturnReport
import com.aceplussolutions.rms.ui.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.list_row_sale_return_report.view.*

class SalesReturnReportViewHolder(
    private val view: View,
    private val onClick: (invoiceId: String) -> Unit
) :
    BaseViewHolder<SalesReturnReport>(view) {
    override fun setData(data: SalesReturnReport) {
        view.apply {
            sale_return_invoice.text = data.invoice_id
            tvCustomerName.text = data.customer_name
            customerAddress.text = data.address
            date.text = data.return_date
            sale_return_total_qty.text = data.total_quantity.toString()
            sale_return_total_amt.text = data.total_amount
            setOnClickListener { onClick(data.invoice_id) }
        }
    }
}
package com.aceplus.dms.ui.viewholders.report

import android.view.View
import com.aceplus.domain.model.report.SalesOrderHistoryReport
import com.aceplussolutions.rms.ui.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.list_row_sale_invoice_report.view.*

class SalesOrderHistoryReportViewHolder(private val view: View):BaseViewHolder<SalesOrderHistoryReport>(view) {
    override fun setData(data: SalesOrderHistoryReport) {
        view.apply {
            invoiceId.text = data.invoice_id
            tvCustomerName.text = data.customer_name
            tvAddress.text = data.address
            totalAmount.text = data.total_amount
            discount.text = data.discount
            netAmount.text = data.net_amount
        }
    }
}
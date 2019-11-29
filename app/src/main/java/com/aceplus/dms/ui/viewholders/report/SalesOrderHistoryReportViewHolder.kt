package com.aceplus.dms.ui.viewholders.report

import android.view.View
import com.aceplus.domain.vo.report.SalesOrderHistoryReport
import com.aceplussolutions.rms.ui.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.fragment_sale_invoice_report.view.*
import kotlinx.android.synthetic.main.list_row_sale_invoice_report.view.*

class SalesOrderHistoryReportViewHolder(private val view: View,private val onClick: (invoiceId: String) -> Unit):BaseViewHolder<SalesOrderHistoryReport>(view) {
    override fun setData(data: SalesOrderHistoryReport) {
        view.apply {
            invoiceId.text = data.invoiceId
            tvCustomerName.text = data.customerName
            tvAddress.text = data.address
            totalAmount.text = data.totalAmount
            discount.text = data.discount
            advance_amt.visibility = View.VISIBLE
            advance_amt.text = data.advancePaymentAmount
            netAmount.text = data.netAmount
            setOnClickListener { onClick(data.invoiceId) }
        }
    }
}
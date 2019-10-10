package com.aceplus.dms.ui.viewholders.report

import android.view.View
import com.aceplus.domain.vo.report.SalesReturnReport
import com.aceplussolutions.rms.ui.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.list_row_sale_return_report.view.*

class SalesReturnReportViewHolder(
    private val view: View,
    private val onClick: (invoiceId: String) -> Unit
) :
    BaseViewHolder<SalesReturnReport>(view) {
    override fun setData(data: SalesReturnReport) {
        view.apply {
            sale_return_invoice.text = data.invoiceId
            tvCustomerName.text = data.customerName
            customerAddress.text = data.address
            date.text = data.returnDate
            sale_return_total_qty.text = data.totalQuantity.toString()
            sale_return_total_amt.text = data.totalAmount
            setOnClickListener { onClick(data.invoiceId) }
        }
    }
}
package com.aceplus.dms.ui.viewholders.report

import android.view.View
import com.aceplus.domain.vo.report.SalesCancelReport
import com.aceplussolutions.rms.ui.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.list_row_sale_cancel_report.view.*

class SalesCancelReportViewHolder(private val view: View,private val onClick: (invoiceId: String) -> Unit):BaseViewHolder<SalesCancelReport>(view) {
    override fun setData(data: SalesCancelReport) {
        view.apply {
            invoiceId.text = data.invoiceId.toString()
            tvCustomerName.text = data.customerName
            val amount = data.totalAmount
            totalAmount.text = amount.toString()
            val discountAmount = data.totalDiscountAmount
            discount.text = discountAmount.toString()
            netAmount.text = (amount - discountAmount).toString()
            setOnClickListener { onClick(data.invoiceId.toString()) }
        }
    }
}
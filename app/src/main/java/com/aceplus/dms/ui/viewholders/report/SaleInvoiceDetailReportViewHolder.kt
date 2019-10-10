package com.aceplus.dms.ui.viewholders.report

import android.view.View
import com.aceplus.domain.vo.report.SaleInvoiceDetailReport
import com.aceplussolutions.rms.ui.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.list_row_sale_invoice_detail.view.*

class SaleInvoiceDetailReportViewHolder(private val view: View):BaseViewHolder<SaleInvoiceDetailReport>(view) {
    override fun setData(data: SaleInvoiceDetailReport) {
        view.apply {
            sidProductName.text = data.productName
            sidQuantity.text = data.soldQuantity.toString()
            sidDiscount.text = data.discountAmount
            sidAmount.text = data.totalAmount.toString()
        }
    }
}
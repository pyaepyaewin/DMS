package com.aceplus.dms.ui.viewholders.report

import android.view.View
import com.aceplus.domain.model.report.SaleInvoiceDetailReport
import com.aceplussolutions.rms.ui.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.list_row_sale_cancel_detail.view.*

class SaleCancelDetailReportViewHolder(private val view: View):BaseViewHolder<SaleInvoiceDetailReport>(view) {
    override fun setData(data: SaleInvoiceDetailReport) {
        view.apply {
            sid_product_name.text = data.product_name
            sid_quantity.text = data.sold_quantity.toString()
            sid_discount.text = data.discount_amount
            sid_amount.text = data.total_amount.toString()
        }
    }
}
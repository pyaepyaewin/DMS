package com.aceplus.dms.ui.viewholders.report

import android.view.View
import com.aceplus.domain.vo.report.SalesReturnDetailReport
import com.aceplussolutions.rms.ui.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.list_row_sale_return_detail.view.*

class SalesReturnDetailReportViewHolder(private val view: View):BaseViewHolder<SalesReturnDetailReport>(view) {
    override fun setData(data: SalesReturnDetailReport) {
        view.apply {
            saleReturnProductName.text = data.customerName
            saleReturnQty.text = data.totalQuantity.toString()
        }
    }
}
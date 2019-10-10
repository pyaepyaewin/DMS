package com.aceplus.dms.ui.viewholders.report

import android.view.View
import com.aceplus.domain.vo.report.DeliverDetailReport
import com.aceplussolutions.rms.ui.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.list_row_deliveryinvoicedetail_report.view.*

class DeliverDetailReportViewHolder(private val view: View):BaseViewHolder<DeliverDetailReport>(view) {
    override fun setData(data: DeliverDetailReport) {
        view.apply {
            productNameTextView.text = data.productName
            qtyTextView.text = data.totalQuantity.toString()
        }
    }
}
package com.aceplus.dms.ui.viewholders.report

import android.view.View
import com.aceplus.domain.vo.report.UnsellReasonReport
import com.aceplussolutions.rms.ui.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.list_row_customer_feedback_report.view.*

class UnsellReasonReportViewHolder(private val view: View):BaseViewHolder<UnsellReasonReport>(view) {
    override fun setData(data: UnsellReasonReport) {
        view.apply {
            tvCustomerName.text = data.customerName
            description.text = data.description
            remark.text = data.remark
        }
    }
}
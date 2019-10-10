package com.aceplus.dms.ui.viewholders.report

import android.view.View
import com.aceplus.domain.vo.report.SalesVisitHistoryReport
import com.aceplussolutions.rms.ui.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.list_row_sale_visit_report.view.*

class SalesVisitHistoryReportViewHolder(private val view: View):BaseViewHolder<SalesVisitHistoryReport>(view) {
    override fun setData(data: SalesVisitHistoryReport) {
        view.apply {
            adapter_sale_visit_customerName.text = data.customerName
            adapter_sale_visit_address.text = data.address
            adapter_sale_visit_status.text = data.status
            adapter_sale_visit_task.text = data.task

        }
    }
}
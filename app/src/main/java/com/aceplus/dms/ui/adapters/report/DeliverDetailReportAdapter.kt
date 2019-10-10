package com.aceplus.dms.ui.adapters.report

import android.view.LayoutInflater
import android.view.ViewGroup
import com.aceplus.dms.R
import com.aceplus.dms.ui.viewholders.report.DeliverDetailReportViewHolder
import com.aceplus.domain.vo.report.DeliverDetailReport
import com.aceplus.shared.ui.adapter.BaseRecyclerViewAdapter

class DeliverDetailReportAdapter:BaseRecyclerViewAdapter<DeliverDetailReportViewHolder,DeliverDetailReport>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): DeliverDetailReportViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.list_row_deliveryinvoicedetail_report,p0,false)
    return DeliverDetailReportViewHolder(view)
    }
}
package com.aceplus.dms.ui.adapters.report

import android.view.LayoutInflater
import android.view.ViewGroup
import com.aceplus.dms.R
import com.aceplus.dms.ui.viewholders.report.SalesVisitHistoryReportViewHolder
import com.aceplus.domain.vo.report.SalesVisitHistoryReport
import com.aceplus.shared.ui.adapter.BaseRecyclerViewAdapter

class SalesVisitHistoryReportAdapter:BaseRecyclerViewAdapter<SalesVisitHistoryReportViewHolder, SalesVisitHistoryReport>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SalesVisitHistoryReportViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.list_row_sale_visit_report,p0,false)
   return SalesVisitHistoryReportViewHolder(view)
    }
}
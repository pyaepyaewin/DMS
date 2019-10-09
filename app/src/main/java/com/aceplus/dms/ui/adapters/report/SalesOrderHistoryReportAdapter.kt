package com.aceplus.dms.ui.adapters.report

import android.view.LayoutInflater
import android.view.ViewGroup
import com.aceplus.dms.R
import com.aceplus.dms.ui.viewholders.report.SalesOrderHistoryReportViewHolder
import com.aceplus.domain.model.report.SalesOrderHistoryReport
import com.aceplus.shared.ui.adapter.BaseRecyclerViewAdapter

class SalesOrderHistoryReportAdapter :
    BaseRecyclerViewAdapter<SalesOrderHistoryReportViewHolder, SalesOrderHistoryReport>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SalesOrderHistoryReportViewHolder {
        val view = LayoutInflater.from(p0.context)
            .inflate(R.layout.list_row_sale_invoice_report, p0, false)
        return SalesOrderHistoryReportViewHolder(view)
    }
}
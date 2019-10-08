package com.aceplus.dms.ui.adapters.report

import android.view.LayoutInflater
import android.view.ViewGroup
import com.aceplus.dms.R
import com.aceplus.dms.ui.viewholders.report.PreOrderReportViewHolder
import com.aceplus.domain.model.report.PreOrderReport
import com.aceplus.shared.ui.adapter.BaseRecyclerViewAdapter

class PreOrderReportAdapter():BaseRecyclerViewAdapter<PreOrderReportViewHolder, PreOrderReport>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PreOrderReportViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.list_row_pre_order_report,p0,false)
        return PreOrderReportViewHolder(view)
    }
}
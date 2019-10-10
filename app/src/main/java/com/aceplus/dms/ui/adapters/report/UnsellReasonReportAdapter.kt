package com.aceplus.dms.ui.adapters.report

import android.view.LayoutInflater
import android.view.ViewGroup
import com.aceplus.dms.R
import com.aceplus.dms.ui.viewholders.report.UnsellReasonReportViewHolder
import com.aceplus.domain.vo.report.UnsellReasonReport
import com.aceplus.shared.ui.adapter.BaseRecyclerViewAdapter

class UnsellReasonReportAdapter:BaseRecyclerViewAdapter<UnsellReasonReportViewHolder,UnsellReasonReport>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): UnsellReasonReportViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.list_row_customer_feedback_report,p0,false)
        return UnsellReasonReportViewHolder(view)
    }
}
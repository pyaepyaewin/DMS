package com.aceplus.dms.ui.adapters.report

import android.view.LayoutInflater
import android.view.ViewGroup
import com.aceplus.dms.R
import com.aceplus.dms.ui.viewholders.report.SalesReturnReportViewHolder
import com.aceplus.domain.model.report.SalesReturnReport
import com.aceplus.shared.ui.adapter.BaseRecyclerViewAdapter

class SalesReturnReportAdapter:BaseRecyclerViewAdapter<SalesReturnReportViewHolder,SalesReturnReport>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SalesReturnReportViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.fragment_sale_return_report,p0,false)
    return SalesReturnReportViewHolder(view)
    }
}
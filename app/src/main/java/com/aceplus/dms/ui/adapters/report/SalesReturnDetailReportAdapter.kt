package com.aceplus.dms.ui.adapters.report

import android.view.LayoutInflater
import android.view.ViewGroup
import com.aceplus.dms.R
import com.aceplus.dms.ui.viewholders.report.SalesReturnDetailReportViewHolder
import com.aceplus.domain.vo.report.SalesReturnDetailReport
import com.aceplus.shared.ui.adapter.BaseRecyclerViewAdapter

class SalesReturnDetailReportAdapter:BaseRecyclerViewAdapter<SalesReturnDetailReportViewHolder, SalesReturnDetailReport>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SalesReturnDetailReportViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.list_row_sale_return_detail,p0,false)
   return SalesReturnDetailReportViewHolder(view)
    }
}
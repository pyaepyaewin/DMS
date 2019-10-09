package com.aceplus.dms.ui.adapters.report

import android.view.LayoutInflater
import android.view.ViewGroup
import com.aceplus.dms.R
import com.aceplus.dms.ui.viewholders.report.PreOrderDetailReportViewHolder
import com.aceplus.domain.model.report.PreOrderDetailReport
import com.aceplus.shared.ui.adapter.BaseRecyclerViewAdapter

class PreOrderDetailReportAdapter:BaseRecyclerViewAdapter<PreOrderDetailReportViewHolder, PreOrderDetailReport>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PreOrderDetailReportViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.list_row_pre_order_product,p0,false)
   return PreOrderDetailReportViewHolder(view)
    }
}
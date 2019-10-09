package com.aceplus.dms.ui.adapters.report

import android.view.LayoutInflater
import android.view.ViewGroup
import com.aceplus.dms.R
import com.aceplus.dms.ui.viewholders.report.SalesCancelReportViewHolder
import com.aceplus.domain.model.report.SalesCancelReport
import com.aceplus.shared.ui.adapter.BaseRecyclerViewAdapter

class SalesCancelReportAdapter(private val onClick: (invoiceId: String) -> Unit):BaseRecyclerViewAdapter<SalesCancelReportViewHolder,SalesCancelReport>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SalesCancelReportViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.list_row_sale_cancel_report,p0,false)
    return SalesCancelReportViewHolder(view,onClick)
    }
}
package com.aceplus.dms.ui.adapters.report

import android.view.LayoutInflater
import android.view.ViewGroup
import com.aceplus.dms.R
import com.aceplus.dms.ui.viewholders.report.SaleCancelDetailReportViewHolder
import com.aceplus.domain.model.report.SaleInvoiceDetailReport
import com.aceplus.domain.model.report.SalesCancelReport
import com.aceplus.shared.ui.adapter.BaseRecyclerViewAdapter

class SaleCancelDetailReportAdapter :
    BaseRecyclerViewAdapter<SaleCancelDetailReportViewHolder, SaleInvoiceDetailReport>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SaleCancelDetailReportViewHolder {
        val view =
            LayoutInflater.from(p0.context).inflate(R.layout.list_row_sale_cancel_detail, p0, false)
        return SaleCancelDetailReportViewHolder(view)
    }
}
package com.aceplus.dms.ui.adapters.report

import android.view.LayoutInflater
import android.view.ViewGroup
import com.aceplus.dms.R
import com.aceplus.dms.ui.viewholders.report.SaleInvoiceDetailReportViewHolder
import com.aceplus.domain.vo.report.SaleInvoiceDetailReport
import com.aceplus.shared.ui.adapter.BaseRecyclerViewAdapter

class SaleInvoiceDetailReportAdapter:BaseRecyclerViewAdapter<SaleInvoiceDetailReportViewHolder, SaleInvoiceDetailReport>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SaleInvoiceDetailReportViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.list_row_sale_invoice_detail,p0,false)
   return SaleInvoiceDetailReportViewHolder(view)
    }
}
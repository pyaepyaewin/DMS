package com.aceplus.dms.ui.adapters.report

import android.view.LayoutInflater
import android.view.ViewGroup
import com.aceplus.dms.R
import com.aceplus.dms.ui.viewholders.report.SaleInvoiceReportViewHolder
import com.aceplus.domain.vo.report.SaleInvoiceReport
import com.aceplus.shared.ui.adapter.BaseRecyclerViewAdapter

class SaleInvoiceReportAdapter(private val onClick: (invoiceId: String) -> Unit) :
    BaseRecyclerViewAdapter<SaleInvoiceReportViewHolder, SaleInvoiceReport>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SaleInvoiceReportViewHolder {
        val view = LayoutInflater.from(p0.context)
            .inflate(R.layout.list_row_sale_invoice_report, p0, false)
        return SaleInvoiceReportViewHolder(view,onClick)
    }
}
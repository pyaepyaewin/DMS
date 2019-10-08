package com.aceplus.dms.ui.adapters.report

import android.view.LayoutInflater
import android.view.ViewGroup
import com.aceplus.dms.R
import com.aceplus.dms.ui.viewholders.report.DeliveryReportViewHolder
import com.aceplus.domain.model.report.DeliverReport
import com.aceplus.shared.ui.adapter.BaseRecyclerViewAdapter

class DeliveryReportAdapter:BaseRecyclerViewAdapter<DeliveryReportViewHolder,DeliverReport>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): DeliveryReportViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.list_row_deliveryinvoice_report,p0,false)
    return DeliveryReportViewHolder(view)
    }
}
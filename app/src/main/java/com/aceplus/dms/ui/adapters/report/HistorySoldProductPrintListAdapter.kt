package com.aceplus.dms.ui.adapters.report

import android.view.LayoutInflater
import android.view.ViewGroup
import com.aceplus.dms.R
import com.aceplus.dms.ui.viewholders.report.HistorySoldProductPrintListViewHolder
import com.aceplus.domain.vo.report.SaleInvoiceDetailReport
import com.aceplus.shared.ui.adapter.BaseRecyclerViewAdapter

class HistorySoldProductPrintListAdapter:BaseRecyclerViewAdapter<HistorySoldProductPrintListViewHolder,SaleInvoiceDetailReport>(){

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): HistorySoldProductPrintListViewHolder {

        val view = LayoutInflater.from(p0.context)
            .inflate(R.layout.list_product_print, p0, false)
        return HistorySoldProductPrintListViewHolder(view)

    }

}
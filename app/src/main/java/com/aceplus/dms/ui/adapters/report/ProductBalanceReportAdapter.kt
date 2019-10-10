package com.aceplus.dms.ui.adapters.report

import android.view.LayoutInflater
import android.view.ViewGroup
import com.aceplus.dms.R
import com.aceplus.dms.ui.viewholders.report.ProductBalanceReportViewHolder
import com.aceplus.shared.ui.adapter.BaseRecyclerViewAdapter

class ProductBalanceReportAdapter:BaseRecyclerViewAdapter<ProductBalanceReportViewHolder, com.aceplus.domain.entity.product.Product>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ProductBalanceReportViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.list_row_product_balance_report,p0,false)
    return ProductBalanceReportViewHolder(view)
    }
}
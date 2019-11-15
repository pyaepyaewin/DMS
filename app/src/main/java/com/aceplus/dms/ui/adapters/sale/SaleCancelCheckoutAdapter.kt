package com.aceplus.dms.ui.adapters.sale

import android.view.LayoutInflater
import android.view.ViewGroup
import com.aceplus.dms.R
import com.aceplus.dms.ui.viewholders.sale.SaleCancelCheckOutViewHolder
import com.aceplus.domain.vo.SoldProductInfo
import com.aceplus.shared.ui.adapter.BaseRecyclerViewAdapter

class SaleCancelCheckoutAdapter: BaseRecyclerViewAdapter<SaleCancelCheckOutViewHolder, SoldProductInfo>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SaleCancelCheckOutViewHolder {
        val view = LayoutInflater.from(p0.context)
            .inflate(R.layout.list_row_sold_product_checkout, p0, false)

        return SaleCancelCheckOutViewHolder(view)
    }
}
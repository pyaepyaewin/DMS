package com.aceplus.dms.ui.adapters.sale

import android.view.LayoutInflater
import android.view.ViewGroup
import com.aceplus.dms.R
import com.aceplus.dms.ui.viewholders.sale.SaleCancelViewHolder
import com.aceplus.domain.model.sale.salecancel.SaleCancelItem
import com.aceplus.shared.ui.adapter.BaseRecyclerViewAdapter

class SalesCancelAdapter(private val onClick: (data: SaleCancelItem) -> Unit):
    BaseRecyclerViewAdapter<SaleCancelViewHolder, SaleCancelItem>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SaleCancelViewHolder {
        val view=LayoutInflater.from(p0.context).inflate(R.layout.row_sale_cancel_activity,p0,false)
        return SaleCancelViewHolder(view,onClick)
    }

}
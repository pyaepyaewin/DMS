package com.aceplus.dms.ui.adapters.sale

import android.view.LayoutInflater
import android.view.ViewGroup
import com.aceplus.dms.R
import com.aceplus.dms.ui.viewholders.sale.SaleCancelDetailViewHolder
import com.aceplus.domain.entity.product.Product
import com.aceplus.domain.model.sale.salecancel.SoldProductDataClass
import com.aceplus.domain.vo.SoldProductInfo
import com.aceplus.shared.ui.adapter.BaseRecyclerViewAdapter

class SaleCancelDetailAdapter(private val onClickQtyButton: (data: SoldProductInfo, position: Int) -> Unit) : BaseRecyclerViewAdapter<SaleCancelDetailViewHolder, SoldProductInfo>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SaleCancelDetailViewHolder {
        val view = LayoutInflater.from(p0.context)
            .inflate(R.layout.list_row_sold_product_checkout, p0, false)
       return SaleCancelDetailViewHolder(view,onClickQtyButton)

    }


}
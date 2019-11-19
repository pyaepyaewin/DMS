package com.aceplus.dms.ui.adapters.sale

import android.view.LayoutInflater
import android.view.ViewGroup
import com.aceplus.dms.R
import com.aceplus.dms.ui.viewholders.sale.SaleReturnSelectedProductViewHolder
import com.aceplus.domain.vo.SoldProductInfo
import com.aceplus.shared.ui.adapter.BaseRecyclerViewAdapter

class SaleReturnSelectedProductAdapter: BaseRecyclerViewAdapter<SaleReturnSelectedProductViewHolder, SoldProductInfo>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SaleReturnSelectedProductViewHolder {

        val view = LayoutInflater.from(p0.context).inflate(R.layout.listrow_sale_return, p0, false)

        return SaleReturnSelectedProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: SaleReturnSelectedProductViewHolder, position: Int) {

        holder.setData(mDataList[position], position)

    }

}
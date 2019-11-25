package com.aceplus.dms.ui.adapters.sale

import android.view.LayoutInflater
import android.view.ViewGroup
import com.aceplus.dms.R
import com.aceplus.dms.ui.viewholders.sale.SaleExchangeInfoViewHolder
import com.aceplus.domain.entity.product.Product
import com.aceplus.shared.ui.adapter.BaseRecyclerViewAdapter

class SaleExchangeInfoAdapter: BaseRecyclerViewAdapter<SaleExchangeInfoViewHolder, Product>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SaleExchangeInfoViewHolder {

        val view = LayoutInflater.from(p0.context).inflate(R.layout.row_sale_exchange_info, p0, false)
        return SaleExchangeInfoViewHolder(view)

    }

}
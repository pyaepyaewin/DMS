package com.aceplus.dms.ui.adapters.sale

import android.view.LayoutInflater
import android.view.ViewGroup
import com.aceplus.dms.R
import com.aceplus.dms.ui.viewholders.CustomerViewHolder
import com.aceplus.dms.ui.viewholders.sale.SoldProductViewHolder
import com.aceplus.domain.entity.customer.Customer
import com.aceplus.domain.entity.product.Product
import com.aceplus.shared.ui.adapter.BaseRecyclerViewAdapter

class SoldProductListAdapter(private val onClickCustomer: (data: Product) -> Unit) :
    BaseRecyclerViewAdapter<SoldProductViewHolder, Product>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SoldProductViewHolder {
        val view =
            LayoutInflater.from(p0.context).inflate(R.layout.list_row_sold_product_with_custom_discount, p0, false)
        return SoldProductViewHolder(view, onClickCustomer)
    }
}
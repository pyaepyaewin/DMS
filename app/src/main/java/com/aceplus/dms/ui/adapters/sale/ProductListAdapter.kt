package com.aceplus.dms.ui.adapters.sale

import android.view.LayoutInflater
import android.view.ViewGroup
import com.aceplus.dms.R
import com.aceplus.dms.ui.viewholders.sale.ProductViewHolder
import com.aceplus.domain.entity.product.Product
import com.aceplus.shared.ui.adapter.BaseRecyclerViewAdapter

class ProductListAdapter(private val onClickCustomer: (data: Product) -> Unit) :
    BaseRecyclerViewAdapter<ProductViewHolder, Product>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ProductViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.custom_simple_list_item_1, p0, false)
        return ProductViewHolder(view, onClickCustomer)
    }

}
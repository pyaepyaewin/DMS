package com.aceplus.dms.ui.viewholders.sale

import android.view.View
import com.aceplus.domain.entity.customer.Customer
import com.aceplus.domain.entity.product.Product
import com.aceplussolutions.rms.ui.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.custom_simple_list_item_1.view.*

class ProductViewHolder(itemView: View, val onClickProduct: (data: Product) -> Unit) :
    BaseViewHolder<Product>(itemView) {

    override fun setData(data: Product) {
        itemView.tvCustomerName.text = data.product_name
        itemView.setOnClickListener { onClickProduct(data) }
        itemView.layCustomerList.setBackgroundResource(android.R.color.transparent)
    }

}
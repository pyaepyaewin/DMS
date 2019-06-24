package com.aceplus.dms.ui.viewholders.sale

import android.view.View
import com.aceplus.domain.entity.customer.Customer
import com.aceplus.domain.entity.product.Product
import com.aceplussolutions.rms.ui.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.custom_simple_list_item_1.view.*

class SoldProductViewHolder(itemView: View, val onLongClickProduct: (data: Product) -> Unit) :
    BaseViewHolder<Product>(itemView) {

    override fun setData(data: Product) {
//        itemView.tvCustomerName.text = data.customer_name
//        itemView.setOnClickListener { onClickCustomer(data) }
    }

}